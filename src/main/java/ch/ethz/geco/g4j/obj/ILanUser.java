package ch.ethz.geco.g4j.obj;

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
    String getSeatName(); // TODO: Possible to replace with Seat Object

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
    String getStudentAssoc();

    /**
     * The status of a LAN user.
     */
    enum Status {
        AWAITING_PAYMENT, PAID_NO_SEAT, PAID_WITH_SEAT, CHECKED_IN
    }
}
