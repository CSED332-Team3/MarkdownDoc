package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

public class InlineCode implements MarkdownString {
    private final String str;

    public InlineCode(String str) {
        this.str = str;
    }

    @Override
    public String getHTMLString() {
        return "<code>" + str + "</code>";
    }
}
