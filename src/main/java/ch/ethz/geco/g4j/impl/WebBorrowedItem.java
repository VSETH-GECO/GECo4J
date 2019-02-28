package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.internal.Requests;
import ch.ethz.geco.g4j.obj.BorrowedItem;
import ch.ethz.geco.g4j.obj.GECoClient;
import reactor.core.publisher.Mono;

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
    public Mono<Void> remove() {
        return ((DefaultGECoClient) client).REQUESTS.makeRequest(Requests.METHOD.DELETE, "/lan/user/" + userID + "/items/" + id, null, null).then();
    }
}
