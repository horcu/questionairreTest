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
 * on 2016-03-03 at 17:06:55 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend.models.betApi.model;

/**
 * Model definition for Team.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the betApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Team extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String market;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private TeamColors teamColors;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Venue venue;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Team setId(java.lang.String id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getMarket() {
    return market;
  }

  /**
   * @param market market or {@code null} for none
   */
  public Team setMarket(java.lang.String market) {
    this.market = market;
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
  public Team setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public TeamColors getTeamColors() {
    return teamColors;
  }

  /**
   * @param teamColors teamColors or {@code null} for none
   */
  public Team setTeamColors(TeamColors teamColors) {
    this.teamColors = teamColors;
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
  public Team setVenue(Venue venue) {
    this.venue = venue;
    return this;
  }

  @Override
  public Team set(String fieldName, Object value) {
    return (Team) super.set(fieldName, value);
  }

  @Override
  public Team clone() {
    return (Team) super.clone();
  }

}
