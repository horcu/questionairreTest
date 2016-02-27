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
 * on 2016-02-27 at 19:11:19 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend.models.betApi.model;

/**
 * Model definition for TeamColors.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the betApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class TeamColors extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String primaryColor;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String secondaryColor;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String tertiaryColor;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPrimaryColor() {
    return primaryColor;
  }

  /**
   * @param primaryColor primaryColor or {@code null} for none
   */
  public TeamColors setPrimaryColor(java.lang.String primaryColor) {
    this.primaryColor = primaryColor;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSecondaryColor() {
    return secondaryColor;
  }

  /**
   * @param secondaryColor secondaryColor or {@code null} for none
   */
  public TeamColors setSecondaryColor(java.lang.String secondaryColor) {
    this.secondaryColor = secondaryColor;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTertiaryColor() {
    return tertiaryColor;
  }

  /**
   * @param tertiaryColor tertiaryColor or {@code null} for none
   */
  public TeamColors setTertiaryColor(java.lang.String tertiaryColor) {
    this.tertiaryColor = tertiaryColor;
    return this;
  }

  @Override
  public TeamColors set(String fieldName, Object value) {
    return (TeamColors) super.set(fieldName, value);
  }

  @Override
  public TeamColors clone() {
    return (TeamColors) super.clone();
  }

}
