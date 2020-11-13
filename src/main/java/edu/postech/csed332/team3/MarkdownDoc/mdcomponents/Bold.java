package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

public class Bold implements MarkdownString {
    private final MarkdownString mds;

    public Bold(MarkdownString mds) {
        this.mds = mds;
    }

    @Override
    public String getHTMLString() {
        return "<strong>" + mds.getHTMLString() + "</strong>";
    }
}
