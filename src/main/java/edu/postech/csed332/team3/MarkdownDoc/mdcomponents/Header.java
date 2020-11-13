package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

public class Header implements MarkdownString {
    private final MarkdownString mds;
    private final int level;

    public Header(MarkdownString mds, int level) {
        this.mds = mds;
        this.level = level;
    }

    @Override
    public String getHTMLString() {
        final String s = "h" + level;
        return "<" + s + ">" + mds.getHTMLString() + "</" + s + ">";
    }
}
