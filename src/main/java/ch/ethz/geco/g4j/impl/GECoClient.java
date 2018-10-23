package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.GECo4J;
import ch.ethz.geco.g4j.internal.Endpoints;
import ch.ethz.geco.g4j.internal.GECoUtils;
import ch.ethz.geco.g4j.internal.Requests;
import ch.ethz.geco.g4j.internal.json.UserObject;
import ch.ethz.geco.g4j.obj.*;
import ch.ethz.geco.g4j.util.APIException;
import ch.ethz.geco.g4j.util.LogMarkers;

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
        return null;
    }

    @Override
    public List<ISeat> getSeats() {
        return null;
    }

    @Override
    public ISeat getSeatByID(Long id) {
        return null;
    }

    @Override
    public ILanUser getLanUserByID(Long id) {
        return null;
    }

    @Override
    public ILanUser getLanUserByName(String name) {
        return null;
    }

    @Override
    public ISeat getSeatByName(String name) {
        return null;
    }

    @Override
    public INews getNewsByID(Long id) {
        return null;
    }

    @Override
    public List<INews> getNews(Integer page) {
        return null;
    }

    @Override
    public IEvent getEventByID(Long id) {
        return null;
    }

    @Override
    public List<IEvent> getEvents(Integer page) {
        return null;
    }
}
