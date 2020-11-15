package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

public class TaskList implements MarkdownString {
    private final boolean isChecked;

    public TaskList(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public String getHTMLString() {
        if(isChecked)
            return "<input type=\"checkbox\" checked=\"checked\" readonly>";
        else
            return "<input type=\"checkbox\" readonly>";
    }
}
