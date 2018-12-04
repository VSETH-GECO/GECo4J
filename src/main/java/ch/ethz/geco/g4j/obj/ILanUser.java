package ch.ethz.geco.g4j.obj;

import java.util.List;
import java.util.Optional;

/**
 * Represents a user on a LAN event.
 */
public interface ILanUser {
    /**
     * Gets the user ID of the LAN user.
     *
     * @return The ID of the LAN user.
     */
    Long getID();

    /**
     * Gets the status message of the LAN user.
     * This message directly depends on the status itself.
     *
     * @return The status message.
     */
    String getStatusMessage();

    /**
     * Gets the status of the LAN user.
     *
     * @return The status of the LAN user.
     */
    Status getStatus();

    /**
     * Gets the username of the LAN user.
     * Note that this is the same for LAN and Web user.
     *
     * @return The username of the LAN user.
     */
    String getUserName();

    /**
     * Gets the first name of the LAN user.
     *
     * @return The first name of the LAN user.
     */
    String getFirstName();

    /**
     * Gets the last name of the LAN user.
     *
     * @return The last name of the LAN user.
     */
    String getLastName();

    /**
     * Gets the seat name of the LAN user.
     * This is NOT the seat ID.
     *
     * @return The seat name of the LAN user.
     */
    Optional<String> getSeatName(); // TODO: Possible to replace with Seat Object

    /**
     * Gets the birthday of the LAN user.
     *
     * @return The birthday of the LAN user.
     */
    Long getBirthDay(); // TODO: Possible to replace with Instant

    /**
     * Gets whether or not the student assoc. of the user was verified.
     *
     * @return True if the student assoc. was verified, false otherwise.
     */
    Boolean isVerified();

    /**
     * Gets the legi number of the LAN user if available.
     *
     * @return The legi number of the LAN user.
     */
    Optional<String> getLegiNumber();

    /**
     * Gets the package the LAN user has chosen to buy.
     *
     * @return The package of the LAN user.
     */
    String getLANPackage();

    /**
     * Gets the student association the LAN user has chosen.
     *
     * @return The student association of the LAN user.
     */
    Optional<String> getStudentAssoc();

    /**
     * Sets the verification status of a LAN user.
     *
     * @param isVerified If the LAN user is verified or not.
     * @param legiNumber The legi number of the LAN user.
     * @return True if the changes were applied, false otherwise.
     */
    Boolean setVerification(Boolean isVerified, String legiNumber); // TODO: maybe return status code enum for better error handling

    /**
     * Checks a LAN user in if the correct check-in string for that user is provided.
     *
     * @param checkinString The check-in string of the LAN user.
     * @return True if the user was checked-in, false otherwise.
     */
    Boolean checkin(String checkinString); // TODO: maybe return status code enum for better error handling

    /**
     * Gets a list of all items a LAN user has currently borrowed.
     *
     * @return A list of all borrowed items of the LAN user.
     */
    List<IBorrowedItem> getBorrowedItems();

    /**
     * Borrows a new item for the LAN user.
     *
     * @param name The name of the item to borrow.
     * @return The newly borrowed item.
     */
    IBorrowedItem borrowItem(String name);

    /**
     * The status of a LAN user.
     */
    enum Status {
        AWAITING_REGISTRATION, AWAITING_PAYMENT, PAID_NO_SEAT, PAID_WITH_SEAT, CHECKED_IN
    }
}
