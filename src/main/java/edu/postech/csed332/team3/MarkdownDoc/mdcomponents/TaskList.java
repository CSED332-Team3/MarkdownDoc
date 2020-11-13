package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

public class TaskList implements MarkdownString {
    private final MarkdownString mds;

    public TaskList(MarkdownString mds) {
        this.mds = mds;
    }

    @Override
    public String getHTMLString() {
        // TODO: 11/14/2020 What is for checkbox?
        return null;
    }
}
