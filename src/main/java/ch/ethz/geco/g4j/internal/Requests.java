package ch.ethz.geco.g4j.internal;

import ch.ethz.geco.g4j.obj.GECoClient;
import ch.ethz.geco.g4j.util.APIException;
import ch.ethz.geco.g4j.util.GECoException;
import ch.ethz.geco.g4j.util.LogMarkers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

import static ch.ethz.geco.g4j.GECo4J.LOGGER;

public class Requests {
    private final GECoClient client;

    /**
     * Constructs a new Request holder.
     *
     * @param client The GECo client, can be null if no API key is needed.
     */
    public Requests(GECoClient client) {
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
    public <T> T makeRequest(String method, String url, String entity, Class<T> clazz, Map<String, String> headers) {
        try {
            String response = makeRequest(method, url, entity, headers);
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
    public <T> T makeRequest(String method, String url, Class<T> clazz, Map<String, String> headers) {
        try {
            String response = makeRequest(method, url, headers);
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
    public <T> T makeRequest(String method, String url, TypeReference typeReference, Map<String, String> headers) {
        try {
            String response = makeRequest(method, url, headers);
            return response == null ? null : GECoUtils.MAPPER.readValue(response, typeReference);
        } catch (IOException e) {
            throw new GECoException("Unable to serialize request!", e);
        }
    }

    /**
     * Makes a request.
     *
     * @param url     The url to make the request to.
     * @param headers The headers to include in the request.
     * @return The response as a byte array.
     * @throws APIException If an API exception occurred.
     */
    public String makeRequest(String method, String url, Map<String, String> headers) {
        try {
            HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();
            for (Map.Entry<String, String> header : headers.entrySet()) {
                request.addRequestProperty(header.getKey(), header.getValue());
            }

            return request(request);
        } catch (IOException e) {
            LOGGER.error(LogMarkers.API, "GECo4J Internal Exception", e);
        }
        return null;
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
    public String makeRequest(String method, String url, String entity, Map<String, String> headers) {
        try {
            HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();
            for (Map.Entry<String, String> header : headers.entrySet()) {
                request.addRequestProperty(header.getKey(), header.getValue());
            }

            OutputStream outputStream = request.getOutputStream();
            outputStream.write(entity.getBytes(StandardCharsets.UTF_8));
            outputStream.close();

            return request(request);
        } catch (IOException e) {
            LOGGER.error(LogMarkers.API, "GECo4J Internal Exception", e);
        }
        return null;
    }

    private String request(HttpURLConnection request) {
        if (client != null)
            request.addRequestProperty("X-API-KEY", client.getAPIToken());

        try {
            request.connect();
            int responseCode = request.getResponseCode();

            InputStream inputStream = request.getInputStream();
            byte[] bytes = readAllBytes(inputStream);

            String data = new String(bytes, StandardCharsets.UTF_8);
            if (responseCode == 403 || responseCode == 404 || responseCode == 424) {
                JsonNode jsonNode = GECoUtils.MAPPER.readTree(data);

                JsonNode message = jsonNode.get("message");
                JsonNode code = jsonNode.get("code");

                if (code == null) {
                    throw new GECoException("Error on request to " + request.getURL() + ". Received response code " + responseCode + ". With response text: " + data);
                }

                throw new APIException(message != null ? message.asText() : "None", code.asInt());
            } else if (responseCode < 200 || responseCode > 299) {
                throw new GECoException("Error on request to " + request.getURL() + ". Received response code " + responseCode + ". With response text: " + data);
            }

            return data;
        } catch (IOException e) {
            LOGGER.error(LogMarkers.API, "GECo4J Internal Exception", e);
            return null;
        }
    }

    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;
    private byte[] readAllBytes(InputStream inputStream) throws IOException {
        byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
        int capacity = buf.length;
        int nread = 0;
        int n;
        for (;;) {
            // read to EOF which may read more or less than initial buffer size
            while ((n = inputStream.read(buf, nread, capacity - nread)) > 0)
                nread += n;

            // if the last call to read returned -1, then we're done
            if (n < 0)
                break;

            // need to allocate a larger buffer
            if (capacity <= MAX_BUFFER_SIZE - capacity) {
                capacity = capacity << 1;
            } else {
                if (capacity == MAX_BUFFER_SIZE)
                    throw new OutOfMemoryError("Required array size too large");
                capacity = MAX_BUFFER_SIZE;
            }
            buf = Arrays.copyOf(buf, capacity);
        }
        return (capacity == nread) ? buf : Arrays.copyOf(buf, nread);
    }
}
