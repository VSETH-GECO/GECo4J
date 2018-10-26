package ch.ethz.geco.g4j.internal.json;

public class NewsObject {
    public String title;
    public String description;
    public String url;
    public Boolean is_draft;
    public AuthorObject author;
    public FooterObject footer;

    public class AuthorObject {
        public String name;
        public String url;
        public String icon_url;
    }

    public class FooterObject {
        public String text;
    }
}
