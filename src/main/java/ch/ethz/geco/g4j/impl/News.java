package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.obj.INews;

public class News implements INews {
    private final String title;
    private final String description;
    private final String url;
    private final Boolean isDraft;
    private final String authorName;
    private final String authorUrl;
    private final String authorIconUrl;
    private final String footer;

    public News(String title, String description, String url, Boolean isDraft, String authorName, String authorUrl, String authorIconUrl, String footer) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.isDraft = isDraft;
        this.authorName = authorName;
        this.authorUrl = authorUrl;
        this.authorIconUrl = authorIconUrl;
        this.footer = footer;
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

    @Override
    public Boolean isDraft() {
        return null;
    }

    @Override
    public String getAuthorName() {
        return null;
    }

    @Override
    public String getAuthorURL() {
        return null;
    }

    @Override
    public String getAuthorIconURL() {
        return null;
    }

    @Override
    public String getFooter() {
        return null;
    }
}
