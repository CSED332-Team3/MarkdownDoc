package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

import java.util.ArrayList;
import java.util.List;

public class Composite implements MarkdownString {
    private final List<MarkdownString> list;

    public Composite() {
        list = new ArrayList<>();
    }

    @Override
    public String getHTMLString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (MarkdownString markdownString : list) {
            stringBuilder.append(markdownString.getHTMLString());
        }
        return stringBuilder.toString();
    }

    public void addMarkdownString(MarkdownString mds) {
        list.add(mds);
    }
}
