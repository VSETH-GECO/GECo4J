package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.obj.IEvent;

public class Event implements IEvent {
    private final String title;
    private final String description;
    private final String url;

    public Event(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
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
