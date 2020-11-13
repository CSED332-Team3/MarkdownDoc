package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

public class Horizontal implements MarkdownString {
    @Override
    public String getHTMLString() {
        return "<hr>";
    }
}
