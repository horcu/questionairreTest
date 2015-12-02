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
 * Model definition for Game.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the gameApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Game extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String away;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String awayRotation;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Broadcast broadcast;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String home;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String homeRotation;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String scheduled;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String status;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Venue venue;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Weather weather;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAway() {
    return away;
  }

  /**
   * @param away away or {@code null} for none
   */
  public Game setAway(java.lang.String away) {
    this.away = away;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAwayRotation() {
    return awayRotation;
  }

  /**
   * @param awayRotation awayRotation or {@code null} for none
   */
  public Game setAwayRotation(java.lang.String awayRotation) {
    this.awayRotation = awayRotation;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Broadcast getBroadcast() {
    return broadcast;
  }

  /**
   * @param broadcast broadcast or {@code null} for none
   */
  public Game setBroadcast(Broadcast broadcast) {
    this.broadcast = broadcast;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getHome() {
    return home;
  }

  /**
   * @param home home or {@code null} for none
   */
  public Game setHome(java.lang.String home) {
    this.home = home;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getHomeRotation() {
    return homeRotation;
  }

  /**
   * @param homeRotation homeRotation or {@code null} for none
   */
  public Game setHomeRotation(java.lang.String homeRotation) {
    this.homeRotation = homeRotation;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Game setId(java.lang.String id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getScheduled() {
    return scheduled;
  }

  /**
   * @param scheduled scheduled or {@code null} for none
   */
  public Game setScheduled(java.lang.String scheduled) {
    this.scheduled = scheduled;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getStatus() {
    return status;
  }

  /**
   * @param status status or {@code null} for none
   */
  public Game setStatus(java.lang.String status) {
    this.status = status;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Venue getVenue() {
    return venue;
  }

  /**
   * @param venue venue or {@code null} for none
   */
  public Game setVenue(Venue venue) {
    this.venue = venue;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Weather getWeather() {
    return weather;
  }

  /**
   * @param weather weather or {@code null} for none
   */
  public Game setWeather(Weather weather) {
    this.weather = weather;
    return this;
  }

  @Override
  public Game set(String fieldName, Object value) {
    return (Game) super.set(fieldName, value);
  }

  @Override
  public Game clone() {
    return (Game) super.clone();
  }

}
