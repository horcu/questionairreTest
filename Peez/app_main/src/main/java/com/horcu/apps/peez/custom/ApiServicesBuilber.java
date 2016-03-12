package com.horcu.apps.peez.custom;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.horcu.apps.peez.backend.models.gameboard.pieceApi.PieceApi;
import com.horcu.apps.peez.backend.models.gameboard.tileApi.TileApi;

import com.horcu.apps.peez.backend.models.invitationApi.InvitationApi;
import com.horcu.apps.peez.backend.models.playerApi.PlayerApi;
import com.horcu.apps.peez.backend.models.userSettingsApi.UserSettingsApi;

import com.horcu.apps.peez.backend.registration.Registration;
import com.horcu.apps.peez.common.utilities.consts;

import java.io.IOException;

/**
 * Created by hacz on 10/25/2015.
 */
public class ApiServicesBuilber {

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

    public static PlayerApi BuildPlayerApiService() {
        return new PlayerApi.Builder(AndroidHttp.newCompatibleTransport()
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

    public static InvitationApi BuildInvitationApiService() {
        return new InvitationApi.Builder(AndroidHttp.newCompatibleTransport()
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

