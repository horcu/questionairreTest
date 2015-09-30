package com.horcu.apps.peez;

import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.horcu.apps.common.utilities.consts;
import com.horcu.apps.peez.backend.models.userApi.UserApi;
import com.horcu.apps.peez.backend.models.userApi.model.User;

import java.io.IOException;

public class PrelimActivity extends AppCompatActivity {
    private SharedPreferences settings;
    private GoogleAccountCredential credential;
    private String accountName;
    static final int REQUEST_ACCOUNT_PICKER = 2;
    private  UserApi myApiService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prelim);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        settings = getSharedPreferences( "Peez", 0);

        credential = GoogleAccountCredential.usingAudience(this,
                "server:client_id:932165043385-peo6s0mkqf2ik7q75poo6vm10sv1fs46.apps.googleusercontent.com");

        setSelectedAccountName(settings.getString(consts.PREF_ACCOUNT_NAME, null));

        if(myApiService == null) {
            UserApi.Builder builder = new UserApi.Builder(AndroidHttp.newCompatibleTransport()
                    , new AndroidJsonFactory(), null)
                    .setRootUrl(consts.DEV_MODE ? consts.DEV_URL : consts.PROD_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        if (credential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            //put this in an Async task
            // look at the original balln app for the proper way to handle errors in an asyc task
            new AsyncTask<Void, Void, String>() {

                protected String doInBackground(Void... params) {
                    User user = new User();
                    user.setAlias("peze");                       // set the email address as the alias then ask the user to change it later in a noninvasive way
                    user.setCash(consts.STARTING_CASH);
                    user.setEmail("horatio.cummings@gmail.com"); // get this automatically after logging in;
                    user.setJoined("2015-09-02"); // set this to today unless the user is already a member
                    user.setRank((long) 100000000);
                    user.setPhone("540 915 2215");               // get this programmatically
                    user.setRegistrationId("regid");             // this will be supplied after calling register in an asyc task before this one

                    try {
                        myApiService.insert(user).execute();
                    } catch (IOException e) {
                        // return Collections.EMPTY_LIST;
                    }
                    return "success";
                }

                protected void onPostExecute(String msg) {
                    Snackbar.make(fab,"user registered successfuly",Snackbar.LENGTH_LONG).show();
                }
            }.execute();

            //end async task



            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
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
                       navigateToApp();
                    }
                    else {
                        chooseAccount();
                    }
                }
                break;
        }
    }

    private void navigateToApp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
