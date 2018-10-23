package ch.ethz.geco.g4j.util;

import ch.ethz.geco.g4j.GECo4J;

/**
 * A GECo API exception.
 */
public class APIException extends RuntimeException {
    private final String message;
    private final Error error;

    public APIException(String message, Integer code) {
        super(message);
        this.message = message;

        switch (code) {
            case 400:
                this.error = Error.NOT_FOUND;
                break;
            case 403:
                this.error = Error.FORBIDDEN;
                break;
            case 424:
                this.error = Error.FAILED_DEPENDENCY;
                break;
            default:
                GECo4J.LOGGER.error(LogMarkers.API, "Invalid error code: {}", code);
                this.error = null;
        }
    }

    /**
     * Gets the error message.
     *
     * @return The error message.
     */
    public String getErrorMessage() {
        return message;
    }

    /**
     * Gets the error code.
     *
     * @return The error code.
     */
    public Error getError() {
        return error;
    }

    public enum Error {NOT_FOUND, FORBIDDEN, FAILED_DEPENDENCY}
}
