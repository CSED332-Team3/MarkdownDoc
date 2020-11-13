package edu.postech.csed332.team3.MarkdownDoc.mdcomponents;

public class Badge implements MarkdownString {
    private final String src;
    private final String alt;
    private final String ref;


    public Badge(String src, String alt, String ref) {
        this.src = src;
        this.alt = alt;
        this.ref = ref;
    }

    @Override
    public String getHTMLString() {
        return "<a href=\"" + ref + "\"><img src=\"" + src + "\" alt=\"" + alt + "\"></a>";
    }
}
