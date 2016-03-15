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
 * on 2016-03-15 at 04:06:39 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend_gameboard.gameApi.model;

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
  private java.lang.String boardKey;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String gameId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String inviteKey;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Player playerTurn;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Player> players;

  static {
    // hack to force ProGuard to consider Player used, since otherwise it would be stripped out
    // see https://github.com/google/google-api-java-client/issues/543
    com.google.api.client.util.Data.nullOf(Player.class);
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getBoardKey() {
    return boardKey;
  }

  /**
   * @param boardKey boardKey or {@code null} for none
   */
  public Game setBoardKey(java.lang.String boardKey) {
    this.boardKey = boardKey;
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
  public Game setGameId(java.lang.String gameId) {
    this.gameId = gameId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getInviteKey() {
    return inviteKey;
  }

  /**
   * @param inviteKey inviteKey or {@code null} for none
   */
  public Game setInviteKey(java.lang.String inviteKey) {
    this.inviteKey = inviteKey;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Player getPlayerTurn() {
    return playerTurn;
  }

  /**
   * @param playerTurn playerTurn or {@code null} for none
   */
  public Game setPlayerTurn(Player playerTurn) {
    this.playerTurn = playerTurn;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Player> getPlayers() {
    return players;
  }

  /**
   * @param players players or {@code null} for none
   */
  public Game setPlayers(java.util.List<Player> players) {
    this.players = players;
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
