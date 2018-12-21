package ch.ethz.geco.g4j.internal;

import ch.ethz.geco.g4j.obj.IGECoClient;
import ch.ethz.geco.g4j.util.APIException;
import ch.ethz.geco.g4j.util.GECoException;
import ch.ethz.geco.g4j.util.LogMarkers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static ch.ethz.geco.g4j.GECo4J.LOGGER;

public class Requests {
    /**
     * Used to send POST requests.
     */
    public final Request POST;
    /**
     * Used to send GET requests.
     */
    public final Request GET;
    /**
     * Used to send DELETE requests.
     */
    public final Request DELETE;
    /**
     * Used to send PATCH requests.
     */
    public final Request PATCH;
    /**
     * Used to send PUT requests.
     */
    public final Request PUT;

    /**
     * The HTTP client used for accessing the API
     */
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    /**
     * Constructs a new Request holder.
     *
     * @param client The GECo client, can be null if no API key is needed.
     */
    public Requests(IGECoClient client) {
        POST = new Request(HttpPost.class, client);
        GET = new Request(HttpGet.class, client);
        DELETE = new Request(HttpDelete.class, client);
        PATCH = new Request(HttpPatch.class, client);
        PUT = new Request(HttpPut.class, client);
    }

    public final class Request {
        /**
         * The class of the method type used for the request
         */
        private final Class<? extends HttpUriRequest> requestClass;

        /**
         * The client used for these requests.
         */
        private final IGECoClient client;

        private Request(Class<? extends HttpUriRequest> requestClass, IGECoClient client) {
            this.requestClass = requestClass;
            this.client = client;
        }

        /**
         * Makes a request.
         *
         * @param url     The url to make the request to.
         * @param entity  Any data to serialize and send in the body of the request.
         * @param clazz   The class of the object to deserialize the json response into.
         * @param headers The headers to include in the request.
         * @param <T>     The type of the object to deserialize the json response into.
         * @return The deserialized response.
         * @throws APIException If an API exception occurred.
         */
        public <T> T makeRequest(String url, Object entity, Class<T> clazz, BasicNameValuePair... headers) {
            try {
                return makeRequest(url, GECoUtils.MAPPER.writeValueAsString(entity), clazz, headers);
            } catch (JsonProcessingException e) {
                throw new GECoException("Unable to serialize request!", e);
            }
        }

        /**
         * Makes a request.
         *
         * @param url     The url to make the request to.
         * @param entity  Any data to serialize and send in the body of the request.
         * @param clazz   The class of the object to deserialize the json response into.
         * @param headers The headers to include in the request.
         * @param <T>     The type of the object to deserialize the json response into.
         * @return The deserialized response.
         * @throws APIException If an API exception occurred.
         */
        public <T> T makeRequest(String url, String entity, Class<T> clazz, BasicNameValuePair... headers) {
            try {
                String response = makeRequest(url, entity, headers);
                return response == null ? null : GECoUtils.MAPPER.readValue(response, clazz);
            } catch (IOException e) {
                throw new GECoException("Unable to serialize request!", e);
            }
        }

        /**
         * Makes a request.
         *
         * @param url     The url to make the request to.
         * @param clazz   The class of the object to deserialize the json response into.
         * @param headers The headers to include in the request.
         * @param <T>     The type of the object to deserialize the json response into.
         * @return The deserialized response.
         * @throws APIException If an API exception occurred.
         */
        public <T> T makeRequest(String url, Class<T> clazz, BasicNameValuePair... headers) {
            try {
                String response = makeRequest(url, headers);
                return response == null ? null : GECoUtils.MAPPER.readValue(response, clazz);
            } catch (IOException e) {
                throw new GECoException("Unable to serialize request!", e);
            }
        }

        /**
         * Makes a request.
         *
         * @param url           The url to make the request to.
         * @param typeReference The type of the object to deserialize the json response into.
         * @param headers       The headers to include in the request.
         * @param <T>           The type of the object to deserialize the json response into.
         * @return The deserialized response.
         * @throws APIException If an API exception occurred.
         */
        public <T> T makeRequest(String url, TypeReference typeReference, BasicNameValuePair... headers) {
            try {
                String response = makeRequest(url, headers);
                return response == null ? null : GECoUtils.MAPPER.readValue(response, typeReference);
            } catch (IOException e) {
                throw new GECoException("Unable to serialize request!", e);
            }
        }

        /**
         * Makes a request.
         *
         * @param url     The url to make the request to.
         * @param entity  Any data to serialize and send in the body of the request.
         * @param headers The headers to include in the request.
         * @throws APIException If an API exception occurred.
         */
        public void makeRequest(String url, Object entity, BasicNameValuePair... headers) {
            try {
                makeRequest(url, GECoUtils.MAPPER.writeValueAsString(entity), headers);
            } catch (IOException e) {
                throw new GECoException("Unable to serialize request!", e);
            }
        }

        /**
         * Makes a request.
         *
         * @param url     The url to make the request to.
         * @param entity  Any data to serialize and send in the body of the request.
         * @param headers The headers to include in the request.
         * @return The response as a byte array.
         * @throws APIException If an API exception occurred.
         */
        public String makeRequest(String url, String entity, BasicNameValuePair... headers) {
            return makeRequest(url, new StringEntity(entity, "UTF-8"), headers);
        }

        /**
         * Makes a request.
         *
         * @param url     The url to make the request to.
         * @param headers The headers to include in the request.
         * @return The response as a byte array.
         * @throws APIException If an API exception occurred.
         */
        public String makeRequest(String url, BasicNameValuePair... headers) {
            try {
                HttpUriRequest request = this.requestClass.getConstructor(String.class).newInstance(url);
                for (BasicNameValuePair header : headers) {
                    request.addHeader(header.getName(), header.getValue());
                }

                return request(request);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                LOGGER.error(LogMarkers.API, "GECo4J Internal Exception", e);
                return null;
            }
        }


        /**
         * Makes a request.
         *
         * @param url     The url to make the request to.
         * @param entity  Any data to serialize and send in the body of the request.
         * @param headers The headers to include in the request.
         * @return The response as a byte array.
         * @throws APIException If an API exception occurred.
         */
        public String makeRequest(String url, HttpEntity entity, BasicNameValuePair... headers) {
            try {
                if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(this.requestClass)) {
                    HttpEntityEnclosingRequestBase request = (HttpEntityEnclosingRequestBase)
                            this.requestClass.getConstructor(String.class).newInstance(url);
                    for (BasicNameValuePair header : headers) {
                        request.addHeader(header.getName(), header.getValue());
                    }
                    request.setEntity(entity);
                    return request(request);
                } else {
                    LOGGER.error(LogMarkers.API, "Tried to attach HTTP entity to invalid type! ({})", this.requestClass.getSimpleName());
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                LOGGER.error(LogMarkers.API, "GECo4J Internal Exception", e);
            }
            return null;
        }

        private String request(HttpUriRequest request) {
            if (client != null)
                request.addHeader("X-API-KEY", client.getAPIToken());

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int responseCode = response.getStatusLine().getStatusCode();

                String data = null;
                if (response.getEntity() != null)
                    data = EntityUtils.toString(response.getEntity());

                if (responseCode == 403 || responseCode == 404 || responseCode == 424) {
                    JsonNode jsonNode = GECoUtils.MAPPER.readTree(data);

                    JsonNode message = jsonNode.get("message");
                    JsonNode code = jsonNode.get("code");

                    if (code == null) {
                        throw new GECoException("Error on request to " + request.getURI() + ". Received response code " + responseCode + ". With response text: " + data);
                    }

                    throw new APIException(message != null ? message.asText() : "None", code.asInt());
                } else if (responseCode < 200 || responseCode > 299) {
                    throw new GECoException("Error on request to " + request.getURI() + ". Received response code " + responseCode + ". With response text: " + data);
                }

                return data;
            } catch (IOException e) {
                LOGGER.error(LogMarkers.API, "GECo4J Internal Exception", e);
                return null;
            }
        }
    }
}
