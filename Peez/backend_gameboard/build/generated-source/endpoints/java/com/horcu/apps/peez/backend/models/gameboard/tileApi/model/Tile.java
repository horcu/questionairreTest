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
 * (build: 2016-01-08 17:48:37 UTC)
 * on 2016-02-12 at 04:10:55 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend.models.gameboard.tileApi.model;

/**
 * Model definition for Tile.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the tileApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Tile extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String finishLine;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String neighbours;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String owner;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String piece;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String spot;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String used;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFinishLine() {
    return finishLine;
  }

  /**
   * @param finishLine finishLine or {@code null} for none
   */
  public Tile setFinishLine(java.lang.String finishLine) {
    this.finishLine = finishLine;
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
  public Tile setId(java.lang.String id) {
    this.id = id;
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
  public Tile setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNeighbours() {
    return neighbours;
  }

  /**
   * @param neighbours neighbours or {@code null} for none
   */
  public Tile setNeighbours(java.lang.String neighbours) {
    this.neighbours = neighbours;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getOwner() {
    return owner;
  }

  /**
   * @param owner owner or {@code null} for none
   */
  public Tile setOwner(java.lang.String owner) {
    this.owner = owner;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPiece() {
    return piece;
  }

  /**
   * @param piece piece or {@code null} for none
   */
  public Tile setPiece(java.lang.String piece) {
    this.piece = piece;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSpot() {
    return spot;
  }

  /**
   * @param spot spot or {@code null} for none
   */
  public Tile setSpot(java.lang.String spot) {
    this.spot = spot;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUsed() {
    return used;
  }

  /**
   * @param used used or {@code null} for none
   */
  public Tile setUsed(java.lang.String used) {
    this.used = used;
    return this;
  }

  @Override
  public Tile set(String fieldName, Object value) {
    return (Tile) super.set(fieldName, value);
  }

  @Override
  public Tile clone() {
    return (Tile) super.clone();
  }

}
