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
 * on 2016-03-12 at 07:22:08 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend.registration.model;

/**
 * Model definition for RegistrationRecord.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the registration. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class RegistrationRecord extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String regDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String token;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String userEmail;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getRegDate() {
    return regDate;
  }

  /**
   * @param regDate regDate or {@code null} for none
   */
  public RegistrationRecord setRegDate(java.lang.String regDate) {
    this.regDate = regDate;
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
  public RegistrationRecord setToken(java.lang.String token) {
    this.token = token;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUserEmail() {
    return userEmail;
  }

  /**
   * @param userEmail userEmail or {@code null} for none
   */
  public RegistrationRecord setUserEmail(java.lang.String userEmail) {
    this.userEmail = userEmail;
    return this;
  }

  @Override
  public RegistrationRecord set(String fieldName, Object value) {
    return (RegistrationRecord) super.set(fieldName, value);
  }

  @Override
  public RegistrationRecord clone() {
    return (RegistrationRecord) super.clone();
  }

}
