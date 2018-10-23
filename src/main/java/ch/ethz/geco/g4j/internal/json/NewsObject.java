package ch.ethz.geco.g4j.internal.json;

public class NewsObject {
    String title;
    String description;
    String url;
    Boolean is_draft;
    AuthorObject author;
    FooterObject footer;

    public class AuthorObject {
        String name;
        String url;
        String icon_url;
    }

    public class FooterObject {
        String text;
    }
}
