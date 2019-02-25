package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.GECo4J;
import ch.ethz.geco.g4j.internal.Endpoints;
import ch.ethz.geco.g4j.internal.GECoUtils;
import ch.ethz.geco.g4j.internal.json.*;
import ch.ethz.geco.g4j.obj.BorrowedItem;
import ch.ethz.geco.g4j.obj.GECoClient;
import ch.ethz.geco.g4j.obj.LanUser;
import ch.ethz.geco.g4j.util.LogMarkers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            setVerification(true, "none");
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
    public Boolean setVerification(Boolean isVerified, String legiNumber) {
        try {
            LanUserObject lanUserObject = ((DefaultGECoClient) client).REQUESTS.makeRequest("PATCH", Endpoints.BASE + "/lan/user/" + id + "/verify", GECoUtils.MAPPER.writeValueAsString(new VerifyRequest(isVerified, legiNumber)), LanUserObject.class, new HashMap<>());

            // Internal error occurred
            if (lanUserObject == null) {
                GECo4J.LOGGER.error(LogMarkers.API, "Internal Error! Please contact a developer.");
                return null;
            }

            return lanUserObject.sa_verified == isVerified && lanUserObject.legi_number.equals(legiNumber);
        } catch (JsonProcessingException e) {
            GECo4J.LOGGER.error(LogMarkers.MAIN, "Internal Error! Please contact a developer.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean checkin(String checkinString) {
        try {
            LanUserObject lanUserObject = ((DefaultGECoClient) client).REQUESTS.makeRequest("PATCH", Endpoints.BASE + "/lan/user/" + id + "/checkin", GECoUtils.MAPPER.writeValueAsString(new CheckinRequest(checkinString)), LanUserObject.class, new HashMap<>());

            // Internal error occurred
            if (lanUserObject == null) {
                GECo4J.LOGGER.error(LogMarkers.API, "Internal Error! Please contact a developer.");
                return null;
            }

            return lanUserObject.status_code == 3;
        } catch (JsonProcessingException e) {
            GECo4J.LOGGER.error(LogMarkers.MAIN, "Internal Error! Please contact a developer.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<BorrowedItem> getBorrowedItems() {
        List<BorrowedItemObject> borrowedItemObjects = ((DefaultGECoClient) client).REQUESTS.makeRequest("GET", Endpoints.BASE + "/lan/user" + id + "/items", new TypeReference<List<BorrowedItemObject>>() {
        }, new HashMap<>());

        // Internal error occurred
        if (borrowedItemObjects == null) {
            GECo4J.LOGGER.error(LogMarkers.API, "Internal Error! Please contact a developer.");
            return null;
        }

        List<BorrowedItem> borrowedItems = new ArrayList<>();
        borrowedItemObjects.forEach(borrowedItemObject -> borrowedItems.add(GECoUtils.getBorrowedItemFromJSON(client, borrowedItemObject, id)));

        return borrowedItems;
    }

    @Override
    public Optional<BorrowedItem> getBorrowedItemByID(Long id) {
        List<BorrowedItem> borrowedItems = getBorrowedItems();

        if (!borrowedItems.isEmpty()) {
            for (BorrowedItem borrowedItem : borrowedItems) {
                if (borrowedItem.getID().equals(id)) {
                    return Optional.of(borrowedItem);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public BorrowedItem borrowItem(String name) {
        try {
            BorrowedItemObject borrowedItemObject = ((DefaultGECoClient) client).REQUESTS.makeRequest("POST", Endpoints.BASE + "/lan/user/" + id + "/items", GECoUtils.MAPPER.writeValueAsString(new BorrowItemRequest(name)), BorrowedItemObject.class, new HashMap<>());

            // Internal error occurred
            if (borrowedItemObject == null) {
                GECo4J.LOGGER.error(LogMarkers.API, "Internal Error! Please contact a developer.");
                return null;
            }

            return GECoUtils.getBorrowedItemFromJSON(client, borrowedItemObject, id);
        } catch (JsonProcessingException e) {
            GECo4J.LOGGER.error(LogMarkers.MAIN, "Internal Error! Please contact a developer.");
            e.printStackTrace();
            return null;
        }
    }
}
