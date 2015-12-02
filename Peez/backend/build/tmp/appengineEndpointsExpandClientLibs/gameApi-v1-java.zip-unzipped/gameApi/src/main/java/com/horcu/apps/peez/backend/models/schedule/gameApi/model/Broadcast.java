/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2015-11-16 19:10:01 UTC)
 * on 2015-11-30 at 07:51:59 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend.models.schedule.gameApi.model;

/**
 * Model definition for Broadcast.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the gameApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Broadcast extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String internet;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String network;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String satellite;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getInternet() {
    return internet;
  }

  /**
   * @param internet internet or {@code null} for none
   */
  public Broadcast setInternet(java.lang.String internet) {
    this.internet = internet;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNetwork() {
    return network;
  }

  /**
   * @param network network or {@code null} for none
   */
  public Broadcast setNetwork(java.lang.String network) {
    this.network = network;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSatellite() {
    return satellite;
  }

  /**
   * @param satellite satellite or {@code null} for none
   */
  public Broadcast setSatellite(java.lang.String satellite) {
    this.satellite = satellite;
    return this;
  }

  @Override
  public Broadcast set(String fieldName, Object value) {
    return (Broadcast) super.set(fieldName, value);
  }

  @Override
  public Broadcast clone() {
    return (Broadcast) super.clone();
  }

}
