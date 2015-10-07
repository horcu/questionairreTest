package com.horcu.apps.peez;

import android.animation.TimeInterpolator;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Credentials;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.greenfrvr.rubberloader.RubberLoaderView;
import com.horcu.apps.common.utilities.consts;
import com.horcu.apps.peez.backend.models.userApi.UserApi;
import com.horcu.apps.peez.backend.models.userApi.model.User;
import com.horcu.apps.peez.backend.registration.Registration;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrelimActivity extends AppCompatActivity {
    private SharedPreferences settings;
    private GoogleAccountCredential credential;
    private String accountName;
    static final int REQUEST_ACCOUNT_PICKER = 2;
    private  UserApi userApi = null;
    private Registration registrationApi = null;
    String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prelim);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        TelephonyManager tMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneNumber = tMgr.getLine1Number();

        TimeInterpolator interpolator = new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return 2;
            }
        };
        ((RubberLoaderView) findViewById(R.id.loader1)).setInterpolator(interpolator);
        ((RubberLoaderView) findViewById(R.id.loader1)).startLoading();

        settings = getSharedPreferences( "Peez", 0);

        credential = GoogleAccountCredential.usingAudience(this,
                "server:client_id:932165043385-peo6s0mkqf2ik7q75poo6vm10sv1fs46.apps.googleusercontent.com");

        setSelectedAccountName(settings.getString(consts.PREF_ACCOUNT_NAME, null));

        BuildRegistrationApiService();
        BuildUserApiService();


        if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {

            //       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);

            //dev
            final User user = new User();
            user.setAlias("peze");                       // set the email address as the alias then ask the user to change it later in a noninvasive way
            user.setCash(consts.STARTING_CASH);
            user.setEmail(credential.getSelectedAccount().name); // get this automatically after logging in; TODO - make provision for getting email if the device is ios
            user.setJoined(new Date().toString()); // set this to today unless the user is already a member
            user.setRank(consts.STARTING_RANK);
            user.setPhone(mPhoneNumber);               // get this programmatically
            RegisterDeviceAsync(fab, user, this);

        }
        //fab.setVisibility(View.GONE);
    }

    private void RegisterDeviceAsync(final FloatingActionButton fab, final User user, final Context ctx) {
        new AsyncTask<Void, Void, String> (){
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

                String msg;
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    String regId = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regId;

                    regService.register(regId).execute();

                } catch (IOException ex) {
                    ex.printStackTrace();
                    msg = "Error: " + ex.getMessage();
                }
                return msg;
            }

            protected void onPostExecute(String regId) {
                AddUserAsync(fab,user, regId);
               Snackbar.make(fab, "device registered with id:" + regId, Snackbar.LENGTH_LONG).show();
               Snackbar.make(fab, "adding user", Snackbar.LENGTH_LONG).show();

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
                if(msg == ""){
                    Snackbar.make(fab, "user registration failed", Snackbar.LENGTH_LONG).show();
                    Logger.getLogger("ADDED USER FAiled").log(Level.SEVERE, msg);
                }
                else{
                    Snackbar.make(fab, "user registration successfully", Snackbar.LENGTH_LONG).show();
                    Logger.getLogger("ADDED USER").log(Level.INFO, msg);
                    navigateToApp(user);
                }

            }
        }.execute();
    }

    private void BuildRegistrationApiService() {
        if(registrationApi == null) {
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
        if(userApi == null) {
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
                        user.setEmail(accountName);//get from preferences/json local pref file / or cloud
                        user.setPhone(mPhoneNumber);//get from preferences/json local pref file / or cloud
                        user.setRank(consts.STARTING_RANK); //get from preferences/json local pref file / or cloud
                        navigateToApp(user);
                    }
                    else {
                        chooseAccount();
                    }
                }
                break;
        }
    }

    private void navigateToApp(User user) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("regId", user.getRegistrationId());
        intent.putExtra("email", user.getEmail());
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
