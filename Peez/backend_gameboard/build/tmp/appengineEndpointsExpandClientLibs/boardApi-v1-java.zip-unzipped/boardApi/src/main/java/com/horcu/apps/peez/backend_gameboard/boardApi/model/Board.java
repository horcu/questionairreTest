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
 * on 2016-02-29 at 18:44:13 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend_gameboard.boardApi.model;

/**
 * Model definition for Board.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the boardApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Board extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String gameKey;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Tile> tiles;

  static {
    // hack to force ProGuard to consider Tile used, since otherwise it would be stripped out
    // see https://github.com/google/google-api-java-client/issues/543
    com.google.api.client.util.Data.nullOf(Tile.class);
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getGameKey() {
    return gameKey;
  }

  /**
   * @param gameKey gameKey or {@code null} for none
   */
  public Board setGameKey(java.lang.String gameKey) {
    this.gameKey = gameKey;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Tile> getTiles() {
    return tiles;
  }

  /**
   * @param tiles tiles or {@code null} for none
   */
  public Board setTiles(java.util.List<Tile> tiles) {
    this.tiles = tiles;
    return this;
  }

  @Override
  public Board set(String fieldName, Object value) {
    return (Board) super.set(fieldName, value);
  }

  @Override
  public Board clone() {
    return (Board) super.clone();
  }

}
