package ch.ethz.geco.g4j.obj;

public interface News {
    /**
     * Gets the ID of the news post.
     *
     * @return The ID of the news post.
     */
    Long getID();

    /**
     * Gets the title of the news post.
     *
     * @return The title of the news post.
     */
    String getTitle();

    /**
     * Gets the description of the news post.
     *
     * @return The description of the news post.
     */
    String getDescription();

    /**
     * Gets the URL of the news post on the website.
     *
     * @return The URL of the news post.
     */
    String getURL();

    /**
     * Gets whether or not the news post is a draft.
     *
     * @return True if the news post is a draft, false otherwise.
     */
    Boolean isDraft();

    /**
     * Gets the time when the news post was published as a UNIX timestamp.
     *
     * @return The UNIX time when the news post was published or null if it is a draft.
     */
    Long getPublishedAt();

    /**
     * Gets the username on the website.
     *
     * @return The username of the author.
     */
    String getAuthorName();

    /**
     * Gets the URL of the user profile on the website.
     *
     * @return The URL of the author.
     */
    String getAuthorURL();

    /**
     * Gets the URL of the user avatar on the website.
     *
     * @return The URL of the author avatar.
     */
    String getAuthorIconURL();

    /**
     * Gets the footer text of the news post.
     *
     * @return The footer of the news post.
     */
    String getFooter();
}
