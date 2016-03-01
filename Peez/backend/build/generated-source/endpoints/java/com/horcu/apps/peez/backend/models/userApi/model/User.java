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
 * on 2016-02-29 at 18:44:29 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend.models.userApi.model;

/**
 * Model definition for User.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the userApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class User extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String alias;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double cash;

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
  private java.lang.String registrationId;

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
  public User setAlias(java.lang.String alias) {
    this.alias = alias;
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
  public User setCash(java.lang.Double cash) {
    this.cash = cash;
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
  public User setEmail(java.lang.String email) {
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
  public User setImageUri(java.lang.String imageUri) {
    this.imageUri = imageUri;
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
  public User setJoined(java.lang.String joined) {
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
  public User setPhone(java.lang.String phone) {
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
  public User setRank(java.lang.Long rank) {
    this.rank = rank;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getRegistrationId() {
    return registrationId;
  }

  /**
   * @param registrationId registrationId or {@code null} for none
   */
  public User setRegistrationId(java.lang.String registrationId) {
    this.registrationId = registrationId;
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
  public User setUserName(java.lang.String userName) {
    this.userName = userName;
    return this;
  }

  @Override
  public User set(String fieldName, Object value) {
    return (User) super.set(fieldName, value);
  }

  @Override
  public User clone() {
    return (User) super.clone();
  }

}
