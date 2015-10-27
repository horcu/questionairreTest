package com.horcu.apps.peez.custom;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.horcu.apps.common.utilities.consts;
import com.horcu.apps.peez.backend.models.userSettingsApi.UserSettingsApi;

import java.io.IOException;

/**
 * Created by hacz on 10/25/2015.
 */
public class Api {

    public static UserSettingsApi BuildUserSettingsApiService() {
       return new UserSettingsApi.Builder(AndroidHttp.newCompatibleTransport()
                    , new AndroidJsonFactory(), null)
                    .setRootUrl(consts.DEV_MODE
                            ? consts.DEV_URL
                            : consts.PROD_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    }).build();

        }
    }

