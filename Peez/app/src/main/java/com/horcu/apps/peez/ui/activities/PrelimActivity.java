package com.horcu.apps.peez.ui.activities;
import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
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
import com.horcu.apps.peez.custom.Api;
import com.horcu.apps.peez.registration.RegistrationIntentService;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrelimActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "PrelimActivity";
    private SharedPreferences settings;
    User user;
    private GoogleAccountCredential credential;
    private String accountName;
    static final int REQUEST_ACCOUNT_PICKER = 2;
    private UserApi userApi = null;
    private Registration registrationApi = null;
    String mPhoneNumber;
    private UserSettingsApi userSettingsApi;
    private String regId;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private RubberLoaderView loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prelim);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        user = new User();
        settings = getSharedPreferences("Peez", 0);
        BuildUserApiService();
        userSettingsApi = Api.BuildUserSettingsApiService();
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

       loader =  ((RubberLoaderView) findViewById(R.id.loader1));
        loader.startLoading();

        settings = getSharedPreferences("Peez", 0);

        credential = GoogleAccountCredential.usingAudience(this, consts.GOOGLE_ACCOUNT_CREDENTIALS_AUDIENCE);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loader.setVisibility(View.GONE);

                boolean sentToken = settings
                        .getBoolean(consts.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    navigateToApp();
                    Snackbar.make(loader,"token sent",Snackbar.LENGTH_LONG).show();
                    settings.edit().putBoolean(consts.DEVICE_REGISTERED, true).apply();
                } else {
                    Snackbar.make(loader,"token not sent",Snackbar.LENGTH_LONG).show();
                    String msg = "token was not successfully sent to the server. kill app and retry. \r OK only for dev mode. CANNOT HAPPEN IN PROD";
                    settings.edit().putBoolean(consts.DEVICE_REGISTERED, false).apply();
                    navigateToErrorPage(msg);
                }
            }
        };

        String name = settings.getString(consts.PREF_ACCOUNT_NAME, null);

        if(name == null)
        {
            chooseAccount();
        }
        else {

            //check if device is registered
            boolean isRegistered = settings.getBoolean(consts.DEVICE_REGISTERED, false);

            //register the app if the device is compatible with play services
            if (checkPlayServices()) {

                if (isRegistered) {
                    navigateToApp();
                } else {
                    // Start IntentService to register this application with GCM.
                    Intent intent = new Intent(this, RegistrationIntentService.class);
                    intent.putExtra("userName", name);
                    startService(intent);
                }
            }
        }
    }

    private void navigateToErrorPage(String msg) {
        startActivity(new Intent(this, FailureActivity.class).putExtra("error", msg));
    }


    private void RegisterDeviceAsync(final FloatingActionButton fab, final User user, final Context ctx) {
        new AsyncTask<Void, Void, Boolean>() {
            Registration regService = null;
            GoogleCloudMessaging gcm;
            Context context = ctx;

            final String SENDER_ID = consts.SENDER_ID;

            protected Boolean doInBackground(Void... params) {
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

                //regId = settings.getString(consts.REG_ID,""); // do reg again with the same regid if it exists there wont be a new record if it doesnt then we will re register
                if (gcm == null)
                    gcm = GoogleCloudMessaging.getInstance(context);


                try {
                  //  regId = settings.getString(consts.REG_ID, "");

                 //   if (regId.equals(""))
                        regId = gcm.register(SENDER_ID);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }

                try {
                    if (regId == null)
                        return false;

                    regService.register(regId).execute();

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(consts.REG_ID, regId);
                    editor.putString(consts.PREF_ACCOUNT_NAME, user.getUserName());
                    editor.apply();

                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            protected void onPostExecute(Boolean success) {
                if (!success) {
                    startActivity(new Intent(context, FailureActivity.class).putExtra("error", "Not able to register device"));
                    return;
                }

                AddUserAsync(fab, user, regId);
                Snackbar.make(fab, "registered device and added user", Snackbar.LENGTH_LONG).show();


                Logger.getLogger("REGISTRATION").log(Level.INFO, regId);
            }
        }.execute();
    }

    private void AddUserAsync(final View fab, final User user, final String regId) {

        new AsyncTask<Void, Void, String>() {

            protected String doInBackground(Void... params) {
                // this will be supplied after calling register in an asyc task before this one


                return "success";
            }

            protected void onPostExecute(String msg) {
                if (msg.equals("")) {
                    Snackbar.make(fab, "user registration failed", Snackbar.LENGTH_LONG).show();
                    Logger.getLogger("ADDED USER FAiled").log(Level.SEVERE, msg);
                } else {
                    Snackbar.make(fab, "user registration successfully", Snackbar.LENGTH_LONG).show();
                    Logger.getLogger("ADDED USER").log(Level.INFO, msg);

                }

            }
        }.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(consts.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
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

        }.execute();
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
                        credential.setSelectedAccountName(accountName);

                        //check if device is registered
                        boolean isRegistered = settings.getBoolean(consts.DEVICE_REGISTERED, false);

                        //register the app if the device is compatible with play services
                        if (checkPlayServices()) {

                            if (isRegistered) {
                                navigateToApp();
                            } else {
                                // Start IntentService to register this application with GCM.
                                Intent intent = new Intent(this, RegistrationIntentService.class);
                                intent.putExtra("userName", accountName);
                                startService(intent);
                            }
                        }
                    } else {
                        chooseAccount();
                    }
                }
                break;
        }
    }

    private void navigateToApp() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
