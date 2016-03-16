package com.horcu.apps.peez.view.activities;//package com.horcu.apps.peez.ui.activities;

import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.playerApi.PlayerApi;
import com.horcu.apps.peez.backend.models.playerApi.model.Player;
import com.horcu.apps.peez.backend.models.userSettingsApi.UserSettingsApi;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.ApiServicesBuilber;
import com.horcu.apps.peez.registration.RegistrationIntentService;
import com.horcu.apps.peez.view.fragments.ColorPickerFragment;
import com.horcu.apps.peez.view.fragments.ColorPickerFragment.OnColorChosenListener;


public class IntroView extends IntroActivity implements OnColorChosenListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "IntroView";
    private SharedPreferences settings;
    Player user = null;
    private GoogleAccountCredential credential;
    private String accountName;
    static final int REQUEST_ACCOUNT_PICKER = 2;
    private PlayerApi playerApi = null;
    String mPhoneNumber;
    private UserSettingsApi userSettingsApi;
    private String regId;
    private BroadcastReceiver mRegistrationBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion <= Build.VERSION_CODES.KITKAT){
            // Do something for versions below lollipop
           setContentView(R.layout.activity_reg);
            CompleteRegistrationAndLogIn();
                  }
         else {
            isFullscreen();
            // else do the lollipop doo babeee!
            super.onCreate(savedInstanceState);
            //  setContentView(R.layout.activity_reg);


         /* Enable/disable skip button */
               setSkipEnabled(false);

        /* Enable/disable finish button */
               setFinishEnabled(false);

//        /* Add your own page change listeners */
//        addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//            @Override
//            public void onPageSelected(int position) {
//            }
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });

                addSlide(new FragmentSlide.Builder()
                        .background(R.color.white)
                        .backgroundDark(R.color.white)
                        .fragment(new ColorPickerFragment())
                        .build());
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                boolean sentToken = settings.getBoolean(consts.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    navigateToApp();
                    settings.edit().putBoolean(consts.DEVICE_REGISTERED, true).apply();
                } else {
                    //  Snackbar.make(loader, "token not sent", Snackbar.LENGTH_LONG).show();
                    String msg = "token was not successfully sent to the server. kill app and retry. \r OK only for dev mode. CANNOT HAPPEN IN PROD";
                    settings.edit().putBoolean(consts.DEVICE_REGISTERED, false).apply();
                    navigateToErrorPage(msg);
                }
            }
        };
       }

    @NonNull
    private int GetFavoriteColor() {
        if(settings == null)
            settings = getSharedPreferences("Peez", 0);

       return settings.getInt(consts.FAV_COLOR, 0);
    }

    private boolean deviceRegistered() {
        if(settings == null)
            settings = getSharedPreferences("Peez", 0);

        return settings.getBoolean(consts.DEVICE_REGISTERED, false);
    }

    private void CompleteRegistrationAndLogIn() {

        playerApi = ApiServicesBuilber.BuildPlayerApiService();
        userSettingsApi = ApiServicesBuilber.BuildUserSettingsApiService();

        settings = getSharedPreferences("Peez", 0);

            credential = GoogleAccountCredential.usingAudience(this, consts.GOOGLE_ACCOUNT_CREDENTIALS_AUDIENCE);

        String name = settings.getString(consts.PREF_ACCOUNT_NAME, null);

        if (name == null) {
            chooseAccount();
        } else {

            //register the app if the device is compatible with play services
            if (checkPlayServices()) {

                if (deviceRegistered()) {
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
        startActivity(new Intent(this, ErrorView.class).putExtra("error", msg));
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
        startActivity(new Intent(getApplicationContext(), MainView.class));
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

    @Override
    public void OnColorChosen(int color) {
        CompleteRegistrationAndLogIn();
    }
}
