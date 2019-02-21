package ch.ethz.geco.g4j.obj;

import java.util.Optional;

/**
 * Represents a user on the website.
 */
public interface User {
    /**
     * Gets the user ID of the user on the website.
     *
     * @return The ID of the user.
     */
    Long getID();

    /**
     * Gets the username of the user on the website.
     *
     * @return The name of the user.
     */
    String getUserName();

    /**
     * Gets the group of the user on the website.
     *
     * @return The group of the user.
     */
    String getUserGroup();

    /**
     * Gets the League of Legends ID of the user on the website if available.
     *
     * @return The LoL ID of the user.
     */
    Optional<Long> getLoLID();

    /**
     * Gets the Steam ID of the user on the website if available.
     *
     * @return The Steam ID of the user.
     */
    Optional<Long> getSteamID();

    /**
     * Gets the Battle.net ID of the user on the website if available.
     *
     * @return The Battle.net ID of the user.
     */
    Optional<String> getBattleNetID();

    /**
     * Gets the Discord ID of the user on the website if available.
     *
     * @return The Discord ID of the user.
     */
    Optional<Long> getDiscordID();
}
