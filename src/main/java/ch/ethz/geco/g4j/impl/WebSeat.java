package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.obj.GECoClient;
import ch.ethz.geco.g4j.obj.LanUser;
import ch.ethz.geco.g4j.obj.Seat;
import ch.ethz.geco.g4j.obj.User;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class WebSeat implements Seat {
    private final Long id;
    private final Long lanUserID;
    private final Long webUserID;
    private final Status status;
    private final String username;
    private final String seatName;
    private final GECoClient client;

    public WebSeat(GECoClient client, Long id, Long lanUserID, Long webUserID, Status status, String username, String seatName) {
        this.client = client;
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
    public Mono<LanUser> getLanUser() {
        if (lanUserID == null) {
            return Mono.empty();
        }

        return client.getLanUserByID(lanUserID);
    }

    @Override
    public Optional<Long> getWebUserID() {
        return Optional.ofNullable(webUserID);
    }

    @Override
    public Mono<User> getWebUser() {
        if (webUserID == null) {
            return Mono.empty();
        }

        return client.getUserByID(webUserID);
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
