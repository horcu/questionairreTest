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
 * (build: 2016-02-18 22:11:37 UTC)
 * on 2016-03-01 at 18:13:50 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend.models.schedule.gameApi.model;

/**
 * Model definition for Weather.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the gameApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Weather extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Object condition;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Object humidity;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Object temperature;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Wind wind;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Object getCondition() {
    return condition;
  }

  /**
   * @param condition condition or {@code null} for none
   */
  public Weather setCondition(java.lang.Object condition) {
    this.condition = condition;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Object getHumidity() {
    return humidity;
  }

  /**
   * @param humidity humidity or {@code null} for none
   */
  public Weather setHumidity(java.lang.Object humidity) {
    this.humidity = humidity;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Object getTemperature() {
    return temperature;
  }

  /**
   * @param temperature temperature or {@code null} for none
   */
  public Weather setTemperature(java.lang.Object temperature) {
    this.temperature = temperature;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Wind getWind() {
    return wind;
  }

  /**
   * @param wind wind or {@code null} for none
   */
  public Weather setWind(Wind wind) {
    this.wind = wind;
    return this;
  }

  @Override
  public Weather set(String fieldName, Object value) {
    return (Weather) super.set(fieldName, value);
  }

  @Override
  public Weather clone() {
    return (Weather) super.clone();
  }

}
