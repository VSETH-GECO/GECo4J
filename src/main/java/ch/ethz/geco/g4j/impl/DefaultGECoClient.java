package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.GECo4J;
import ch.ethz.geco.g4j.internal.GECoUtils;
import ch.ethz.geco.g4j.internal.Requests;
import ch.ethz.geco.g4j.internal.json.*;
import ch.ethz.geco.g4j.obj.*;
import ch.ethz.geco.g4j.util.LogMarkers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DefaultGECoClient implements GECoClient {
    /**
     * The requests holder object.
     */
    final Requests REQUESTS;
    private final String apiToken;

    public DefaultGECoClient(String apiToken) {
        REQUESTS = new Requests(this);
        GECo4J.LOGGER.info(LogMarkers.MAIN, "Creating new client with API token: " + apiToken);
        this.apiToken = apiToken;
    }

    @Override
    public String getAPIToken() {
        return apiToken;
    }

    @Override
    public Mono<User> getUserByID(Long id) {
        return REQUESTS.makeRequest(Requests.METHOD.GET, "/user/" + id, UserObject.class, null).map(GECoUtils::getUserFromJSON);
    }

    @Override
    public Mono<User> getUserByDiscordID(Long id) {
        return REQUESTS.makeRequest(Requests.METHOD.GET, "/user/discord/" + id, UserObject.class, null).map(GECoUtils::getUserFromJSON);
    }

    @Override
    public Flux<Seat> getSeats() {
        return REQUESTS.makeRequest(Requests.METHOD.GET, "/lan/seats", SeatObject[].class, null).flatMapMany(seatObjects -> {
            Seat[] seats = new Seat[seatObjects.length];
            for (int i = 0; i < seatObjects.length; i++) {
                seats[i] = GECoUtils.getSeatFromJSON(this, seatObjects[i]);
            }

            return Flux.fromArray(seats);
        });
    }

    @Override
    public Mono<Seat> getSeatByID(Long id) {
        return REQUESTS.makeRequest(Requests.METHOD.GET, "/lan/seats/" + id, SeatObject.class, null).map(seatObject -> GECoUtils.getSeatFromJSON(this, seatObject));
    }

    @Override
    public Mono<LanUser> getLanUserByID(Long id) {
        return REQUESTS.makeRequest(Requests.METHOD.GET, "/lan/user/" + id, LanUserObject.class, null)
                .map(lanUserObject -> GECoUtils.getLanUserFromJSON(this, lanUserObject));
    }

    @Override
    public Mono<LanUser> getLanUserByName(String name) {
        return REQUESTS.makeRequest(Requests.METHOD.GET, "/lan/search/user/" + name, LanUserObject.class, null)
                .map(lanUserObject -> GECoUtils.getLanUserFromJSON(this, lanUserObject));
    }

    @Override
    public Mono<Seat> getSeatByName(String name) {
        return REQUESTS.makeRequest(Requests.METHOD.GET, "/lan/search/seats/" + name, SeatObject.class, null).map(seatObject -> GECoUtils.getSeatFromJSON(this, seatObject));
    }

    @Override
    public Mono<News> getNewsByID(Long id) {
        return REQUESTS.makeRequest(Requests.METHOD.GET, "/web/news/" + id, NewsObject.class, null).map(GECoUtils::getNewsFromJSON);
    }

    @Override
    public Flux<News> getNews(Integer page) {
        return REQUESTS.makeRequest(Requests.METHOD.GET, "/web/news?page=" + page, NewsObject[].class, null).flatMapMany(newsObjects -> {
            News[] news = new News[newsObjects.length];
            for (int i = 0; i < newsObjects.length; i++) {
                news[i] = GECoUtils.getNewsFromJSON(newsObjects[i]);
            }

            return Flux.fromArray(news);
        });
    }

    @Override
    public Mono<Event> getEventByID(Long id) {
        return REQUESTS.makeRequest(Requests.METHOD.GET, "/web/events/" + id, EventObject.class, null).map(GECoUtils::getEventFromJSON);
    }

    @Override
    public Flux<Event> getEvents(Integer page) {
        return REQUESTS.makeRequest(Requests.METHOD.GET, "/web/events?page=" + page, EventObject[].class, null).flatMapMany(eventObjects -> {
            Event[] events = new Event[eventObjects.length];
            for (int i = 0; i < eventObjects.length; i++) {
                events[i] = GECoUtils.getEventFromJSON(eventObjects[i]);
            }

            return Flux.fromArray(events);
        });
    }
}
