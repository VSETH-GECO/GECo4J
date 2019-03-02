package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.internal.GECoUtils;
import ch.ethz.geco.g4j.internal.Requests;
import ch.ethz.geco.g4j.internal.json.*;
import ch.ethz.geco.g4j.obj.BorrowedItem;
import ch.ethz.geco.g4j.obj.GECoClient;
import ch.ethz.geco.g4j.obj.LanUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class WebLanUser implements LanUser {
    private final GECoClient client;
    private final Long id;
    private final String statusMessage;
    private final Status status;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String seatName;
    private final Long birthday;
    private final Boolean isVerified;
    private final String legiNumber;
    private final String lanPackage;
    private final String studentAssoc;

    public WebLanUser(GECoClient client, Long id, String statusMessage, Status status, String username, String firstName, String lastName, String seatName, Long birthday, Boolean isVerified, String legiNumber, String lanPackage, String studentAssoc) {
        this.client = client;
        this.id = id;
        this.statusMessage = statusMessage;
        this.status = status;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.seatName = seatName;
        this.birthday = birthday;
        this.isVerified = isVerified;
        this.legiNumber = legiNumber;
        this.lanPackage = lanPackage;
        this.studentAssoc = studentAssoc;

        // Automatically verify external users
        if (lanPackage.equalsIgnoreCase("external")) {
            setVerification(true, "none").block();
        }
    }

    @Override
    public Long getID() {
        return id;
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public Optional<String> getSeatName() {
        return Optional.ofNullable(seatName);
    }

    @Override
    public Long getBirthDay() {
        return birthday;
    }

    @Override
    public Boolean isVerified() {
        return isVerified;
    }

    @Override
    public Optional<String> getLegiNumber() {
        return Optional.ofNullable(legiNumber);
    }

    @Override
    public String getLANPackage() {
        return lanPackage;
    }

    @Override
    public Optional<String> getStudentAssoc() {
        return Optional.ofNullable(studentAssoc);
    }

    @Override
    public Mono<LanUser> setVerification(Boolean isVerified, String legiNumber) {
        String content;
        try {
            content = GECoUtils.MAPPER.writeValueAsString(new VerifyRequest(isVerified, legiNumber));
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }

        return ((DefaultGECoClient) client).REQUESTS.makeRequest(Requests.METHOD.PATCH, "/lan/user/" + id + "/verify", LanUserObject.class, content)
                .map(lanUserObject -> GECoUtils.getLanUserFromJSON(client, lanUserObject));
    }

    @Override
    public Mono<LanUser> checkin(String checkinString) {
        String content;
        try {
            content = GECoUtils.MAPPER.writeValueAsString(new CheckinRequest(checkinString));
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }

        return ((DefaultGECoClient) client).REQUESTS.makeRequest(Requests.METHOD.PATCH, "/lan/user/" + id + "/checkin", LanUserObject.class, content)
                .map(lanUserObject -> GECoUtils.getLanUserFromJSON(client, lanUserObject));
    }

    @Override
    public Flux<BorrowedItem> getBorrowedItems() {
        return ((DefaultGECoClient) client).REQUESTS.makeRequest(Requests.METHOD.GET, "/lan/user/" + id + "/items", BorrowedItemObject[].class, null).flatMapMany(borrowedItemObjects -> {
            BorrowedItem[] borrowedItems = new BorrowedItem[borrowedItemObjects.length];
            for (int i = 0; i < borrowedItemObjects.length; i++) {
                borrowedItems[i] = GECoUtils.getBorrowedItemFromJSON(client, borrowedItemObjects[i], id);
            }

            return Flux.fromArray(borrowedItems);
        });
    }

    @Override
    public Mono<BorrowedItem> getBorrowedItemByID(Long id) {
        return getBorrowedItems().filter(borrowedItem -> borrowedItem.getID().equals(id)).single();
    }

    @Override
    public Mono<BorrowedItem> borrowItem(String name) {
        String content;
        try {
            content = GECoUtils.MAPPER.writeValueAsString(new BorrowItemRequest(name));
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }

        return ((DefaultGECoClient) client).REQUESTS.makeRequest(Requests.METHOD.POST, "/lan/user/" + id + "/items", BorrowedItemObject.class, content)
                .map(borrowedItemObject -> GECoUtils.getBorrowedItemFromJSON(client, borrowedItemObject, id));
    }
}
