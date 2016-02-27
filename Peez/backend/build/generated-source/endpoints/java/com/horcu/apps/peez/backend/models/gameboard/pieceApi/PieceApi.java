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
 * on 2016-02-27 at 19:11:41 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend.models.gameboard.pieceApi;

/**
 * Service definition for PieceApi (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link PieceApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class PieceApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.21.0 of the pieceApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://ballrz-7d916.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "pieceApi/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public PieceApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  PieceApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getpiece".
   *
   * This request holds the parameters needed by the pieceApi server.  After setting any optional
   * parameters, call the {@link Getpiece#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public Getpiece getpiece(java.lang.Long id) throws java.io.IOException {
    Getpiece result = new Getpiece(id);
    initialize(result);
    return result;
  }

  public class Getpiece extends PieceApiRequest<com.horcu.apps.peez.backend.models.gameboard.pieceApi.model.Piece> {

    private static final String REST_PATH = "piece/{id}";

    /**
     * Create a request for the method "getpiece".
     *
     * This request holds the parameters needed by the the pieceApi server.  After setting any
     * optional parameters, call the {@link Getpiece#execute()} method to invoke the remote operation.
     * <p> {@link
     * Getpiece#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected Getpiece(java.lang.Long id) {
      super(PieceApi.this, "GET", REST_PATH, null, com.horcu.apps.peez.backend.models.gameboard.pieceApi.model.Piece.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public Getpiece setAlt(java.lang.String alt) {
      return (Getpiece) super.setAlt(alt);
    }

    @Override
    public Getpiece setFields(java.lang.String fields) {
      return (Getpiece) super.setFields(fields);
    }

    @Override
    public Getpiece setKey(java.lang.String key) {
      return (Getpiece) super.setKey(key);
    }

    @Override
    public Getpiece setOauthToken(java.lang.String oauthToken) {
      return (Getpiece) super.setOauthToken(oauthToken);
    }

    @Override
    public Getpiece setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Getpiece) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Getpiece setQuotaUser(java.lang.String quotaUser) {
      return (Getpiece) super.setQuotaUser(quotaUser);
    }

    @Override
    public Getpiece setUserIp(java.lang.String userIp) {
      return (Getpiece) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public Getpiece setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public Getpiece set(String parameterName, Object value) {
      return (Getpiece) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertpiece".
   *
   * This request holds the parameters needed by the pieceApi server.  After setting any optional
   * parameters, call the {@link Insertpiece#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.horcu.apps.peez.backend.models.gameboard.pieceApi.model.Piece}
   * @return the request
   */
  public Insertpiece insertpiece(com.horcu.apps.peez.backend.models.gameboard.pieceApi.model.Piece content) throws java.io.IOException {
    Insertpiece result = new Insertpiece(content);
    initialize(result);
    return result;
  }

  public class Insertpiece extends PieceApiRequest<com.horcu.apps.peez.backend.models.gameboard.pieceApi.model.Piece> {

    private static final String REST_PATH = "piece";

    /**
     * Create a request for the method "insertpiece".
     *
     * This request holds the parameters needed by the the pieceApi server.  After setting any
     * optional parameters, call the {@link Insertpiece#execute()} method to invoke the remote
     * operation. <p> {@link
     * Insertpiece#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.horcu.apps.peez.backend.models.gameboard.pieceApi.model.Piece}
     * @since 1.13
     */
    protected Insertpiece(com.horcu.apps.peez.backend.models.gameboard.pieceApi.model.Piece content) {
      super(PieceApi.this, "POST", REST_PATH, content, com.horcu.apps.peez.backend.models.gameboard.pieceApi.model.Piece.class);
    }

    @Override
    public Insertpiece setAlt(java.lang.String alt) {
      return (Insertpiece) super.setAlt(alt);
    }

    @Override
    public Insertpiece setFields(java.lang.String fields) {
      return (Insertpiece) super.setFields(fields);
    }

    @Override
    public Insertpiece setKey(java.lang.String key) {
      return (Insertpiece) super.setKey(key);
    }

    @Override
    public Insertpiece setOauthToken(java.lang.String oauthToken) {
      return (Insertpiece) super.setOauthToken(oauthToken);
    }

    @Override
    public Insertpiece setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Insertpiece) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Insertpiece setQuotaUser(java.lang.String quotaUser) {
      return (Insertpiece) super.setQuotaUser(quotaUser);
    }

    @Override
    public Insertpiece setUserIp(java.lang.String userIp) {
      return (Insertpiece) super.setUserIp(userIp);
    }

    @Override
    public Insertpiece set(String parameterName, Object value) {
      return (Insertpiece) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "list".
   *
   * This request holds the parameters needed by the pieceApi server.  After setting any optional
   * parameters, call the {@link List#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public List list() throws java.io.IOException {
    List result = new List();
    initialize(result);
    return result;
  }

  public class List extends PieceApiRequest<com.horcu.apps.peez.backend.models.gameboard.pieceApi.model.CollectionResponsePiece> {

    private static final String REST_PATH = "tile";

    /**
     * Create a request for the method "list".
     *
     * This request holds the parameters needed by the the pieceApi server.  After setting any
     * optional parameters, call the {@link List#execute()} method to invoke the remote operation. <p>
     * {@link List#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected List() {
      super(PieceApi.this, "GET", REST_PATH, null, com.horcu.apps.peez.backend.models.gameboard.pieceApi.model.CollectionResponsePiece.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public List setAlt(java.lang.String alt) {
      return (List) super.setAlt(alt);
    }

    @Override
    public List setFields(java.lang.String fields) {
      return (List) super.setFields(fields);
    }

    @Override
    public List setKey(java.lang.String key) {
      return (List) super.setKey(key);
    }

    @Override
    public List setOauthToken(java.lang.String oauthToken) {
      return (List) super.setOauthToken(oauthToken);
    }

    @Override
    public List setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (List) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public List setQuotaUser(java.lang.String quotaUser) {
      return (List) super.setQuotaUser(quotaUser);
    }

    @Override
    public List setUserIp(java.lang.String userIp) {
      return (List) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public List setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer limit;

    /**

     */
    public java.lang.Integer getLimit() {
      return limit;
    }

    public List setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public List set(String parameterName, Object value) {
      return (List) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link PieceApi}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link PieceApi}. */
    @Override
    public PieceApi build() {
      return new PieceApi(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link PieceApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setPieceApiRequestInitializer(
        PieceApiRequestInitializer pieceapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(pieceapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
