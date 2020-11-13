package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

public class Italic implements MarkdownString {
    private final MarkdownString mds;

    public Italic(MarkdownString mds) {
        this.mds = mds;
    }

    @Override
    public String getHTMLString() {
        return "<em>" + mds.getHTMLString() + "</em>";
    }
}
