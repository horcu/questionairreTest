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
 * on 2015-10-09 at 01:17:04 UTC 
 * Modify at your own risk.
 */

package com.horcu.apps.peez.backend.storage.cloudStorageApi;

/**
 * Service definition for CloudStorageApi (v1).
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
 * This service uses {@link CloudStorageApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class CloudStorageApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.20.0 of the cloudStorageApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "cloudStorageApi/v1/";

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
  public CloudStorageApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  CloudStorageApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getCloudStorage".
   *
   * This request holds the parameters needed by the cloudStorageApi server.  After setting any
   * optional parameters, call the {@link GetCloudStorage#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetCloudStorage getCloudStorage(java.lang.Long id) throws java.io.IOException {
    GetCloudStorage result = new GetCloudStorage(id);
    initialize(result);
    return result;
  }

  public class GetCloudStorage extends CloudStorageApiRequest<com.horcu.apps.peez.backend.storage.cloudStorageApi.model.CloudStorage> {

    private static final String REST_PATH = "cloudstorage/{id}";

    /**
     * Create a request for the method "getCloudStorage".
     *
     * This request holds the parameters needed by the the cloudStorageApi server.  After setting any
     * optional parameters, call the {@link GetCloudStorage#execute()} method to invoke the remote
     * operation. <p> {@link GetCloudStorage#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetCloudStorage(java.lang.Long id) {
      super(CloudStorageApi.this, "GET", REST_PATH, null, com.horcu.apps.peez.backend.storage.cloudStorageApi.model.CloudStorage.class);
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
    public GetCloudStorage setAlt(java.lang.String alt) {
      return (GetCloudStorage) super.setAlt(alt);
    }

    @Override
    public GetCloudStorage setFields(java.lang.String fields) {
      return (GetCloudStorage) super.setFields(fields);
    }

    @Override
    public GetCloudStorage setKey(java.lang.String key) {
      return (GetCloudStorage) super.setKey(key);
    }

    @Override
    public GetCloudStorage setOauthToken(java.lang.String oauthToken) {
      return (GetCloudStorage) super.setOauthToken(oauthToken);
    }

    @Override
    public GetCloudStorage setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetCloudStorage) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetCloudStorage setQuotaUser(java.lang.String quotaUser) {
      return (GetCloudStorage) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetCloudStorage setUserIp(java.lang.String userIp) {
      return (GetCloudStorage) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetCloudStorage setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetCloudStorage set(String parameterName, Object value) {
      return (GetCloudStorage) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertCloudStorage".
   *
   * This request holds the parameters needed by the cloudStorageApi server.  After setting any
   * optional parameters, call the {@link InsertCloudStorage#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.horcu.apps.peez.backend.storage.cloudStorageApi.model.CloudStorage}
   * @return the request
   */
  public InsertCloudStorage insertCloudStorage(com.horcu.apps.peez.backend.storage.cloudStorageApi.model.CloudStorage content) throws java.io.IOException {
    InsertCloudStorage result = new InsertCloudStorage(content);
    initialize(result);
    return result;
  }

  public class InsertCloudStorage extends CloudStorageApiRequest<com.horcu.apps.peez.backend.storage.cloudStorageApi.model.CloudStorage> {

    private static final String REST_PATH = "cloudstorage";

    /**
     * Create a request for the method "insertCloudStorage".
     *
     * This request holds the parameters needed by the the cloudStorageApi server.  After setting any
     * optional parameters, call the {@link InsertCloudStorage#execute()} method to invoke the remote
     * operation. <p> {@link InsertCloudStorage#initialize(com.google.api.client.googleapis.services.A
     * bstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param content the {@link com.horcu.apps.peez.backend.storage.cloudStorageApi.model.CloudStorage}
     * @since 1.13
     */
    protected InsertCloudStorage(com.horcu.apps.peez.backend.storage.cloudStorageApi.model.CloudStorage content) {
      super(CloudStorageApi.this, "POST", REST_PATH, content, com.horcu.apps.peez.backend.storage.cloudStorageApi.model.CloudStorage.class);
    }

    @Override
    public InsertCloudStorage setAlt(java.lang.String alt) {
      return (InsertCloudStorage) super.setAlt(alt);
    }

    @Override
    public InsertCloudStorage setFields(java.lang.String fields) {
      return (InsertCloudStorage) super.setFields(fields);
    }

    @Override
    public InsertCloudStorage setKey(java.lang.String key) {
      return (InsertCloudStorage) super.setKey(key);
    }

    @Override
    public InsertCloudStorage setOauthToken(java.lang.String oauthToken) {
      return (InsertCloudStorage) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertCloudStorage setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertCloudStorage) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertCloudStorage setQuotaUser(java.lang.String quotaUser) {
      return (InsertCloudStorage) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertCloudStorage setUserIp(java.lang.String userIp) {
      return (InsertCloudStorage) super.setUserIp(userIp);
    }

    @Override
    public InsertCloudStorage set(String parameterName, Object value) {
      return (InsertCloudStorage) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link CloudStorageApi}.
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

    /** Builds a new instance of {@link CloudStorageApi}. */
    @Override
    public CloudStorageApi build() {
      return new CloudStorageApi(this);
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
     * Set the {@link CloudStorageApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setCloudStorageApiRequestInitializer(
        CloudStorageApiRequestInitializer cloudstorageapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(cloudstorageapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
