package ch.ethz.geco.g4j.obj;

public interface Event {
    /**
     * Gets the ID of the event post.
     *
     * @return The ID of the event post.
     */
    Long getID();

    /**
     * Gets the title of the event.
     *
     * @return the title of the event.
     */
    String getTitle();

    /**
     * Gets the description of the event.
     *
     * @return The description of the event.
     */
    String getDescription();

    /**
     * Gets the website URL of the event.
     *
     * @return The URL of the event.
     */
    String getURL();
}
