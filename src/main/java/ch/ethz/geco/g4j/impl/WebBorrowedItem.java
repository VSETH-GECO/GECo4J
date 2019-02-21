package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.internal.Endpoints;
import ch.ethz.geco.g4j.obj.BorrowedItem;
import ch.ethz.geco.g4j.obj.GECoClient;

public class WebBorrowedItem implements BorrowedItem {
    private final GECoClient client;
    private final Long id;
    private final Long userID;
    private final String name;

    public WebBorrowedItem(GECoClient client, Long id, Long userID, String name) {
        this.client = client;
        this.id = id;
        this.userID = userID;
        this.name = name;
    }

    @Override
    public Long getID() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void remove() {
        // TODO: improve error handling
        ((DefaultGECoClient) client).REQUESTS.DELETE.makeRequest(Endpoints.BASE + "/lan/user/" + userID + "/items/" + id);
    }
}
