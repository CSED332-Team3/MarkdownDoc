package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

public class Link implements MarkdownString {
    private final String text;
    private final String link;

    public Link(String text, String link) {
        this.text = text;
        this.link = link;
    }

    @Override
    public String getHTMLString() {
        return "<a href=\"" + link + "\">" + text + "</a>";
    }
}
