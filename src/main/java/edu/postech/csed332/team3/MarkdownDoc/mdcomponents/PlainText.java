package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

public class PlainText implements MarkdownString {
    private final String str;

    public PlainText(String str) {
        this.str = str;
    }

    @Override
    public String getHTMLString() {
        return str;
    }

    // Simple wrapper.
    public static MarkdownString of(String text) {
        return () -> text;
    }
}
