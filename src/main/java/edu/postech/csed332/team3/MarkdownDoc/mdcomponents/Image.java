package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

public class Image implements MarkdownString {
    private final String src;
    private final String alt;

    public Image(String src, String alt) {
        this.src = src;
        this.alt = alt;
    }

    @Override
    public String getHTMLString() {
        return "<img src=\"" + src + "\" alt=\"" + alt + "\">";
    }
}
