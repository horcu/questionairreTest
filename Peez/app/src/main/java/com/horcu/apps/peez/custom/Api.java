package com.horcu.apps.peez.custom;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.horcu.apps.common.utilities.consts;
import com.horcu.apps.peez.backend.models.betApi.BetApi;
import com.horcu.apps.peez.backend.models.gameboard.pieceApi.PieceApi;
import com.horcu.apps.peez.backend.models.gameboard.tileApi.TileApi;
import com.horcu.apps.peez.backend.models.misc.betStructApi.BetStructApi;
import com.horcu.apps.peez.backend.models.userApi.UserApi;
import com.horcu.apps.peez.backend.models.userSettingsApi.UserSettingsApi;
import com.horcu.apps.peez.backend.registration.Registration;

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

    public static UserApi BuildUserApiService() {
        return new UserApi.Builder(AndroidHttp.newCompatibleTransport()
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

    public static Registration BuildRegistrationApiService() {
        return new Registration.Builder(AndroidHttp.newCompatibleTransport()
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

    public static BetStructApi BuildBetStructureApiService() {
        return new BetStructApi.Builder(AndroidHttp.newCompatibleTransport()
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

    public static BetApi BuildBetApiService() {
        return new BetApi.Builder(AndroidHttp.newCompatibleTransport()
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

    public static TileApi BuildTileApiService() {
        return new TileApi.Builder(AndroidHttp.newCompatibleTransport()
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

    public static PieceApi BuildPieceApiService() {
        return new PieceApi.Builder(AndroidHttp.newCompatibleTransport()
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

