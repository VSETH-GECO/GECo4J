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
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getURL() {
        return null;
    }
}
