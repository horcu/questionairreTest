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
 * on 2016-03-16 at 04:46:50 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend.models.invitationApi.model;

/**
 * Model definition for Player.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the invitationApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Player extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String alias;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean canBeMessaged;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double cash;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean currentlyOnLine;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean currentlyPlaying;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String email;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String imageUri;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean inGameWithUser;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String joined;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String phone;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long rank;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String token;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String userName;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAlias() {
    return alias;
  }

  /**
   * @param alias alias or {@code null} for none
   */
  public Player setAlias(java.lang.String alias) {
    this.alias = alias;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getCanBeMessaged() {
    return canBeMessaged;
  }

  /**
   * @param canBeMessaged canBeMessaged or {@code null} for none
   */
  public Player setCanBeMessaged(java.lang.Boolean canBeMessaged) {
    this.canBeMessaged = canBeMessaged;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getCash() {
    return cash;
  }

  /**
   * @param cash cash or {@code null} for none
   */
  public Player setCash(java.lang.Double cash) {
    this.cash = cash;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getCurrentlyOnLine() {
    return currentlyOnLine;
  }

  /**
   * @param currentlyOnLine currentlyOnLine or {@code null} for none
   */
  public Player setCurrentlyOnLine(java.lang.Boolean currentlyOnLine) {
    this.currentlyOnLine = currentlyOnLine;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getCurrentlyPlaying() {
    return currentlyPlaying;
  }

  /**
   * @param currentlyPlaying currentlyPlaying or {@code null} for none
   */
  public Player setCurrentlyPlaying(java.lang.Boolean currentlyPlaying) {
    this.currentlyPlaying = currentlyPlaying;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEmail() {
    return email;
  }

  /**
   * @param email email or {@code null} for none
   */
  public Player setEmail(java.lang.String email) {
    this.email = email;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getImageUri() {
    return imageUri;
  }

  /**
   * @param imageUri imageUri or {@code null} for none
   */
  public Player setImageUri(java.lang.String imageUri) {
    this.imageUri = imageUri;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getInGameWithUser() {
    return inGameWithUser;
  }

  /**
   * @param inGameWithUser inGameWithUser or {@code null} for none
   */
  public Player setInGameWithUser(java.lang.Boolean inGameWithUser) {
    this.inGameWithUser = inGameWithUser;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getJoined() {
    return joined;
  }

  /**
   * @param joined joined or {@code null} for none
   */
  public Player setJoined(java.lang.String joined) {
    this.joined = joined;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPhone() {
    return phone;
  }

  /**
   * @param phone phone or {@code null} for none
   */
  public Player setPhone(java.lang.String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getRank() {
    return rank;
  }

  /**
   * @param rank rank or {@code null} for none
   */
  public Player setRank(java.lang.Long rank) {
    this.rank = rank;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getToken() {
    return token;
  }

  /**
   * @param token token or {@code null} for none
   */
  public Player setToken(java.lang.String token) {
    this.token = token;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUserName() {
    return userName;
  }

  /**
   * @param userName userName or {@code null} for none
   */
  public Player setUserName(java.lang.String userName) {
    this.userName = userName;
    return this;
  }

  @Override
  public Player set(String fieldName, Object value) {
    return (Player) super.set(fieldName, value);
  }

  @Override
  public Player clone() {
    return (Player) super.clone();
  }

}