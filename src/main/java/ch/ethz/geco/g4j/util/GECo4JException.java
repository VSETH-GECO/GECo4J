package ch.ethz.geco.g4j.util;

public class GECo4JException extends RuntimeException {
    private String message;

    public GECo4JException(String message) {
        super(message);
        this.message = message;
    }

    public GECo4JException(String message, Throwable cause) {
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
