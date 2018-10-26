package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.internal.Endpoints;
import ch.ethz.geco.g4j.obj.IBorrowedItem;
import ch.ethz.geco.g4j.obj.IGECoClient;

public class BorrowedItem implements IBorrowedItem {
    private final IGECoClient client;
    private final Long id;
    private final Long userID;
    private final String name;

    public BorrowedItem(IGECoClient client, Long id, Long userID, String name) {
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
        ((GECoClient) client).REQUESTS.DELETE.makeRequest(Endpoints.BASE + "/lan/user/" + userID + "/items/" + id);
    }
}
