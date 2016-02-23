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
 * on 2016-02-23 at 08:19:58 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend.models.betApi.model;

/**
 * Model definition for NFLPlayer.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the betApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class NFLPlayer extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("img_url")
  private java.lang.String imgUrl;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer number;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String position;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String school;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Team team;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer tenure;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getImgUrl() {
    return imgUrl;
  }

  /**
   * @param imgUrl imgUrl or {@code null} for none
   */
  public NFLPlayer setImgUrl(java.lang.String imgUrl) {
    this.imgUrl = imgUrl;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public NFLPlayer setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getNumber() {
    return number;
  }

  /**
   * @param number number or {@code null} for none
   */
  public NFLPlayer setNumber(java.lang.Integer number) {
    this.number = number;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPosition() {
    return position;
  }

  /**
   * @param position position or {@code null} for none
   */
  public NFLPlayer setPosition(java.lang.String position) {
    this.position = position;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSchool() {
    return school;
  }

  /**
   * @param school school or {@code null} for none
   */
  public NFLPlayer setSchool(java.lang.String school) {
    this.school = school;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Team getTeam() {
    return team;
  }

  /**
   * @param team team or {@code null} for none
   */
  public NFLPlayer setTeam(Team team) {
    this.team = team;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getTenure() {
    return tenure;
  }

  /**
   * @param tenure tenure or {@code null} for none
   */
  public NFLPlayer setTenure(java.lang.Integer tenure) {
    this.tenure = tenure;
    return this;
  }

  @Override
  public NFLPlayer set(String fieldName, Object value) {
    return (NFLPlayer) super.set(fieldName, value);
  }

  @Override
  public NFLPlayer clone() {
    return (NFLPlayer) super.clone();
  }

}
