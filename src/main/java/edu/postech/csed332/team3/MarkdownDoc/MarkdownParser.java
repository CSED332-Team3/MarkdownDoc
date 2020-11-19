package edu.postech.csed332.team3.MarkdownDoc;

import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownParser {

    private MarkdownParser() {
    }

    @NonNull
    public static String parse(@NonNull String comment) {
//        if (!isMarkdown(comment))
//            throw new IllegalArgumentException("Not a md comment.");

        final Parser parser = Parser.builder().build();
        final Node node = parser.parse(comment);
        final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();

        return htmlRenderer.render(node);
    }

    public static boolean isMarkdown(@NonNull String comment) {
        try {
            return comment.subSequence(0, 7).equals("!!mdDoc");
        } catch (Exception e) {
            return false;
        }
    }
}
