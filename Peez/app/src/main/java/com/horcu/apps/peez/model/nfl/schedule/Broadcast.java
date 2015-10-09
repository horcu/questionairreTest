package com.horcu.apps.peez.model.nfl.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hcummings on 10/9/2015.
 */
public class Broadcast {
        @SerializedName("network")
        @Expose
        private String network;
        @SerializedName("satellite")
        @Expose
        private String satellite;
        @SerializedName("internet")
        @Expose
        private String internet;

        /**
         * No args constructor for use in serialization
         *
         */
        public Broadcast() {
        }

        /**
         *
         * @param satellite
         * @param internet
         * @param network
         */
        public Broadcast(String network, String satellite, String internet) {
            this.network = network;
            this.satellite = satellite;
            this.internet = internet;
        }

        /**
         *
         * @return
         * The network
         */
        public String getNetwork() {
            return network;
        }

        /**
         *
         * @param network
         * The network
         */
        public void setNetwork(String network) {
            this.network = network;
        }

        public Broadcast withNetwork(String network) {
            this.network = network;
            return this;
        }

        /**
         *
         * @return
         * The satellite
         */
        public String getSatellite() {
            return satellite;
        }

        /**
         *
         * @param satellite
         * The satellite
         */
        public void setSatellite(String satellite) {
            this.satellite = satellite;
        }

        public Broadcast withSatellite(String satellite) {
            this.satellite = satellite;
            return this;
        }

        /**
         *
         * @return
         * The internet
         */
        public String getInternet() {
            return internet;
        }

        /**
         *
         * @param internet
         * The internet
         */
        public void setInternet(String internet) {
            this.internet = internet;
        }

        public Broadcast withInternet(String internet) {
            this.internet = internet;
            return this;
        }

    }
