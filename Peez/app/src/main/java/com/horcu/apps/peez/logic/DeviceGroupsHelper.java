/*
Copyright 2015 Google Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.horcu.apps.peez.logic;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.horcu.apps.common.utilities.consts;
import com.horcu.apps.peez.model.app.DeviceGroup;
import com.horcu.apps.peez.model.app.SenderCollection;
import com.horcu.apps.peez.model.app.Sender;
import com.horcu.apps.peez.service.LoggingService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.horcu.apps.peez.logic.HttpRequest.*;


public class DeviceGroupsHelper {

    private static final String GCM_GROUPS_ENDPOINT =
            "https://gcm-http.googleapis.com/gcm/notification";

    private final Context mContext;
    private final LoggingService.Logger mLogger;
    private final SenderCollection mSenders;


    public DeviceGroupsHelper(Context context) {
        mContext = context.getApplicationContext();
        mLogger = new LoggingService.Logger(mContext);
        mSenders = SenderCollection.getInstance(mContext);
    }

    /**
     * Execute the HTTP call to create the Device Group in background.
     *
     * <code>
     *   Content-Type: application/json
     *   Authorization: key=API_KEY
     *   project_id: SENDER_ID
     *   {
     *     "operation": "create",
     *     "notification_key_name": "appUser-Chris",
     *     "registration_ids": ["4", "8", "15", "16", "23", "42"]
     *   }
     * </code>
     */
    public void asyncCreateGroup(final View snackbarView, final String senderId, final String apiKey,
                                 final String groupName, Bundle members) {
        final Bundle newMembers = new Bundle(members);
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    com.horcu.apps.peez.logic.HttpRequest httpRequest = new com.horcu.apps.peez.logic.HttpRequest();
                    httpRequest.setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
                    httpRequest.setHeader(HEADER_AUTHORIZATION, "key=" + consts.API_KEY);
                    httpRequest.setHeader(HEADER_PROJECT_ID, consts.SENDER_ID);

                    JSONObject requestBody = new JSONObject();
                    requestBody.put("operation", "create");
                    requestBody.put("notification_key_name", groupName);
                    requestBody.put("registration_ids",
                            new JSONArray(bundleValues2Array(newMembers)));

                    httpRequest.doPost(GCM_GROUPS_ENDPOINT, requestBody.toString());

                    JSONObject responseBody = new JSONObject(httpRequest.getResponseBody());

                    if (responseBody.has("error")) {
                        mLogger.log(Log.INFO, "Group creation failed."
                                + "\ngroupName: " + groupName
                                + "\nhttpResponse:" + httpRequest.getResponseBody());
                        Snackbar.make(snackbarView, "couldnt put that group together. my bad" +
                                responseBody.getString("error"), Snackbar.LENGTH_LONG).show();
                    } else {
                        // Store the group in the local storage.
                        DeviceGroup group = new DeviceGroup();
                        group.notificationKeyName = groupName;
                        group.notificationKey = responseBody.getString("notification_key");
                        for (String name : newMembers.keySet()) {
                            group.tokens.put(name, newMembers.getString(name));
                        }

                        Sender sender = mSenders.getSender(senderId);
                        sender.groups.put(group.notificationKeyName, group);
                        mSenders.updateSender(sender);

                        mLogger.log(Log.INFO, "Group creation succeeded."
                                + "\ngroupName: " + group.notificationKeyName
                                + "\ngroupKey: " + group.notificationKey);
                        Snackbar.make(snackbarView, "done", Snackbar.LENGTH_LONG).show();
                    }
                } catch (JSONException | IOException e) {
                    mLogger.log(Log.INFO, "Exception while creating a new group"
                            + "\nerror: " + e.getMessage()
                            + "\ngroupName: " + groupName);
                    Snackbar.make(snackbarView, "couldnt put that group together. my bad" +
                            e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                return null;
            }
        }.execute();
    }

    /**
     * Execute the HTTP call to delete a Device Group.
     *
     * This is obtained by removing all the members of the group.
     */
    public void asyncDeleteGroup(final View snackbarView, final String senderId, final String apiKey,
                                 final String groupName) {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                Sender sender = mSenders.getSender(senderId);
                if (sender == null) {
                    return null;
                }
                DeviceGroup group = sender.groups.get(groupName);
                if (group == null) {
                    return null;
                }
                Bundle members2Remove = new Bundle();
                for (String name : group.tokens.keySet()) {
                    members2Remove.putString(name, group.tokens.get(name));
                }
                if (members2Remove.size() > 0) {
                    removeMembers(snackbarView, senderId, apiKey, groupName,
                            group.notificationKey, members2Remove);
                }
                sender.groups.remove(group.notificationKeyName);
                mSenders.updateSender(sender);
                return null;
            }
        }.execute();
    }

    /**
     * Execute in background the HTTP calls to add and remove members.
     */
    public void asyncUpdateGroup(final View snackbarView,  final String senderId, final String apiKey,
                                 final String groupName, final String groupKey,
                                 Bundle newMembers, Bundle removedMembers) {
        final Bundle members2Add = new Bundle(newMembers);
        final Bundle members2Remove = new Bundle(removedMembers);
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                if (members2Add.size() > 0) {
                    addMembers(snackbarView, senderId, apiKey, groupName, groupKey, members2Add);
                }
                if (members2Remove.size() > 0) {
                    removeMembers(snackbarView,senderId, apiKey, groupName, groupKey, members2Remove);
                }
                return null;
            }
        }.execute();
    }

    /**
     * Execute the HTTP call to remove registration_ids from a the Device Group.
     *
     * <code>
     *   Content-Type: application/json
     *   Authorization: key=API_KEY
     *   project_id: SENDER_ID
     *   {
     *     "operation": "add",
     *     "notification_key_name": "appUser-Chris",
     *     "notification_key": "aUniqueKey",
     *     "registration_ids": ["4", "8", "15", "16", "23", "42"]
     *   }
     * </code>
     */
    public void addMembers(final View snackbarView, String senderId, String apiKey, String groupName,
                           String groupKey, Bundle members) {
        try {
            com.horcu.apps.peez.logic.HttpRequest httpRequest = new com.horcu.apps.peez.logic.HttpRequest();
            httpRequest.setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
            httpRequest.setHeader(HEADER_AUTHORIZATION, "key=" + apiKey);
            httpRequest.setHeader(HEADER_PROJECT_ID, senderId);

            JSONObject requestBody = new JSONObject();
            requestBody.put("operation", "add");
            requestBody.put("notification_key_name", groupName);
            requestBody.put("notification_key", groupKey);
            requestBody.put("registration_ids", new JSONArray(bundleValues2Array(members)));

            httpRequest.doPost(GCM_GROUPS_ENDPOINT, requestBody.toString());

            JSONObject responseBody = new JSONObject(httpRequest.getResponseBody());

            if (responseBody.has("error")) {
                mLogger.log(Log.INFO, "Error while adding new group members."
                        + "\ngroupName: " + groupName
                        + "\ngroupKey: " + groupKey
                        + "\nhttpResponse: " + httpRequest.getResponseBody());

                Snackbar.make(snackbarView, "couldnt put that group together. my bad" +
                        responseBody.getString("error"), Snackbar.LENGTH_LONG).show();
            } else {
                // Store the group in the local storage.
                Sender sender = mSenders.getSender(senderId);
                DeviceGroup newGroup = sender.groups.get(groupName);
                for(String name : members.keySet()) {
                    newGroup.tokens.put(name, members.getString(name));
                }
                mSenders.updateSender(sender);

                mLogger.log(Log.INFO, "Group members added successfully."
                        + "\ngroupName: " + groupName
                        + "\ngroupKey: " + groupKey);
                Snackbar.make(snackbarView, "done", Snackbar.LENGTH_LONG).show();
            }
        } catch (JSONException | IOException e) {
            mLogger.log(Log.INFO, "Exception while adding new group members."
                    + "\nerror: " + e.getMessage()
                    + "\ngroupName: " + groupName
                    + "\ngroupKey: " + groupKey);
            Snackbar.make(snackbarView, "couldnt put that group together. my bad" +
                    e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Execute the HTTP call to remove registration_ids from a the Device Group.
     *
     * <code>
     *   Content-Type: application/json
     *   Authorization: key=API_KEY
     *   project_id: SENDER_ID
     *   {
     *     "operation": "remove",
     *     "notification_key_name": "appUser-Chris",
     *     "notification_key": "aUniqueKey",
     *     "registration_ids": ["4", "8", "15", "16", "23", "42"]
     *   }
     * </code>
     */
    public void removeMembers(final View snackbarView, String senderId, String apiKey, String groupName,
                              String groupKey, Bundle members) {
        try {
            com.horcu.apps.peez.logic.HttpRequest httpRequest = new com.horcu.apps.peez.logic.HttpRequest();
            httpRequest.setHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
            httpRequest.setHeader(HEADER_AUTHORIZATION, "key=" + apiKey);
            httpRequest.setHeader(HEADER_PROJECT_ID, senderId);

            JSONObject requestBody = new JSONObject();
            requestBody.put("operation", "remove");
            requestBody.put("notification_key_name", groupName);
            requestBody.put("notification_key", groupKey);
            requestBody.put("registration_ids", new JSONArray(bundleValues2Array(members)));

            httpRequest.doPost(GCM_GROUPS_ENDPOINT, requestBody.toString());

            JSONObject responseBody = new JSONObject(httpRequest.getResponseBody());

            if (responseBody.has("error")) {
                mLogger.log(Log.INFO, "Error while removing group members."
                    + "\ngroupName: " + groupName
                    + "\ngroupKey: " + groupKey
                    + "\nhttpResponse: " + httpRequest.getResponseBody());
                Snackbar.make(snackbarView, "couldnt remove member. my bad" +
                        responseBody.getString("error"), Snackbar.LENGTH_LONG).show();
            } else {
                // Store the group in the local storage.
                SenderCollection senders = SenderCollection.getInstance(mContext);
                Sender sender = senders.getSender(senderId);
                DeviceGroup newGroup = sender.groups.get(groupName);
                for(String name : members.keySet()) {
                    newGroup.tokens.remove(name);
                }
                senders.updateSender(sender);

                mLogger.log(Log.INFO, "Group members removed successfully."
                        + "\ngroupName: " + groupName
                        + "\ngroupKey: " + groupKey);
                Snackbar.make(snackbarView, "gone..bye"  , Snackbar.LENGTH_LONG).show();
            }
        } catch (JSONException | IOException e) {
            mLogger.log(Log.INFO, "Exception while removing group members."
                    + "\nerror: " + e.getMessage()
                    + "\ngroupName: " + groupName
                    + "\ngroupKey: " + groupKey);
            Snackbar.make(snackbarView, "couldnt remove them.. my bad"  , Snackbar.LENGTH_LONG).show();
        }
    }

    private List<String> bundleValues2Array(Bundle bundle) {
        ArrayList<String> values = new ArrayList<>();
        for (String key : bundle.keySet()) {
            values.add(bundle.getString(key));
        }
        return values;
    }
}