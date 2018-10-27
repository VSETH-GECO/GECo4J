package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.obj.IEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event implements IEvent {
    private final Long id;
    private final String title;
    private final String description;
    private final String url;

    private static final Pattern idPattern = Pattern.compile("/(\\d+)/?");

    public Event(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;

        Matcher matcher = idPattern.matcher(url);

        if (matcher.find()) {
            id = Long.valueOf(matcher.group(1));
        } else {
            throw new RuntimeException("Could not construct Event object: Failed to get ID from URL.");
        }
    }

    @Override
    public Long getID() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getURL() {
        return url;
    }
}
