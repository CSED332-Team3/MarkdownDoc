package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.util.text.StringUtil;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class MarkdownParser {
    private MarkdownParser() {
    }

    /**
     * Parses mdDoc string.
     *
     * @param comment The comment to be parsed.
     * @return Parsed comment in HTML form.
     */
    @NotNull
    public static String parse(@NotNull String comment) {
        if (isMarkdown(comment)) {
            comment = comment.replaceFirst("!!mdDoc\n", "");
            comment = parseLoop(comment);
        }

        final Parser parser = Parser.builder().build();
        final Node node = parser.parse(comment);
        final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();

        return htmlRenderer.render(node);
    }

    /**
     * @param comment Comment text to check whether it is mdDoc.
     * @return The result.
     */
    public static boolean isMarkdown(@NotNull String comment) {
        try {
            return comment.matches("\\s*!!mdDoc(.|\\n)*");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Main parser loop.
     *
     * @param comment The comment to be parsed.
     * @return Parsed strikethrough, table, and checkbox md only.
     * @deprecated Planned change to private.
     */
    @Deprecated(since = "This function will be private soon.")
    @NotNull
    public static String parseLoop(@NotNull String comment) {
        final BufferedReader bufferedReader = new BufferedReader(new StringReader(comment));
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String line;
            @NotNull String prevLine = preprocess(bufferedReader.readLine());
            while ((line = bufferedReader.readLine()) != null) {
                line = preprocess(line);
                if (!line.matches("-{3,}") && line.matches("\\|?-{3,}(\\|-{3,})*\\|?")) {
                    final int columnNum = StringUtil.getOccurrenceCount(line, "-|-") + 1;
                    final String regex = String.format("\\|[^|]*(\\|[^|]*){%d}\\|", columnNum - 1);
                    if (prevLine.matches(regex)) {
                        stringBuilder.append("<table>\n");
                        stringBuilder.append("<thead>\n").append(parseTableHeader(prevLine)).append("</thead>\n");
                        stringBuilder.append("<tbody>\n");
                        while ((line = bufferedReader.readLine()) != null && !line.matches("\n?")) {
                            stringBuilder.append(parseTableDetails(line, columnNum));
                        }
                        stringBuilder.append("</tbody>\n");
                        stringBuilder.append("</table>\n");
                        if (line == null)
                            return stringBuilder.toString();
                        prevLine = line;
                        continue;
                    }
                }
                stringBuilder.append(prevLine).append("\n");
                prevLine = line;
            }
            stringBuilder.append(prevLine).append("\n");
        } catch (IOException io) {
            return comment;
        }

        return stringBuilder.toString();
    }

    @NotNull
    private static String preprocess(@NotNull String comment) {
        comment = comment.trim();
        comment = parseCheckBox(comment);
        comment = parseStrikethrough(comment);
        return comment;
    }

    @NotNull
    private static String parseStrikethrough(@NotNull String comment) {
        String strikethroughToken = "~~";
        int count = StringUtil.getOccurrenceCount(comment, strikethroughToken);
        if (count % 2 == 1) count--;

        while (count > 0) {
            comment = comment
                    .replaceFirst(strikethroughToken, "<strike>")
                    .replaceFirst(strikethroughToken, "</strike>");
            count -= 2;
        }

        return comment;
    }

    /**
     * Parses a table header.
     *
     * @param comment One line string to be parsed.
     * @return Comment in a table header HTML form.
     */
    @NotNull
    public static String parseTableHeader(@NotNull String comment) {
        StringBuilder stringBuilder = new StringBuilder().append("<tr>\n");
        String[] header = comment.split("\\|");
        for (String s : header) {
            if (s.equals(""))
                continue;
            stringBuilder.append("<th>").append(s).append("</th>\n");
        }
        stringBuilder.append("</tr>\n");

        return stringBuilder.toString();
    }

    /**
     * Parses a table detail.
     *
     * @param comment One line string to be parsed.
     * @return Comment in a table detail HTML form.
     */
    @NotNull
    public static String parseTableDetails(@NotNull String comment, int columnNum) {
        StringBuilder stringBuilder = new StringBuilder().append("<tr>\n");
        String[] strings = comment.split("\\|");
        int i = 0;
        if (comment.indexOf("|") == 0) {
            columnNum++;
            i++;
        }

        for (; i < columnNum; i++) {
            if (i >= strings.length)
                break;

            String detail = strings[i];
            stringBuilder.append("<td>").append(detail).append("</td>\n");
        }
        stringBuilder.append("</tr>\n");

        return stringBuilder.toString();
    }

    /**
     * Parses a checkbox.
     *
     * @param comment One line string to be parsed.
     * @return Comment with checkbox HTMLs.
     */
    @NotNull
    public static String parseCheckBox(@NotNull String comment) {
        if (comment.matches("^- \\[[ x]]\\s*"))
            return comment;
        return comment
                .replaceAll("^- \\[ ]\\s+", "<input type=\"checkbox\" disabled>")
                .replaceAll("^- \\[x]\\s+", "<input type=\"checkbox\" checked disabled>");
    }
}
