package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

public class CodeBlock implements MarkdownString {
    private final String str;
    private final String brush;

    public CodeBlock(String str) {
        this(str, null);
    }

    public CodeBlock(String str, String brush) {
        this.str = str;
        this.brush = brush;
    }

    @Override
    public String getHTMLString() {
        if (brush != null) {
            return "<pre class=\"brush: " + brush + ";\">" + str + "</pre>";
        }
        return "<pre>" + str + "</pre>";
    }
}
