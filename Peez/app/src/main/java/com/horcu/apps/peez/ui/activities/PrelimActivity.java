//package com.horcu.apps.peez.ui.activities;
//
//import android.accounts.AccountManager;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.design.widget.Snackbar;
//import android.support.v4.content.LocalBroadcastManager;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
//import com.greenfrvr.rubberloader.RubberLoaderView;
//
//import com.horcu.apps.peez.R;
//import com.horcu.apps.peez.backend.models.userApi.UserApi;
//import com.horcu.apps.peez.backend.models.userApi.model.User;
//import com.horcu.apps.peez.backend.models.userSettingsApi.UserSettingsApi;
//import com.horcu.apps.peez.common.utilities.consts;
//import com.horcu.apps.peez.custom.Api;
//import com.horcu.apps.peez.registration.RegistrationIntentService;
//
//public class PrelimActivity extends AppCompatActivity {
//
//    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
//    private static final String TAG = "PrelimActivity";
//    private SharedPreferences settings;
//    User user;
//    private GoogleAccountCredential credential;
//    private String accountName;
//    static final int REQUEST_ACCOUNT_PICKER = 2;
//    private UserApi userApi = null;
//    String mPhoneNumber;
//    private UserSettingsApi userSettingsApi;
//    private String regId;
//    private BroadcastReceiver mRegistrationBroadcastReceiver;
//    private RubberLoaderView loader;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//     //   setContentView(R.layout.activity_prelim);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setElevation(0);
//        }
//
//        user = new User();
//        settings = getSharedPreferences("Peez", 0);
//        userApi = Api.BuildUserApiService();
//        userSettingsApi = Api.BuildUserSettingsApiService();
//
//       // loader = ((RubberLoaderView) findViewById(R.id.loader1));
//      //  loader.startLoading();
//
//        settings = getSharedPreferences("Peez", 0);
//
//        credential = GoogleAccountCredential.usingAudience(this, consts.GOOGLE_ACCOUNT_CREDENTIALS_AUDIENCE);
//        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                loader.setVisibility(View.GONE);
//
//                boolean sentToken = settings
//                        .getBoolean(consts.SENT_TOKEN_TO_SERVER, false);
//                if (sentToken) {
//                    navigateToApp();
//                    Snackbar.make(loader, "token sent", Snackbar.LENGTH_LONG).show();
//                    settings.edit().putBoolean(consts.DEVICE_REGISTERED, true).apply();
//                } else {
//                    Snackbar.make(loader, "token not sent", Snackbar.LENGTH_LONG).show();
//                    String msg = "token was not successfully sent to the server. kill app and retry. \r OK only for dev mode. CANNOT HAPPEN IN PROD";
//                    settings.edit().putBoolean(consts.DEVICE_REGISTERED, false).apply();
//                    navigateToErrorPage(msg);
//                }
//            }
//        };
//
//        String name = settings.getString(consts.PREF_ACCOUNT_NAME, null);
//
//        if (name == null) {
//            chooseAccount();
//        } else {
//
//            //check if device is registered
//            boolean isRegistered = settings.getBoolean(consts.DEVICE_REGISTERED, false);
//
//            //register the app if the device is compatible with play services
//            if (checkPlayServices()) {
//
//                if (isRegistered) {
//                    navigateToApp();
//                } else {
//                    // Start IntentService to register this application with GCM.
//                    Intent intent = new Intent(this, RegistrationIntentService.class);
//                    intent.putExtra("userName", name);
//                    startService(intent);
//                }
//            }
//        }
//    }
//
//    private void navigateToErrorPage(String msg) {
//        startActivity(new Intent(this, FailureActivity.class).putExtra("error", msg));
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(consts.REGISTRATION_COMPLETE));
//    }
//
//    @Override
//    protected void onPause() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
//        super.onPause();
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case REQUEST_ACCOUNT_PICKER:
//                if (data != null && data.getExtras() != null) {
//                    String accountName =
//                            data.getExtras().getString(
//                                    AccountManager.KEY_ACCOUNT_NAME);
//                    if (accountName != null) {
//                        credential.setSelectedAccountName(accountName);
//
//                        //check if device is registered
//                        boolean isRegistered = settings.getBoolean(consts.DEVICE_REGISTERED, false);
//
//                        //register the app if the device is compatible with play services
//                        if (checkPlayServices()) {
//
//                            if (isRegistered) {
//                                navigateToApp();
//                            } else {
//                                // Start IntentService to register this application with GCM.
//                                Intent intent = new Intent(this, RegistrationIntentService.class);
//                                intent.putExtra("userName", accountName);
//                                startService(intent);
//                            }
//                        }
//                    } else {
//                        chooseAccount();
//                    }
//                }
//                break;
//        }
//    }
//
//    private void navigateToApp() {
//        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//    }
//
//    void chooseAccount() {
//        startActivityForResult(credential.newChooseAccountIntent(),
//                REQUEST_ACCOUNT_PICKER);
//    }
//
//    /**
//     * Check the device to make sure it has the Google Play Services APK. If
//     * it doesn't, display a dialog that allows users to download the APK from
//     * the Google Play Store or enable it in the device's system settings.
//     */
//    private boolean checkPlayServices() {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (apiAvailability.isUserResolvableError(resultCode)) {
//                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
//                        .show();
//            } else {
//                Log.i(TAG, "This device is not supported.");
//                finish();
//            }
//            return false;
//        }
//        return true;
//    }
//}
