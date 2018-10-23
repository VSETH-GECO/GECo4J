package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.obj.IBorrowedItem;

public class BorrowedItem implements IBorrowedItem {
    private final Long id;
    private final Long userID;
    private final String name;

    public BorrowedItem(Long id, Long userID, String name) {
        this.id = id;
        this.userID = userID;
        this.name = name;
    }

    @Override
    public Long getID() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void remove() {

    }
}
