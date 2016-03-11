package com.horcu.apps.peez.view;//package com.horcu.apps.peez.ui.activities;

import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.userApi.UserApi;
import com.horcu.apps.peez.backend.models.userApi.model.User;
import com.horcu.apps.peez.backend.models.userSettingsApi.UserSettingsApi;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.Api;

import com.horcu.apps.peez.registration.RegistrationIntentService;
import com.wang.avi.AVLoadingIndicatorView;

import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;

public class IntroView extends IntroActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "IntroView";
    private SharedPreferences settings;
    User user;
    private GoogleAccountCredential credential;
    private String accountName;
    static final int REQUEST_ACCOUNT_PICKER = 2;
    private UserApi userApi = null;
    String mPhoneNumber;
    private UserSettingsApi userSettingsApi;
    private String regId;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private AVLoadingIndicatorView loader;
    private LineColorPicker colorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                boolean sentToken = settings
                        .getBoolean(consts.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    navigateToApp();
                    settings.edit().putBoolean(consts.DEVICE_REGISTERED, true).apply();
                } else {
                    Snackbar.make(loader, "token not sent", Snackbar.LENGTH_LONG).show();
                    String msg = "token was not successfully sent to the server. kill app and retry. \r OK only for dev mode. CANNOT HAPPEN IN PROD";
                    settings.edit().putBoolean(consts.DEVICE_REGISTERED, false).apply();
                    navigateToErrorPage(msg);
                }
            }
        };

        /**
         * Standard slide (like Google's intros)
         */
        addSlide(new SimpleSlide.Builder()
                .title(R.string.registration_slide)
                .description(R.string.reg_description)
             //   .image(R.drawable.reg_image)
                .background(R.color.accent_material_dark)
                .backgroundDark(R.color.accent_material_dark)
                .build());

        /**
         * Custom fragment slide for color picker
         */
//        addSlide(new FragmentSlide.Builder()
//                .background(R.color.primary)
//                .backgroundDark(R.color.primary)
//                .fragment(R.layout.fragment_color_view, R.style.AppTheme_bub)
//                .build());

        /* Enable/disable skip button */
        setSkipEnabled(true);

        /* Enable/disable finish button */
        setFinishEnabled(true);

        /* Add your own page change listeners */
        addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        settings = getSharedPreferences("Peez", 0);

        // Inflate the layout for this fragment
        colorPicker = (LineColorPicker)findViewById(R.id.picker);

        // set color palette
        colorPicker.setColors(GetColors());

        // set selected color [optional]
        //colorPicker.setSelectedColor(Color.RED);

        // set on change listener
        colorPicker.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                settings.edit().putInt(consts.FAV_COLOR, c).apply();

                View pickerView =  findViewById(R.id.color_picker_layout);
                pickerView.setVisibility(View.GONE);
                YoYo.with(Techniques.FadeOut).duration(1000).playOn(pickerView);

                findViewById(R.id.loader_layout).setVisibility(View.VISIBLE);

                CompleteRegistrationAndLogIn();
            }
        });
    }

    private void CompleteRegistrationAndLogIn() {
        // get selected color
        int color = colorPicker.getColor();

        loader = (AVLoadingIndicatorView)findViewById(R.id.loadView_reg);
        user = new User();
        userApi = Api.BuildUserApiService();
        userSettingsApi = Api.BuildUserSettingsApiService();

        settings = getSharedPreferences("Peez", 0);

        credential = GoogleAccountCredential.usingAudience(this, consts.GOOGLE_ACCOUNT_CREDENTIALS_AUDIENCE);


        String name = settings.getString(consts.PREF_ACCOUNT_NAME, null);

        if (name == null) {
            chooseAccount();
        } else {

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

    private int[] GetColors() { //TODO get these from an array of colors from config or better yet cloud storage
        return new int[]{ //TODO use better colors
                Color.parseColor("#77a0b4"),
                Color.parseColor("#ffff00"),
                Color.parseColor("#7c684D"),
                Color.parseColor("#561235"),
                Color.parseColor("#7c684D"),
                Color.parseColor("#f2c554"),
                Color.parseColor("#ae63b0"),
                Color.parseColor("#e07a24")
        };
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
}
