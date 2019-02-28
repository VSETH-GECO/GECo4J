package ch.ethz.geco.g4j.internal;

import ch.ethz.geco.g4j.obj.GECoClient;
import ch.ethz.geco.g4j.util.APIException;
import ch.ethz.geco.g4j.util.GECo4JException;
import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.util.annotation.Nullable;

import java.io.IOException;

public class Requests {
    private final HttpClient httpClient;

    public enum METHOD {GET, POST, DELETE, PATCH}

    /**
     * Constructs a new Request holder.
     *
     * @param client The GECo client, can be null if no API key is needed.
     */
    public Requests(GECoClient client) {
        httpClient = HttpClient.create().headers(h -> {
            h.add("X-API-KEY", client.getAPIToken());
            h.add("Content-Type", "application/json");
        }).baseUrl(Endpoints.BASE);
    }

    /**
     * Gets the shared http client.
     *
     * @return the HTTP client.
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    public <T> Mono<T> makeRequest(METHOD method, String url, Class<T> clazz, @Nullable String content) {
        HttpClient.ResponseReceiver<?> receiver = null;
        switch (method) {
            case GET:
                receiver = httpClient.get().uri(url);
                break;
            case POST:
                receiver = httpClient.post().uri(url).send(ByteBufFlux.fromString(content != null ? Flux.just(content) : Flux.empty()));
                break;
            case PATCH:
                receiver = httpClient.patch().uri(url).send(ByteBufFlux.fromString(content != null ? Flux.just(content) : Flux.empty()));
                break;
            case DELETE:
                receiver = httpClient.delete().uri(url).send(ByteBufFlux.fromString(content != null ? Flux.just(content) : Flux.empty()));
                break;
        }

        return receiver.responseSingle((response, responseContent) -> {
            int responseCode = response.status().code();
            return responseContent.asString().flatMap(data -> {
                if (responseCode == 403 || responseCode == 404 || responseCode == 424) {
                    JsonNode jsonNode;
                    try {
                        jsonNode = GECoUtils.MAPPER.readTree(data);

                        JsonNode message = jsonNode.get("message");
                        JsonNode code = jsonNode.get("code");

                        // If it's not a "controlled" exception
                        if (code == null) {
                            return Mono.error(new GECo4JException("Error on request to " + response.uri() + ". Received response code " + responseCode + ". With response text: " + data));
                        }

                        return Mono.error(new APIException(message != null ? message.asText() : "None", code.asInt()));
                    } catch (IOException e) {
                        return Mono.error(e);
                    }
                } else if (responseCode < 200 || responseCode > 299) {
                    return Mono.error(new GECo4JException("Error on request to " + response.uri() + ". Received response code " + responseCode + ". With response text: " + data));
                }

                try {
                    if (clazz == null) {
                        return Mono.empty();
                    }

                    return Mono.just(GECoUtils.MAPPER.readValue(data, clazz));
                } catch (IOException e) {
                    return Mono.error(e);
                }
            });
        });
    }
}
