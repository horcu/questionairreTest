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
 * on 2015-10-15 at 06:29:20 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend.models.nFLWeekApi;

/**
 * Service definition for NFLWeekApi (v1).
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
 * This service uses {@link NFLWeekApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class NFLWeekApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.20.0 of the nFLWeekApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "nFLWeekApi/v1/";

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
  public NFLWeekApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  NFLWeekApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "current".
   *
   * This request holds the parameters needed by the nFLWeekApi server.  After setting any optional
   * parameters, call the {@link Current#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public Current current() throws java.io.IOException {
    Current result = new Current();
    initialize(result);
    return result;
  }

  public class Current extends NFLWeekApiRequest<com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek> {

    private static final String REST_PATH = "nfLWeek/current";

    /**
     * Create a request for the method "current".
     *
     * This request holds the parameters needed by the the nFLWeekApi server.  After setting any
     * optional parameters, call the {@link Current#execute()} method to invoke the remote operation.
     * <p> {@link
     * Current#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected Current() {
      super(NFLWeekApi.this, "GET", REST_PATH, null, com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek.class);
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
    public Current setAlt(java.lang.String alt) {
      return (Current) super.setAlt(alt);
    }

    @Override
    public Current setFields(java.lang.String fields) {
      return (Current) super.setFields(fields);
    }

    @Override
    public Current setKey(java.lang.String key) {
      return (Current) super.setKey(key);
    }

    @Override
    public Current setOauthToken(java.lang.String oauthToken) {
      return (Current) super.setOauthToken(oauthToken);
    }

    @Override
    public Current setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Current) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Current setQuotaUser(java.lang.String quotaUser) {
      return (Current) super.setQuotaUser(quotaUser);
    }

    @Override
    public Current setUserIp(java.lang.String userIp) {
      return (Current) super.setUserIp(userIp);
    }

    @Override
    public Current set(String parameterName, Object value) {
      return (Current) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "get".
   *
   * This request holds the parameters needed by the nFLWeekApi server.  After setting any optional
   * parameters, call the {@link Get#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public Get get(java.lang.Long id) throws java.io.IOException {
    Get result = new Get(id);
    initialize(result);
    return result;
  }

  public class Get extends NFLWeekApiRequest<com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek> {

    private static final String REST_PATH = "nFLWeek/{id}";

    /**
     * Create a request for the method "get".
     *
     * This request holds the parameters needed by the the nFLWeekApi server.  After setting any
     * optional parameters, call the {@link Get#execute()} method to invoke the remote operation. <p>
     * {@link Get#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected Get(java.lang.Long id) {
      super(NFLWeekApi.this, "GET", REST_PATH, null, com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek.class);
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
    public Get setAlt(java.lang.String alt) {
      return (Get) super.setAlt(alt);
    }

    @Override
    public Get setFields(java.lang.String fields) {
      return (Get) super.setFields(fields);
    }

    @Override
    public Get setKey(java.lang.String key) {
      return (Get) super.setKey(key);
    }

    @Override
    public Get setOauthToken(java.lang.String oauthToken) {
      return (Get) super.setOauthToken(oauthToken);
    }

    @Override
    public Get setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Get) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Get setQuotaUser(java.lang.String quotaUser) {
      return (Get) super.setQuotaUser(quotaUser);
    }

    @Override
    public Get setUserIp(java.lang.String userIp) {
      return (Get) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public Get setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public Get set(String parameterName, Object value) {
      return (Get) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insert".
   *
   * This request holds the parameters needed by the nFLWeekApi server.  After setting any optional
   * parameters, call the {@link Insert#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek}
   * @return the request
   */
  public Insert insert(com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek content) throws java.io.IOException {
    Insert result = new Insert(content);
    initialize(result);
    return result;
  }

  public class Insert extends NFLWeekApiRequest<com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek> {

    private static final String REST_PATH = "nFLWeek";

    /**
     * Create a request for the method "insert".
     *
     * This request holds the parameters needed by the the nFLWeekApi server.  After setting any
     * optional parameters, call the {@link Insert#execute()} method to invoke the remote operation.
     * <p> {@link
     * Insert#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek}
     * @since 1.13
     */
    protected Insert(com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek content) {
      super(NFLWeekApi.this, "POST", REST_PATH, content, com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek.class);
    }

    @Override
    public Insert setAlt(java.lang.String alt) {
      return (Insert) super.setAlt(alt);
    }

    @Override
    public Insert setFields(java.lang.String fields) {
      return (Insert) super.setFields(fields);
    }

    @Override
    public Insert setKey(java.lang.String key) {
      return (Insert) super.setKey(key);
    }

    @Override
    public Insert setOauthToken(java.lang.String oauthToken) {
      return (Insert) super.setOauthToken(oauthToken);
    }

    @Override
    public Insert setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Insert) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Insert setQuotaUser(java.lang.String quotaUser) {
      return (Insert) super.setQuotaUser(quotaUser);
    }

    @Override
    public Insert setUserIp(java.lang.String userIp) {
      return (Insert) super.setUserIp(userIp);
    }

    @Override
    public Insert set(String parameterName, Object value) {
      return (Insert) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "list".
   *
   * This request holds the parameters needed by the nFLWeekApi server.  After setting any optional
   * parameters, call the {@link List#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public List list() throws java.io.IOException {
    List result = new List();
    initialize(result);
    return result;
  }

  public class List extends NFLWeekApiRequest<com.horcu.apps.peez.backend.models.nFLWeekApi.model.CollectionResponseNFLWeek> {

    private static final String REST_PATH = "nFLWeek";

    /**
     * Create a request for the method "list".
     *
     * This request holds the parameters needed by the the nFLWeekApi server.  After setting any
     * optional parameters, call the {@link List#execute()} method to invoke the remote operation. <p>
     * {@link List#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected List() {
      super(NFLWeekApi.this, "GET", REST_PATH, null, com.horcu.apps.peez.backend.models.nFLWeekApi.model.CollectionResponseNFLWeek.class);
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
   * Create a request for the method "patch".
   *
   * This request holds the parameters needed by the nFLWeekApi server.  After setting any optional
   * parameters, call the {@link Patch#execute()} method to invoke the remote operation.
   *
   * @param id
   * @param content the {@link com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek}
   * @return the request
   */
  public Patch patch(java.lang.Long id, com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek content) throws java.io.IOException {
    Patch result = new Patch(id, content);
    initialize(result);
    return result;
  }

  public class Patch extends NFLWeekApiRequest<com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek> {

    private static final String REST_PATH = "nFLWeek/{id}";

    /**
     * Create a request for the method "patch".
     *
     * This request holds the parameters needed by the the nFLWeekApi server.  After setting any
     * optional parameters, call the {@link Patch#execute()} method to invoke the remote operation.
     * <p> {@link
     * Patch#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @param content the {@link com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek}
     * @since 1.13
     */
    protected Patch(java.lang.Long id, com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek content) {
      super(NFLWeekApi.this, "PATCH", REST_PATH, content, com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public Patch setAlt(java.lang.String alt) {
      return (Patch) super.setAlt(alt);
    }

    @Override
    public Patch setFields(java.lang.String fields) {
      return (Patch) super.setFields(fields);
    }

    @Override
    public Patch setKey(java.lang.String key) {
      return (Patch) super.setKey(key);
    }

    @Override
    public Patch setOauthToken(java.lang.String oauthToken) {
      return (Patch) super.setOauthToken(oauthToken);
    }

    @Override
    public Patch setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Patch) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Patch setQuotaUser(java.lang.String quotaUser) {
      return (Patch) super.setQuotaUser(quotaUser);
    }

    @Override
    public Patch setUserIp(java.lang.String userIp) {
      return (Patch) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public Patch setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public Patch set(String parameterName, Object value) {
      return (Patch) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "remove".
   *
   * This request holds the parameters needed by the nFLWeekApi server.  After setting any optional
   * parameters, call the {@link Remove#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public Remove remove(java.lang.Long id) throws java.io.IOException {
    Remove result = new Remove(id);
    initialize(result);
    return result;
  }

  public class Remove extends NFLWeekApiRequest<Void> {

    private static final String REST_PATH = "nFLWeek/{id}";

    /**
     * Create a request for the method "remove".
     *
     * This request holds the parameters needed by the the nFLWeekApi server.  After setting any
     * optional parameters, call the {@link Remove#execute()} method to invoke the remote operation.
     * <p> {@link
     * Remove#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected Remove(java.lang.Long id) {
      super(NFLWeekApi.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public Remove setAlt(java.lang.String alt) {
      return (Remove) super.setAlt(alt);
    }

    @Override
    public Remove setFields(java.lang.String fields) {
      return (Remove) super.setFields(fields);
    }

    @Override
    public Remove setKey(java.lang.String key) {
      return (Remove) super.setKey(key);
    }

    @Override
    public Remove setOauthToken(java.lang.String oauthToken) {
      return (Remove) super.setOauthToken(oauthToken);
    }

    @Override
    public Remove setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Remove) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Remove setQuotaUser(java.lang.String quotaUser) {
      return (Remove) super.setQuotaUser(quotaUser);
    }

    @Override
    public Remove setUserIp(java.lang.String userIp) {
      return (Remove) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public Remove setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public Remove set(String parameterName, Object value) {
      return (Remove) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "update".
   *
   * This request holds the parameters needed by the nFLWeekApi server.  After setting any optional
   * parameters, call the {@link Update#execute()} method to invoke the remote operation.
   *
   * @param id
   * @param content the {@link com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek}
   * @return the request
   */
  public Update update(java.lang.Long id, com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek content) throws java.io.IOException {
    Update result = new Update(id, content);
    initialize(result);
    return result;
  }

  public class Update extends NFLWeekApiRequest<com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek> {

    private static final String REST_PATH = "nFLWeek/{id}";

    /**
     * Create a request for the method "update".
     *
     * This request holds the parameters needed by the the nFLWeekApi server.  After setting any
     * optional parameters, call the {@link Update#execute()} method to invoke the remote operation.
     * <p> {@link
     * Update#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @param content the {@link com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek}
     * @since 1.13
     */
    protected Update(java.lang.Long id, com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek content) {
      super(NFLWeekApi.this, "PUT", REST_PATH, content, com.horcu.apps.peez.backend.models.nFLWeekApi.model.NFLWeek.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public Update setAlt(java.lang.String alt) {
      return (Update) super.setAlt(alt);
    }

    @Override
    public Update setFields(java.lang.String fields) {
      return (Update) super.setFields(fields);
    }

    @Override
    public Update setKey(java.lang.String key) {
      return (Update) super.setKey(key);
    }

    @Override
    public Update setOauthToken(java.lang.String oauthToken) {
      return (Update) super.setOauthToken(oauthToken);
    }

    @Override
    public Update setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Update) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Update setQuotaUser(java.lang.String quotaUser) {
      return (Update) super.setQuotaUser(quotaUser);
    }

    @Override
    public Update setUserIp(java.lang.String userIp) {
      return (Update) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public Update setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public Update set(String parameterName, Object value) {
      return (Update) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link NFLWeekApi}.
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

    /** Builds a new instance of {@link NFLWeekApi}. */
    @Override
    public NFLWeekApi build() {
      return new NFLWeekApi(this);
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
     * Set the {@link NFLWeekApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setNFLWeekApiRequestInitializer(
        NFLWeekApiRequestInitializer nflweekapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(nflweekapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
