package ch.ethz.geco.g4j.impl;

import ch.ethz.geco.g4j.obj.INews;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class News implements INews {
    private final Long id;
    private final String title;
    private final String description;
    private final String url;
    private final Boolean isDraft;
    private final Long publishedAt;
    private final String authorName;
    private final String authorUrl;
    private final String authorIconUrl;
    private final String footer;

    private static final Pattern idPattern = Pattern.compile("/(\\d+)/?");

    public News(String title, String description, String url, Boolean isDraft, Long publishedAt, String authorName, String authorUrl, String authorIconUrl, String footer) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.isDraft = isDraft;
        this.publishedAt = publishedAt;
        this.authorName = authorName;
        this.authorUrl = authorUrl;
        this.authorIconUrl = authorIconUrl;
        this.footer = footer;

        Matcher matcher = idPattern.matcher(url);

        if (matcher.find()) {
            id = Long.valueOf(matcher.group(1));
        } else {
            throw new RuntimeException("Could not construct News object: Failed to get ID from URL.");
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

    @Override
    public Boolean isDraft() {
        return isDraft;
    }

    @Override
    public Long getPublishedAt() {
        return publishedAt;
    }

    @Override
    public String getAuthorName() {
        return authorName;
    }

    @Override
    public String getAuthorURL() {
        return authorUrl;
    }

    @Override
    public String getAuthorIconURL() {
        return authorIconUrl;
    }

    @Override
    public String getFooter() {
        return footer;
    }
}
