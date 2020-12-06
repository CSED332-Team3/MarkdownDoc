package edu.postech.csed332.team3.markdowndoc.explorer;

import edu.postech.csed332.team3.markdowndoc.converter.MarkdownParser;

public class FileManagerStub {
    private final StringBuilder stringBuilder = new StringBuilder();
    String path;

    public FileManagerStub(String path) {
        this.path = path;
    }

    public String write(String id, String name, String doc) {
        return stringBuilder
                .append(appendId(id))
                .append(name)
                .append("\n")
                .append(appendComment(doc))
                .append("</p>\n").toString();
    }

    public String close() {
        String str = stringBuilder.toString();
        stringBuilder.setLength(0);
        return str;
    }

    /**
     * Apply code markdown grammar to visualize doc comment.
     *
     * @param docComment comment for editing.
     * @return edited doc comment.
     */
    public String appendComment(String docComment) {
        if (docComment != null)
            return MarkdownParser.parse(docComment);
        return "";
    }

    public String appendId(String type) {
        switch (type) {
            case "class":
                return "<p id=\"c\">\n";
            case "method":
                return "<p id=\"m\">\n";
            case "field":
                return "<p id=\"f\">\n";
        }
        return "<p>\n";
    }
}
