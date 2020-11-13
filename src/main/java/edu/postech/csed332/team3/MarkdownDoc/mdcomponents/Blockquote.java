package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

public class Blockquote implements MarkdownString {
    private final MarkdownString mds;

    public Blockquote(MarkdownString mds) {
        this.mds = mds;
    }

    @Override
    public String getHTMLString() {
        return "<blockquote>" + mds.getHTMLString() + "</blockquote>";
    }
}
