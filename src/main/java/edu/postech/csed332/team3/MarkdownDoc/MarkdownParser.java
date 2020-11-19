package edu.postech.csed332.team3.MarkdownDoc;

import com.intellij.openapi.util.text.StringUtil;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownParser {

    private MarkdownParser() {
    }

    @NonNull
    public static String parse(@NonNull String comment) {
        comment = strikeThroughParse(comment);

        final Parser parser = Parser.builder().build();
        final Node node = parser.parse(comment);
        final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();

        return htmlRenderer.render(node);
    }

    @NonNull
    private static String strikeThroughParse(@NonNull String comment) {
//        if (!isMarkdown(comment))
//            throw new IllegalArgumentException("Not a md comment.");

        String strikeThroughToken = "~~";
        int count = StringUtil.getOccurrenceCount(comment, strikeThroughToken);
        if (count % 2 == 1) count--;

        while (count > 0) {
            if (count % 2 == 0)
                comment = comment.replaceFirst(strikeThroughToken, "<strike>");
            else
                comment = comment.replaceFirst(strikeThroughToken, "</strike>");
            count--;
        }

        return comment;
    }

    public static boolean isMarkdown(@NonNull String comment) {
        try {
            return comment.subSequence(0, 7).equals("!!mdDoc");
        } catch (Exception e) {
            return false;
        }
    }
}
