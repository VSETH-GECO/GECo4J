package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.GECo4J;
import ch.ethz.geco.g4j.internal.Endpoints;
import ch.ethz.geco.g4j.internal.GECoUtils;
import ch.ethz.geco.g4j.internal.json.*;
import ch.ethz.geco.g4j.obj.IBorrowedItem;
import ch.ethz.geco.g4j.obj.IGECoClient;
import ch.ethz.geco.g4j.obj.ILanUser;
import ch.ethz.geco.g4j.util.LogMarkers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LanUser implements ILanUser {
    private final IGECoClient client;
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

    public LanUser(IGECoClient client, Long id, String statusMessage, Status status, String username, String firstName, String lastName, String seatName, Long birthday, Boolean isVerified, String legiNumber, String lanPackage, String studentAssoc) {
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
    public String getSeatName() {
        return seatName;
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
    public String getStudentAssoc() {
        return studentAssoc;
    }

    @Override
    public Boolean setVerification(Boolean isVerified, String legiNumber) {
        try {
            LanUserObject lanUserObject = ((GECoClient) client).REQUESTS.PATCH.makeRequest(Endpoints.BASE + "/lan/user/" + id + "/verify", GECoUtils.MAPPER.writeValueAsString(new VerifyRequest(isVerified, legiNumber)), LanUserObject.class);

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
            LanUserObject lanUserObject = ((GECoClient) client).REQUESTS.PATCH.makeRequest(Endpoints.BASE + "/lan/user/" + id + "/checkin", GECoUtils.MAPPER.writeValueAsString(new CheckinRequest(checkinString)), LanUserObject.class);

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
    public List<IBorrowedItem> getBorrowedItems() {
        List<BorrowedItemObject> borrowedItemObjects = ((GECoClient) client).REQUESTS.GET.makeRequest(Endpoints.BASE + "/lan/user" + id + "/items", new TypeReference<List<BorrowedItemObject>>(){});

        // Internal error occurred
        if (borrowedItemObjects == null) {
            GECo4J.LOGGER.error(LogMarkers.API, "Internal Error! Please contact a developer.");
            return null;
        }

        List<IBorrowedItem> borrowedItems = new ArrayList<>();
        borrowedItemObjects.forEach(borrowedItemObject -> borrowedItems.add(GECoUtils.getBorrowedItemFromJSON(client, borrowedItemObject, id)));

        return borrowedItems;
    }

    @Override
    public IBorrowedItem borrowItem(String name) {
        try {
            BorrowedItemObject borrowedItemObject = ((GECoClient) client).REQUESTS.POST.makeRequest(Endpoints.BASE + "/lan/user/"+id+"/items", GECoUtils.MAPPER.writeValueAsString(new BorrowItemRequest(name)), BorrowedItemObject.class);

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
