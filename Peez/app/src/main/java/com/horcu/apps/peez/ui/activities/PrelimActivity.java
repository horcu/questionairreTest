package com.horcu.apps.peez.ui.activities;

import android.animation.TimeInterpolator;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.greenfrvr.rubberloader.RubberLoaderView;
import com.horcu.apps.common.utilities.consts;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.userApi.UserApi;
import com.horcu.apps.peez.backend.models.userApi.model.User;
import com.horcu.apps.peez.backend.models.userSettingsApi.UserSettingsApi;
import com.horcu.apps.peez.backend.models.userSettingsApi.model.UserSettings;
import com.horcu.apps.peez.backend.registration.Registration;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrelimActivity extends AppCompatActivity {
    private SharedPreferences settings;
    private GoogleAccountCredential credential;
    private String accountName;
    static final int REQUEST_ACCOUNT_PICKER = 2;
    private UserApi userApi = null;
    private Registration registrationApi = null;
    String mPhoneNumber;
    private UserSettingsApi userSettingsApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prelim);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        settings = getSharedPreferences("Peez", 0);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneNumber = tMgr.getLine1Number();

        ((RubberLoaderView) findViewById(R.id.loader1)).startLoading();

        settings = getSharedPreferences("Peez", 0);

        credential = GoogleAccountCredential.usingAudience(this, consts.GOOGLE_ACCOUNT_CREDENTIALS_AUDIENCE );
        setSelectedAccountName(settings.getString(consts.PREF_ACCOUNT_NAME, null));
        BuildRegistrationApiService();
        BuildUserApiService();
        BuildUserSettingsApiService();

        if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {

            final User user = new User();
            user.setAlias("");                       // set the email address as the alias then ask the user to change it later in a noninvasive way
            user.setCash(consts.STARTING_CASH);
            user.setUserName(credential.getSelectedAccount().name); // get this automatically after logging in; TODO - make provision for getting email if the device is ios
            user.setEmail(credential.getSelectedAccount().name); // get this automatically after logging in; TODO - make provision for getting email if the device is ios
            user.setJoined(new Date().toString()); // set this to today unless the user is already a member
            user.setRank(consts.STARTING_RANK);
            user.setPhone(mPhoneNumber);               // get this programmatically
            RegisterDeviceAsync(fab, user, this);
        }
    }

    private void RegisterDeviceAsync(final FloatingActionButton fab, final User user, final Context ctx) {
        new AsyncTask<Void, Void, String>() {
            Registration regService = null;
            GoogleCloudMessaging gcm;
            Context context = ctx;

            final String SENDER_ID = consts.SENDER_ID;

            protected String doInBackground(Void... params) {
                if (regService == null) {
                    Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), null)
                            // Need setRootUrl and setGoogleClientRequestInitializer only for local testing,
                            // otherwise they can be skipped
                            .setRootUrl(consts.DEV_MODE
                                    ? consts.DEV_URL
                                    : consts.PROD_URL)
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                        throws IOException {
                                    abstractGoogleClientRequest.setDisableGZipContent(true);
                                }
                            });
                    regService = builder.build();
                }

                String regId = null;
                    if (gcm == null) {

                        gcm = GoogleCloudMessaging.getInstance(context);
                    }


                    if(!settings.getString(consts.REG_ID, "").equals("")){

                        try {
                            regId = settings.getString(consts.REG_ID,""); // do reg again with the same regid if it exists there wont be a new record if it doesnt then we will re register
                            regService.register(regId).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try {
                            regId = gcm.register(SENDER_ID);
                          } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            if(regId == null)
                                return null;

                            regService.register(regId).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                return regId;
            }

            protected void onPostExecute(String regId) {
                if(regId == null || regId.equals(""))
                    return;

                AddUserAsync(fab, user, regId);
                Snackbar.make(fab, "registered device and added user", Snackbar.LENGTH_LONG).show();

                SharedPreferences.Editor editor = settings.edit();
                editor.putString(consts.REG_ID, regId);
                editor.commit();
                Logger.getLogger("REGISTRATION").log(Level.INFO, regId);
            }
        }.execute();
    }

    private void AddUserAsync(final FloatingActionButton fab, final User user, final String regId) {

        new AsyncTask<Void, Void, String>() {

            protected String doInBackground(Void... params) {
                // this will be supplied after calling register in an asyc task before this one

                try {
                    user.setRegistrationId(regId);
                    userApi.insert(user).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "";
                }
                return "success";
            }

            protected void onPostExecute(String msg) {
                if (msg.equals("")) {
                    Snackbar.make(fab, "user registration failed", Snackbar.LENGTH_LONG).show();
                    Logger.getLogger("ADDED USER FAiled").log(Level.SEVERE, msg);
                } else {
                    Snackbar.make(fab, "user registration successfully", Snackbar.LENGTH_LONG).show();
                    Logger.getLogger("ADDED USER").log(Level.INFO, msg);
                    AddUserSettingsAsync(user);
                    navigateToApp(user);
                }

            }
        }.execute();
    }

    private void AddUserSettingsAsync(final User user) {

        new AsyncTask<Void, Void, String>() {

            protected String doInBackground(Void... params) {
                // this will be supplied after calling register in an asyc task before this one

                try {
                    UserSettings uSettings = new UserSettings();
                    uSettings.setName(user.getEmail());
                   // uSettings.setEmail(user.getEmail());
                    uSettings.setValue(getString(R.string.newUserSettingsJson));
                    userSettingsApi.insert(uSettings).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "";
                }
                return "success";
            }

            protected void onPostExecute(String msg) {

            }
        }.execute();
    }

    private void BuildRegistrationApiService() {
        if (registrationApi == null) {
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport()
                    , new AndroidJsonFactory(), null)
                    .setRootUrl(consts.DEV_MODE
                            ? consts.DEV_URL
                            : consts.PROD_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            registrationApi = builder.build();
        }
    }

    private void BuildUserApiService() {
        if (userApi == null) {
            UserApi.Builder builder = new UserApi.Builder(AndroidHttp.newCompatibleTransport()
                    , new AndroidJsonFactory(), null)
                    .setRootUrl(consts.DEV_MODE
                            ? consts.DEV_URL
                            : consts.PROD_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            userApi = builder.build();
        }
    }

    private void BuildUserSettingsApiService() {
        if (userSettingsApi == null) {
            UserSettingsApi.Builder builder = new UserSettingsApi.Builder(AndroidHttp.newCompatibleTransport()
                    , new AndroidJsonFactory(), null)
                    .setRootUrl(consts.DEV_MODE
                            ? consts.DEV_URL
                            : consts.PROD_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            userSettingsApi = builder.build();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ACCOUNT_PICKER:
                if (data != null && data.getExtras() != null) {
                    String accountName =
                            data.getExtras().getString(
                                    AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        setSelectedAccountName(accountName);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(consts.PREF_ACCOUNT_NAME, accountName);
                        editor.commit();

                        User user = new User();
                        user.setRegistrationId(settings.getString("regId", "0"));
                        user.setUserName(accountName);//get from preferences/json local pref file / or cloud
                      //  user.setEmail(accountName);//get from preferences/json local pref file / or cloud
                        user.setPhone(mPhoneNumber);//get from preferences/json local pref file / or cloud
                        user.setRank(consts.STARTING_RANK); //get from preferences/json local pref file / or cloud
                        navigateToApp(user);
                    } else {
                        chooseAccount();
                    }
                }
                break;
        }
    }

    private void navigateToApp(User user) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("regId", user.getRegistrationId());
        //intent.putExtra("email", user.getEmail());
        intent.putExtra("userName", user.getUserName());
        intent.putExtra("rank", user.getRank());
        intent.putExtra("phone", user.getPhone());

        startActivity(intent);
    }

    private void setSelectedAccountName(String accountName) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(consts.PREF_ACCOUNT_NAME, accountName);
        editor.commit();
        credential.setSelectedAccountName(accountName);
        this.accountName = accountName;
    }

    void chooseAccount() {
        startActivityForResult(credential.newChooseAccountIntent(),
                REQUEST_ACCOUNT_PICKER);
    }
}
