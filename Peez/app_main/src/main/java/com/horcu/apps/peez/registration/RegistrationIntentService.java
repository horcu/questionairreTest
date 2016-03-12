/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.horcu.apps.peez.registration;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.horcu.apps.peez.backend.models.playerApi.PlayerApi;
import com.horcu.apps.peez.backend.models.playerApi.model.Player;
import com.horcu.apps.peez.backend.models.userSettingsApi.UserSettingsApi;
import com.horcu.apps.peez.backend.models.userSettingsApi.model.UserSettings;
import com.horcu.apps.peez.backend.registration.Registration;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.ApiServicesBuilber;

import java.io.IOException;
import java.util.Date;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global", consts.TEST_GAME_TOPIC};
    private PlayerApi playerApi;
    private Registration registrationApi = null;
    private SharedPreferences settings;
    private GoogleAccountCredential credential;
    private UserSettingsApi userSettingsApi;
    private GoogleCloudMessaging gcm;

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
        settings = getSharedPreferences("Peez", 0);
        String uName = intent.getExtras().getString("userName", null);
        credential = GoogleAccountCredential.usingAudience(this, consts.GOOGLE_ACCOUNT_CREDENTIALS_AUDIENCE);
        credential.setSelectedAccountName(uName);
        registrationApi = ApiServicesBuilber.BuildRegistrationApiService();
        playerApi = ApiServicesBuilber.BuildPlayerApiService();
        userSettingsApi = ApiServicesBuilber.BuildUserSettingsApiService();

            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(consts.SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            Log.i(TAG, "GCM Registration Token: " + token);

            if(token.equals("")) return;

            //TODO should we refresh the tokens somewhere on the server
            //TODO the registration record ideally should include the email address (or whatever unique identifyer that is settled on) of the user.

            String mPhoneNumber = "";// getPhoneNumber();

            String email = credential.getSelectedAccountName() != null ? credential.getSelectedAccountName() : uName;

            //add or update the user
            Player player = addOrUpdateUserRecord(token, mPhoneNumber, email);

            if(player == null)
                return;
            //now add the registration records
            addRegistrationRecord(token, email);

            //add or update the userSettings
            //addDefaultUserSettingForNewUser(user);

            //save regId and account name
            SharedPreferences.Editor editor = settings.edit();
          //  editor.putString(consts.REG_KEY, regId);
            editor.putString(consts.REG_ID, token);
            editor.putString(consts.PREF_ACCOUNT_NAME, player != null ? player.getUserName() : email);
            editor.apply();

            // Subscribe to topic channels
            subscribeTopics(token);
           // subscribeTopics("ballrz"); //default app info channel
           // subscribeTopics("public"); //default public channel

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            settings.edit().putBoolean(consts.SENT_TOKEN_TO_SERVER, true).apply();
            // [END register_for_gcm]
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            settings.edit().putBoolean(consts.SENT_TOKEN_TO_SERVER, false).apply();
        }
        // Notify UI that registration has completed.
        Intent registrationComplete = new Intent(consts.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }


    private String getPhoneNumber() {
        //get the phone number
        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return tMgr.getLine1Number();
    }

    @Nullable
    private Player addOrUpdateUserRecord(String token, String mPhoneNumber, String username) throws IOException {
        Player user = null;
        try {
            user = playerApi.get(username).execute();

            if (user == null) {
                user = makeNewUser(token, mPhoneNumber, username);
            } else {
                if (user.getToken().equals(token))
                    return user;

                user.setToken(token);
                playerApi.update(username, user);
            }

        } catch (IOException e) {
            user = makeNewUser(token, mPhoneNumber, username);
            e.printStackTrace();
            // no user found so new record needed
        }
        return user;
    }

    private void addRegistrationRecord(String token, String email) {
        try {
            Registration.Register record = registrationApi.register(token, email);
            record.execute();

        } catch (IOException e) {
            e.printStackTrace();
            //registration failed
        }
    }

    private void addDefaultUserSettingForNewUser(Player user) throws IOException {
        //add default user settings to new user
        UserSettings uSettings = new UserSettings();
        uSettings.setName(user.getEmail());
        // uSettings.setEmail(user.getEmail());
        String val = "test";
        uSettings.setValue(val);

        UserSettings uset = userSettingsApi.get(user.getEmail()).execute();
        if (uset == null)
            userSettingsApi.insert(uSettings).execute();
        else {
            if (uSettings.getValue().equals(val))
                return;
            userSettingsApi.update(user.getEmail(), uSettings).execute();
        }
    }

    @NonNull
    private Player makeNewUser(String token, String mPhoneNumber, String username) throws IOException {
        Player user;
        user = new Player();
        user.setAlias("");                       // set the email address as the alias then ask the user to change it later in a noninvasive way
        user.setCash(consts.STARTING_CASH);
        user.setUserName(username); // get this automatically after logging in;
        user.setEmail(username); // get this automatically after logging in;
        user.setJoined(new Date().toString()); // set this to today unless the user is already a member
        user.setRank(consts.STARTING_RANK);
        user.setPhone(mPhoneNumber);
        user.setToken(token);
        playerApi.insert(user).execute();
        return user;
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]
}
