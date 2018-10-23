package ch.ethz.geco.g4j.util;

public class GECoException extends RuntimeException {
    private String message;

    public GECoException(String message) {
        super(message);
        this.message = message;
    }

    public GECoException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    /**
     * Gets the error message.
     *
     * @return The error message.
     */
    public String getErrorMessage() {
        return message;
    }
}
