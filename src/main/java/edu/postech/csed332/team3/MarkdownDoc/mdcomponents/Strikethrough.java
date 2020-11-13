package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

public class Strikethrough implements MarkdownString {
    private final MarkdownString mds;

    public Strikethrough(MarkdownString mds) {
        this.mds = mds;
    }

    @Override
    public String getHTMLString() {
        return "<del>" + mds.getHTMLString() + "</del>";
    }
}
