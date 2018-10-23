package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.obj.ISeat;

import java.util.Optional;

public class Seat implements ISeat {
    private final Long id;
    private final Long lanUserID;
    private final Long webUserID;
    private final Status status;
    private final String username;
    private final String seatName;

    public Seat(Long id, Long lanUserID, Long webUserID, Status status, String username, String seatName) {
        this.id = id;
        this.lanUserID = lanUserID;
        this.webUserID = webUserID;
        this.status = status;
        this.username = username;
        this.seatName = seatName;
    }

    @Override
    public Long getID() {
        return id;
    }

    @Override
    public Optional<Long> getLanUserID() {
        return Optional.ofNullable(lanUserID);
    }

    @Override
    public Optional<Long> getWebUserID() {
        return Optional.ofNullable(webUserID);
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public Optional<String> getUserName() {
        return Optional.ofNullable(username);
    }

    @Override
    public String getSeatName() {
        return seatName;
    }
}
