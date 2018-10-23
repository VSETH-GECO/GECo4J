package ch.ethz.geco.g4j.obj;

import java.util.Optional;

/**
 * Represents a single seat on an event.
 */
public interface ISeat {
    /**
     * Gets the ID of the seat.
     *
     * @return The seat ID.
     */
    Long getID();

    /**
     * Gets the user ID of the LAN user sitting on the seat if available.
     *
     * @return The user ID of the LAN user.
     */
    Optional<Long> getLanUserID(); // TODO: Possible to replace with ILanUser

    /**
     * Gets the user ID of the Web user sitting on the seat if available.
     *
     * @return The user ID of the Web user.
     */
    Optional<Long> getWebUserID(); // TODO: Possible to replace with IUser

    /**
     * Gets the status of the seat.
     *
     * @return The status.
     */
    Status getStatus();

    /**
     * Gets the username of the user sitting on the seat.
     *
     * @return The username.
     */
    Optional<String> getUserName();

    /**
     * Gets the seat name of the seat. This is different than the seat ID and is not
     * guaranteed to be unique but most likely is.
     *
     * @return The seat name.
     */
    String getSeatName();

    /**
     * Indicates whether a seat is FREE, RESERVED or OCCUPIED.
     */
    enum Status {
        FREE, RESERVED, OCCUPIED
    }
}
