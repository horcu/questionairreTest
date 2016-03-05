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
 * on 2016-03-05 at 02:36:50 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend.models.gameboard.tileApi;

/**
 * Service definition for TileApi (v1).
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
 * This service uses {@link TileApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class TileApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.21.0 of the tileApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "tileApi/v1/";

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
  public TileApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  TileApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "gettile".
   *
   * This request holds the parameters needed by the tileApi server.  After setting any optional
   * parameters, call the {@link Gettile#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public Gettile gettile(java.lang.Long id) throws java.io.IOException {
    Gettile result = new Gettile(id);
    initialize(result);
    return result;
  }

  public class Gettile extends TileApiRequest<com.horcu.apps.peez.backend.models.gameboard.tileApi.model.Tile> {

    private static final String REST_PATH = "tile/{id}";

    /**
     * Create a request for the method "gettile".
     *
     * This request holds the parameters needed by the the tileApi server.  After setting any optional
     * parameters, call the {@link Gettile#execute()} method to invoke the remote operation. <p>
     * {@link
     * Gettile#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected Gettile(java.lang.Long id) {
      super(TileApi.this, "GET", REST_PATH, null, com.horcu.apps.peez.backend.models.gameboard.tileApi.model.Tile.class);
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
    public Gettile setAlt(java.lang.String alt) {
      return (Gettile) super.setAlt(alt);
    }

    @Override
    public Gettile setFields(java.lang.String fields) {
      return (Gettile) super.setFields(fields);
    }

    @Override
    public Gettile setKey(java.lang.String key) {
      return (Gettile) super.setKey(key);
    }

    @Override
    public Gettile setOauthToken(java.lang.String oauthToken) {
      return (Gettile) super.setOauthToken(oauthToken);
    }

    @Override
    public Gettile setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Gettile) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Gettile setQuotaUser(java.lang.String quotaUser) {
      return (Gettile) super.setQuotaUser(quotaUser);
    }

    @Override
    public Gettile setUserIp(java.lang.String userIp) {
      return (Gettile) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public Gettile setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public Gettile set(String parameterName, Object value) {
      return (Gettile) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "inserttile".
   *
   * This request holds the parameters needed by the tileApi server.  After setting any optional
   * parameters, call the {@link Inserttile#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.horcu.apps.peez.backend.models.gameboard.tileApi.model.Tile}
   * @return the request
   */
  public Inserttile inserttile(com.horcu.apps.peez.backend.models.gameboard.tileApi.model.Tile content) throws java.io.IOException {
    Inserttile result = new Inserttile(content);
    initialize(result);
    return result;
  }

  public class Inserttile extends TileApiRequest<com.horcu.apps.peez.backend.models.gameboard.tileApi.model.Tile> {

    private static final String REST_PATH = "tile";

    /**
     * Create a request for the method "inserttile".
     *
     * This request holds the parameters needed by the the tileApi server.  After setting any optional
     * parameters, call the {@link Inserttile#execute()} method to invoke the remote operation. <p>
     * {@link
     * Inserttile#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.horcu.apps.peez.backend.models.gameboard.tileApi.model.Tile}
     * @since 1.13
     */
    protected Inserttile(com.horcu.apps.peez.backend.models.gameboard.tileApi.model.Tile content) {
      super(TileApi.this, "POST", REST_PATH, content, com.horcu.apps.peez.backend.models.gameboard.tileApi.model.Tile.class);
    }

    @Override
    public Inserttile setAlt(java.lang.String alt) {
      return (Inserttile) super.setAlt(alt);
    }

    @Override
    public Inserttile setFields(java.lang.String fields) {
      return (Inserttile) super.setFields(fields);
    }

    @Override
    public Inserttile setKey(java.lang.String key) {
      return (Inserttile) super.setKey(key);
    }

    @Override
    public Inserttile setOauthToken(java.lang.String oauthToken) {
      return (Inserttile) super.setOauthToken(oauthToken);
    }

    @Override
    public Inserttile setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Inserttile) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Inserttile setQuotaUser(java.lang.String quotaUser) {
      return (Inserttile) super.setQuotaUser(quotaUser);
    }

    @Override
    public Inserttile setUserIp(java.lang.String userIp) {
      return (Inserttile) super.setUserIp(userIp);
    }

    @Override
    public Inserttile set(String parameterName, Object value) {
      return (Inserttile) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "list".
   *
   * This request holds the parameters needed by the tileApi server.  After setting any optional
   * parameters, call the {@link List#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public List list() throws java.io.IOException {
    List result = new List();
    initialize(result);
    return result;
  }

  public class List extends TileApiRequest<com.horcu.apps.peez.backend.models.gameboard.tileApi.model.CollectionResponseTile> {

    private static final String REST_PATH = "tile";

    /**
     * Create a request for the method "list".
     *
     * This request holds the parameters needed by the the tileApi server.  After setting any optional
     * parameters, call the {@link List#execute()} method to invoke the remote operation. <p> {@link
     * List#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must be
     * called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected List() {
      super(TileApi.this, "GET", REST_PATH, null, com.horcu.apps.peez.backend.models.gameboard.tileApi.model.CollectionResponseTile.class);
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
   * Builder for {@link TileApi}.
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

    /** Builds a new instance of {@link TileApi}. */
    @Override
    public TileApi build() {
      return new TileApi(this);
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
     * Set the {@link TileApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setTileApiRequestInitializer(
        TileApiRequestInitializer tileapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(tileapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
