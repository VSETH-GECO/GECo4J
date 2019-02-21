package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.obj.User;

import java.util.Optional;

public class WebUser implements User {
    private final Long id;
    private final String username;
    private final String usergroup;
    private final Long lolID;
    private final Long steamID;
    private final String bnetID;
    private final Long discordID;

    public WebUser(Long id, String username, String usergroup, Long lolID, Long steamID, String bnetID, Long discordID) {
        this.id = id;
        this.username = username;
        this.usergroup = usergroup;
        this.lolID = lolID;
        this.steamID = steamID;
        this.bnetID = bnetID;
        this.discordID = discordID;
    }

    @Override
    public Long getID() {
        return id;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getUserGroup() {
        return usergroup;
    }

    @Override
    public Optional<Long> getLoLID() {
        return Optional.ofNullable(lolID);
    }

    @Override
    public Optional<Long> getSteamID() {
        return Optional.ofNullable(steamID);
    }

    @Override
    public Optional<String> getBattleNetID() {
        return Optional.ofNullable(bnetID);
    }

    @Override
    public Optional<Long> getDiscordID() {
        return Optional.ofNullable(discordID);
    }
}
