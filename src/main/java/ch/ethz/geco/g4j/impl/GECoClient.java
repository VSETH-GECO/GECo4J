package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.GECo4J;
import ch.ethz.geco.g4j.internal.Endpoints;
import ch.ethz.geco.g4j.internal.GECoUtils;
import ch.ethz.geco.g4j.internal.Requests;
import ch.ethz.geco.g4j.internal.json.*;
import ch.ethz.geco.g4j.obj.*;
import ch.ethz.geco.g4j.util.APIException;
import ch.ethz.geco.g4j.util.LogMarkers;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class GECoClient implements IGECoClient {
    /**
     * The requests holder object.
     */
    public final Requests REQUESTS = new Requests(this);
    private final String apiToken;

    public GECoClient(String apiToken) {
        GECo4J.LOGGER.info(LogMarkers.MAIN, "Creating new client with API token: " + apiToken);
        this.apiToken = apiToken;
    }

    @Override
    public String getAPIToken() {
        return apiToken;
    }

    @Override
    public IUser getUserByID(Long id) {
        try {
            UserObject userObject = REQUESTS.GET.makeRequest(Endpoints.BASE + "/user/" + id, UserObject.class);

            // If an internal error occurred
            if (userObject == null) {
                return null;
            }

            return GECoUtils.getUserFromJSON(userObject);
        } catch (APIException e) {
            switch (e.getError()) {
                case NOT_FOUND:
                    return null;
            }
        }

        GECo4J.LOGGER.error(LogMarkers.UTIL, "Unknown error occured! Please contact a developer.");
        return null;
    }

    @Override
    public IUser getUserByDiscordID(Long id) {
        try {
            UserObject userObject = REQUESTS.GET.makeRequest(Endpoints.BASE + "/user/discord/" + id, UserObject.class);

            // If an internal error occurred
            if (userObject == null) {
                return null;
            }

            return GECoUtils.getUserFromJSON(userObject);
        } catch (APIException e) {
            switch (e.getError()) {
                case NOT_FOUND:
                    return null;
            }
        }

        GECo4J.LOGGER.error(LogMarkers.UTIL, "Unknown error occured! Please contact a developer.");
        return null;
    }

    @Override
    public List<ISeat> getSeats() {
        try {
            List<SeatObject> seatObjects = REQUESTS.GET.makeRequest(Endpoints.BASE + "/lan/seats", new TypeReference<List<SeatObject>>() {
            });

            // If an internal error occurred
            if (seatObjects == null) {
                return null;
            }

            List<ISeat> seats = new ArrayList<>();
            seatObjects.forEach(seatObject -> seats.add(GECoUtils.getSeatFromJSON(seatObject)));

            return seats;
        } catch (APIException e) {
            switch (e.getError()) {
                case NOT_FOUND:
                    return new ArrayList<>();
            }
        }

        GECo4J.LOGGER.error(LogMarkers.UTIL, "Unknown error occured! Please contact a developer.");
        return null;
    }

    @Override
    public ISeat getSeatByID(Long id) {
        try {
            SeatObject seatObject = REQUESTS.GET.makeRequest(Endpoints.BASE + "/lan/seats/" + id, SeatObject.class);

            // If an internal error occurred
            if (seatObject == null) {
                return null;
            }

            return GECoUtils.getSeatFromJSON(seatObject);
        } catch (APIException e) {
            switch (e.getError()) {
                case NOT_FOUND:
                    return null;
            }
        }

        GECo4J.LOGGER.error(LogMarkers.UTIL, "Unknown error occured! Please contact a developer.");
        return null;
    }

    @Override
    public ILanUser getLanUserByID(Long id) {
        try {
            LanUserObject lanUserObject = REQUESTS.GET.makeRequest(Endpoints.BASE + "/lan/user/" + id, LanUserObject.class);

            // If an internal error occurred.
            if (lanUserObject == null) {
                return null;
            }

            return GECoUtils.getLanUserFromJSON(this, lanUserObject);
        } catch (APIException e) {
            switch (e.getError()) {
                case NOT_FOUND:
                    return null;
            }
        }

        GECo4J.LOGGER.error(LogMarkers.UTIL, "Unknown error occured! Please contact a developer.");
        return null;
    }

    @Override
    public ILanUser getLanUserByName(String name) {
        try {
            LanUserObject lanUserObject = REQUESTS.GET.makeRequest(Endpoints.BASE + "/lan/search/user/" + name, LanUserObject.class);

            // If an internal error occurred.
            if (lanUserObject == null) {
                return null;
            }

            return GECoUtils.getLanUserFromJSON(this, lanUserObject);
        } catch (APIException e) {
            switch (e.getError()) {
                case NOT_FOUND:
                    return null;
            }
        }

        GECo4J.LOGGER.error(LogMarkers.UTIL, "Unknown error occured! Please contact a developer.");
        return null;
    }

    @Override
    public ISeat getSeatByName(String name) {
        try {
            SeatObject seatObject = REQUESTS.GET.makeRequest(Endpoints.BASE + "/lan/search/seats/" + name, SeatObject.class);

            // If an internal error occurred
            if (seatObject == null) {
                return null;
            }

            return GECoUtils.getSeatFromJSON(seatObject);
        } catch (APIException e) {
            switch (e.getError()) {
                case NOT_FOUND:
                    return null;
            }
        }

        GECo4J.LOGGER.error(LogMarkers.UTIL, "Unknown error occured! Please contact a developer.");
        return null;
    }

    @Override
    public INews getNewsByID(Long id) {
        try {
            NewsObject newsObject = REQUESTS.GET.makeRequest(Endpoints.BASE + "/web/news/" + id, NewsObject.class);

            // If an internal error occurred
            if (newsObject == null) {
                return null;
            }

            return GECoUtils.getNewsFromJSON(newsObject);
        } catch (APIException e) {
            switch (e.getError()) {
                case NOT_FOUND:
                    return null;
            }
        }

        GECo4J.LOGGER.error(LogMarkers.UTIL, "Unknown error occured! Please contact a developer.");
        return null;
    }

    @Override
    public List<INews> getNews(Integer page) {
        try {
            List<NewsObject> newsObjects = REQUESTS.GET.makeRequest(Endpoints.BASE + "/web/news?page=" + page, new TypeReference<List<NewsObject>>() {
            });

            // If an internal error occurred
            if (newsObjects == null) {
                return null;
            }

            List<INews> news = new ArrayList<>();
            newsObjects.forEach(newsObject -> news.add(GECoUtils.getNewsFromJSON(newsObject)));

            return news;
        } catch (APIException e) {
            switch (e.getError()) {
                case NOT_FOUND:
                    return new ArrayList<>();
            }
        }

        GECo4J.LOGGER.error(LogMarkers.UTIL, "Unknown error occured! Please contact a developer.");
        return null;
    }

    @Override
    public IEvent getEventByID(Long id) {
        try {
            EventObject eventObject = REQUESTS.GET.makeRequest(Endpoints.BASE + "/web/events/" + id, EventObject.class);

            // If an internal error occurred
            if (eventObject == null) {
                return null;
            }

            return GECoUtils.getEventFromJSON(eventObject);
        } catch (APIException e) {
            switch (e.getError()) {
                case NOT_FOUND:
                    return null;
            }
        }

        GECo4J.LOGGER.error(LogMarkers.UTIL, "Unknown error occured! Please contact a developer.");
        return null;
    }

    @Override
    public List<IEvent> getEvents(Integer page) {
        try {
            List<EventObject> eventObjects = REQUESTS.GET.makeRequest(Endpoints.BASE + "/web/events?page=" + page, new TypeReference<List<EventObject>>(){});

            // If an internal error occurred
            if (eventObjects == null) {
                return null;
            }

            List<IEvent> events = new ArrayList<>();
            eventObjects.forEach(eventObject -> events.add(GECoUtils.getEventFromJSON(eventObject)));

            return events;
        } catch (APIException e) {
            switch (e.getError()) {
                case NOT_FOUND:
                    return new ArrayList<>();
            }
        }

        GECo4J.LOGGER.error(LogMarkers.UTIL, "Unknown error occured! Please contact a developer.");
        return null;
    }
}
