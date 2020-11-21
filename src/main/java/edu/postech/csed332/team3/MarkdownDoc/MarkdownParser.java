package edu.postech.csed332.team3.MarkdownDoc;

import com.intellij.openapi.util.text.StringUtil;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.nullness.qual.NonNull;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class MarkdownParser {

    private MarkdownParser() {
    }

    @NonNull
    public static String parse(@NonNull String comment) {
        checkValidity(comment);

        comment = parseStrikethrough(comment);

        final Parser parser = Parser.builder().build();
        final Node node = parser.parse(comment);
        final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();

        return htmlRenderer.render(node);
    }

    public static boolean isMarkdown(@NonNull String comment) {
        return true;
//        try {
//            return comment.subSequence(0, 7).equals("!!mdDoc");
//        } catch (Exception e) {
//            return false;
//        }
    }

    @NonNull
    private static String parseStrikethrough(@NonNull String comment) {
        checkValidity(comment);

        String strikethroughToken = "~~";
        int count = StringUtil.getOccurrenceCount(comment, strikethroughToken);
        if (count % 2 == 1) count--;

        while (count > 0) {
            comment = comment.replaceFirst(strikethroughToken, "<strike>");
            comment = comment.replaceFirst(strikethroughToken, "</strike>");
            count -= 2;
        }

        return comment;
    }

    @NonNull
    private static String parseTable(@NonNull String comment) {


        final BufferedReader bufferedReader = new BufferedReader(new StringReader(comment));
        try {
            String line;
            String prevLine = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                if (line.matches("-{3,}")) {
                    prevLine = line;
                    continue;
                }
                if (line.matches("\\|?-{3,}(\\|-{3,})*\\|?")) {
                    final int dividers = StringUtil.getOccurrenceCount(line, "-|-");
                    final String regex = String.format("\\|?[^|]*(\\|[^|]*){%d}\\|?", dividers);
                    if (prevLine.matches(regex)) {
                        // Main code



                    }
                }
                prevLine = line;
            }
        } catch (IOException io) {
            return comment;
        }




        return comment;
    }

    private static void checkValidity(@NonNull String comment) {
        if (!isMarkdown(comment))
            throw new IllegalArgumentException("Not a md comment.");
    }
}
