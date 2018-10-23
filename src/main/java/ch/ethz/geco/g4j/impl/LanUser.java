package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.obj.IBorrowedItem;
import ch.ethz.geco.g4j.obj.ILanUser;

import java.util.List;
import java.util.Optional;

public class LanUser implements ILanUser {
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

    public LanUser(Long id, String statusMessage, Status status, String username, String firstName, String lastName, String seatName, Long birthday, Boolean isVerified, String legiNumber, String lanPackage, String studentAssoc) {
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
    public void setVerification(Boolean isVerified, String legiNumber) {

    }

    @Override
    public void checkin(String checkinString) {

    }

    @Override
    public List<IBorrowedItem> getBorrowedItems() {
        return null;
    }

    @Override
    public IBorrowedItem borrowItem(String name) {
        return null;
    }
}
