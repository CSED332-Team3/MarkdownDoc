package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

public class Underline implements MarkdownString {
    private final MarkdownString mds;

    public Underline(MarkdownString mds) {
        this.mds = mds;
    }

    @Override
    public String getHTMLString() {
        return "<ins>" + mds.getHTMLString() + "</ins>";
    }
}
