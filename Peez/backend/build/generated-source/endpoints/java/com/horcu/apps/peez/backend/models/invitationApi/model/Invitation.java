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
 * (build: 2016-03-25 20:06:55 UTC)
 * on 2016-03-28 at 21:27:35 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend.models.invitationApi.model;

/**
 * Model definition for Invitation.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the invitationApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Invitation extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private GameInvite invite;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Player maker;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Player> users;

  static {
    // hack to force ProGuard to consider Player used, since otherwise it would be stripped out
    // see https://github.com/google/google-api-java-client/issues/543
    com.google.api.client.util.Data.nullOf(Player.class);
  }

  /**
   * @return value or {@code null} for none
   */
  public GameInvite getInvite() {
    return invite;
  }

  /**
   * @param invite invite or {@code null} for none
   */
  public Invitation setInvite(GameInvite invite) {
    this.invite = invite;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Player getMaker() {
    return maker;
  }

  /**
   * @param maker maker or {@code null} for none
   */
  public Invitation setMaker(Player maker) {
    this.maker = maker;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Player> getUsers() {
    return users;
  }

  /**
   * @param users users or {@code null} for none
   */
  public Invitation setUsers(java.util.List<Player> users) {
    this.users = users;
    return this;
  }

  @Override
  public Invitation set(String fieldName, Object value) {
    return (Invitation) super.set(fieldName, value);
  }

  @Override
  public Invitation clone() {
    return (Invitation) super.clone();
  }

}
