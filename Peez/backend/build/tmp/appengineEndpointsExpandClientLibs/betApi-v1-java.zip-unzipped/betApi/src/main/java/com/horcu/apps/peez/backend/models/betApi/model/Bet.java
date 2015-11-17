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
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-08-03 17:34:38 UTC)
 * on 2015-11-17 at 05:26:37 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend.models.betApi.model;

/**
 * Model definition for Bet.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the betApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Bet extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> acceptersRegIds;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> bet;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime betAcceptedAt;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime betDeclinedAt;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String betId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime betMadeAt;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String betterRegId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> declinersRegIds;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String gameId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private NFLPlayer player;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Team team;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double wager;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getAcceptersRegIds() {
    return acceptersRegIds;
  }

  /**
   * @param acceptersRegIds acceptersRegIds or {@code null} for none
   */
  public Bet setAcceptersRegIds(java.util.List<java.lang.String> acceptersRegIds) {
    this.acceptersRegIds = acceptersRegIds;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getBet() {
    return bet;
  }

  /**
   * @param bet bet or {@code null} for none
   */
  public Bet setBet(java.util.List<java.lang.String> bet) {
    this.bet = bet;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getBetAcceptedAt() {
    return betAcceptedAt;
  }

  /**
   * @param betAcceptedAt betAcceptedAt or {@code null} for none
   */
  public Bet setBetAcceptedAt(com.google.api.client.util.DateTime betAcceptedAt) {
    this.betAcceptedAt = betAcceptedAt;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getBetDeclinedAt() {
    return betDeclinedAt;
  }

  /**
   * @param betDeclinedAt betDeclinedAt or {@code null} for none
   */
  public Bet setBetDeclinedAt(com.google.api.client.util.DateTime betDeclinedAt) {
    this.betDeclinedAt = betDeclinedAt;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getBetId() {
    return betId;
  }

  /**
   * @param betId betId or {@code null} for none
   */
  public Bet setBetId(java.lang.String betId) {
    this.betId = betId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getBetMadeAt() {
    return betMadeAt;
  }

  /**
   * @param betMadeAt betMadeAt or {@code null} for none
   */
  public Bet setBetMadeAt(com.google.api.client.util.DateTime betMadeAt) {
    this.betMadeAt = betMadeAt;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getBetterRegId() {
    return betterRegId;
  }

  /**
   * @param betterRegId betterRegId or {@code null} for none
   */
  public Bet setBetterRegId(java.lang.String betterRegId) {
    this.betterRegId = betterRegId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getDeclinersRegIds() {
    return declinersRegIds;
  }

  /**
   * @param declinersRegIds declinersRegIds or {@code null} for none
   */
  public Bet setDeclinersRegIds(java.util.List<java.lang.String> declinersRegIds) {
    this.declinersRegIds = declinersRegIds;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getGameId() {
    return gameId;
  }

  /**
   * @param gameId gameId or {@code null} for none
   */
  public Bet setGameId(java.lang.String gameId) {
    this.gameId = gameId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public NFLPlayer getPlayer() {
    return player;
  }

  /**
   * @param player player or {@code null} for none
   */
  public Bet setPlayer(NFLPlayer player) {
    this.player = player;
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
  public Bet setTeam(Team team) {
    this.team = team;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getWager() {
    return wager;
  }

  /**
   * @param wager wager or {@code null} for none
   */
  public Bet setWager(java.lang.Double wager) {
    this.wager = wager;
    return this;
  }

  @Override
  public Bet set(String fieldName, Object value) {
    return (Bet) super.set(fieldName, value);
  }

  @Override
  public Bet clone() {
    return (Bet) super.clone();
  }

}
