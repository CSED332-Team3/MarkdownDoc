package edu.postech.csed332.team3.markdowndoc.converter;

import edu.postech.csed332.team3.markdowndoc.converter.MarkdownParser;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownParserTest {
    @NotNull
    static String parseReroute(@NotNull String str) {
        String toParse = "!!mdDoc\n" + str;
        return MarkdownParser.parse(toParse);
    }

    /**
     * Test isMarkdown method in MarkdownParser.
     */
    @Test
    void testIsMarkdown() {
        assertTrue(MarkdownParser.isMarkdown("!!mdDoc"));
        assertTrue(MarkdownParser.isMarkdown(" \t\n!!mdDoc"));
        assertTrue(MarkdownParser.isMarkdown("!!mdDoc\nCSED332\nWOMBO COMBO\n!!!!!"));
        assertFalse(MarkdownParser.isMarkdown("??mdDoc"));
        assertFalse(MarkdownParser.isMarkdown("!! mdDoc"));
    }

    /**
     * Test Commonmark library.
     */
    @Test
    void testEmbeddedHtml() {
        assertEquals("<p><strike>test</strike></p>\n", parseReroute("<strike>test</strike>"));
    }

    /**
     * Test header grammar.
     */
    @Test
    void testIsHeader() {
        assertEquals("<h1>header1</h1>\n", parseReroute("# header1"));
        assertEquals("<h2>header2</h2>\n", parseReroute("## header2"));
        assertEquals("<h3>header3</h3>\n", parseReroute("### header3"));
        assertEquals("<h4>header4</h4>\n", parseReroute("#### header4"));
        assertEquals("<h5>header5</h5>\n", parseReroute("##### header5"));
        assertEquals("<h6>header6</h6>\n", parseReroute("###### header6"));
    }

    /**
     * Test list grammar.
     */
    @Test
    void testIsList() {
        assertEquals("<ul>\n" +
                "<li>list</li>\n" +
                "</ul>\n", parseReroute("* list"));
    }

    /**
     * Test image grammar.
     */
    @Test
    void testIsImage() {
        assertEquals("<p><img src=\"\" alt=\"Image\" /></p>\n", parseReroute("![Image]()"));
        assertEquals("<p><a href=\"#\"><img src=\"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png\"></a></p>\n",
                parseReroute("<a href=\"#\"><img src=\"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png\"></a> "));
    }

    /**
     * Test link grammar.
     */
    @Test
    void testIsLink() {
        assertEquals("<p><a href=\"https://csed332.postech.ac.kr/team3-2020/MarkdownDoc\">MarkdownDoc</a></p>\n",
                parseReroute("[MarkdownDoc](https://csed332.postech.ac.kr/team3-2020/MarkdownDoc)"));
    }

    /**
     * Test code block grammar.
     */
    @Test
    void testIsCodeBlock() {
        assertEquals("<pre><code class=\"language-javascript\">function test() {\n" +
                "console.log(&quot;hello world!&quot;);\n" +
                "}\n" +
                "</code></pre>\n", parseReroute("```javascript\nfunction test() {\n console.log(\"hello world!\");\n}\n```"));
    }

    /**
     * Test block quote grammar.
     */
    @Test
    void testIsBlockquote() {
        assertEquals("<blockquote>\n" +
                "<p>MarkdownDoc</p>\n" +
                "</blockquote>\n", parseReroute("> MarkdownDoc"));
    }

    /**
     * Test inline code grammar.
     */
    @Test
    void testIsInlineCode() {
        assertEquals("<p><code>printf(&quot;Hello world!&quot;)</code></p>\n",
                parseReroute("`printf(\"Hello world!\")`"));
    }

    /**
     * Test horizontal bar grammar.
     */
    @Test
    void testIsHorizontal() {
        assertEquals("<hr />\n", parseReroute("---"));
        assertEquals("<hr />\n", parseReroute("***"));
        assertEquals("<hr />\n", parseReroute("___"));
    }

    /**
     * Test badge grammar.
     */
    @Test
    void testIsBadge() {
        assertEquals("<p><a href=\"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png\">https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png</a></p>\n",
                parseReroute("<https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png>"));
    }

    /**
     * Test bold text grammar.
     */
    @Test
    void testIsBold() {
        assertEquals("<p><strong>bold</strong></p>\n", parseReroute("**bold**"));
        assertEquals("<p><strong>bold</strong></p>\n", parseReroute("__bold__"));
    }

    /**
     * Test italic text grammar.
     */
    @Test
    void testIsItalic() {
        assertEquals("<p><em>italic</em></p>\n", parseReroute("*italic*"));
        assertEquals("<p><em>italic</em></p>\n", parseReroute("_italic_"));
    }

    /**
     * Test underline text grammar.
     */
    @Test
    void testIsUnderline() {
        assertEquals("<p><u>underline</u></p>\n", parseReroute("<u>underline</u>"));
    }

    /**
     * Test strikethrough grammar.
     */
    @Test
    void testIsStrikeThrough() {
        assertEquals("<p><strike>StrikeThrough</strike></p>\n", parseReroute("~~StrikeThrough~~"));
    }

    /**
     * Test strikethrough grammar.
     */
    @Test
    void testOddStrikeThrough() {
        assertEquals("<p><strike>StrikeThrough</strike>odd tilde~~</p>\n", parseReroute("~~StrikeThrough~~odd tilde~~"));
        assertEquals("<p>odd tilde~~</p>\n", parseReroute("odd tilde~~"));
    }

    @Test
    void testTableBarRecognition() {
        // Header.
        assertEquals("<p>asdf|asdf|asdf|\n" +
                        "|---|---|---|\n" +
                        "|asdf|asdf|asdf|</p>\n",
                parseReroute("asdf|asdf|asdf|\n" +
                        "|---|---|---|\n" +
                        "|asdf|asdf|asdf|\n"));
        assertEquals("<p>|asdf|asdf|asdf\n" +
                        "|---|---|---|\n" +
                        "|asdf|asdf|asdf|</p>\n",
                parseReroute("|asdf|asdf|asdf\n" +
                        "|---|---|---|\n" +
                        "|asdf|asdf|asdf|\n"));
        // Divider.
        assertEquals("<p>|asdf|asdf|asdf|\n" +
                        "---|---|---|\n" +
                        "|asdf|asdf|asdf|\n" +
                        "|asdf|asdf|asdf|</p>\n",
                parseReroute("|asdf|asdf|asdf|\n" +
                        "---|---|---|\n" +
                        "|asdf|asdf|asdf|\n" +
                        "|asdf|asdf|asdf|"));
        assertEquals("<p>|asdf|asdf|asdf|\n" +
                        "|---|---|---\n" +
                        "|asdf|asdf|asdf|\n" +
                        "|asdf|asdf|asdf|</p>\n",
                parseReroute("|asdf|asdf|asdf|\n" +
                        "|---|---|---\n" +
                        "|asdf|asdf|asdf|\n" +
                        "|asdf|asdf|asdf|"));

        // Details.
        assertEquals("<table>\n" +
                        "<thead>\n" +
                        "<tr>\n" +
                        "<th>asdf</th>\n" +
                        "<th>asdf</th>\n" +
                        "<th>asdf</th>\n" +
                        "</tr>\n" +
                        "</thead>\n" +
                        "<tbody>\n" +
                        "</tbody>\n" +
                        "</table>\n" +
                        "asdf|asdf|asdf|\n",
                parseReroute("|asdf|asdf|asdf|\n" +
                        "|---|---|---|\n" +
                        "asdf|asdf|asdf|\n"));
        assertEquals("<table>\n" +
                        "<thead>\n" +
                        "<tr>\n" +
                        "<th>asdf</th>\n" +
                        "<th>asdf</th>\n" +
                        "<th>asdf</th>\n" +
                        "</tr>\n" +
                        "</thead>\n" +
                        "<tbody>\n" +
                        "</tbody>\n" +
                        "</table>\n" +
                        "|asdf|asdf|asdf\n",
                parseReroute("|asdf|asdf|asdf|\n" +
                        "|---|---|---|\n" +
                        "|asdf|asdf|asdf\n"));

        // Complete form.
        assertEquals("<table>\n" +
                        "<thead>\n" +
                        "<tr>\n" +
                        "<th>asdf</th>\n" +
                        "<th>asdf</th>\n" +
                        "<th>asdf</th>\n" +
                        "</tr>\n" +
                        "</thead>\n" +
                        "<tbody>\n" +
                        "<tr>\n" +
                        "<td>asdf</td>\n" +
                        "<td>asdf</td>\n" +
                        "<td>asdf</td>\n" +
                        "</tr>\n" +
                        "</tbody>\n" +
                        "</table>\n",
                parseReroute("|asdf|asdf|asdf|\n" +
                        "|---|---|---|\n" +
                        "|asdf|asdf|asdf|\n"));
    }

    /**
     * Test table grammar.
     */
    @Test
    void testParseTable() {
        assertEquals("<table>\n" +
                        "<thead>\n" +
                        "<tr>\n" +
                        "<th>asdf</th>\n" +
                        "<th>asdf</th>\n" +
                        "<th>asdf</th>\n" +
                        "</tr>\n" +
                        "</thead>\n" +
                        "<tbody>\n" +
                        "<tr>\n" +
                        "<td>asdf</td>\n" +
                        "<td>asdf</td>\n" +
                        "<td>asdf</td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td>asdf</td>\n" +
                        "<td>asdf</td>\n" +
                        "<td>asdf</td>\n" +
                        "</tr>\n" +
                        "</tbody>\n" +
                        "</table>\n",
                parseReroute("|asdf|asdf|asdf|\n" +
                        "|---|---|---|\n" +
                        "|asdf|asdf|asdf|\n" +
                        "|asdf|asdf|asdf|"));
        assertEquals("<table>\n" +
                        "<thead>\n" +
                        "<tr>\n" +
                        "<th>asdf</th>\n" +
                        "<th>asdf</th>\n" +
                        "<th>asdf</th>\n" +
                        "</tr>\n" +
                        "</thead>\n" +
                        "<tbody>\n" +
                        "<tr>\n" +
                        "<td>asdf</td>\n" +
                        "<td>asdf</td>\n" +
                        "<td>asdf</td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td>asdf</td>\n" +
                        "<td>asdf</td>\n" +
                        "</tr>\n" +
                        "</tbody>\n" +
                        "</table>\n",
                parseReroute("|asdf|asdf|asdf|\n" +
                        "|---|---|---|\n" +
                        "|asdf|asdf|asdf|\n" +
                        "|asdf|asdf|"));
        assertEquals("<p>|asdf|asdf|asdf|\n" +
                        "|---|---|\n" +
                        "|asdf|asdf|asdf|\n" +
                        "|asdf|asdf|asdf|</p>\n",
                parseReroute("|asdf|asdf|asdf|\n" +
                        "|---|---|\n" +
                        "|asdf|asdf|asdf|\n" +
                        "|asdf|asdf|asdf|"));
        assertEquals("<p>|asdf|asdf|\n" +
                        "|---|---|---|\n" +
                        "|asdf|asdf|asdf|\n" +
                        "|asdf|asdf|asdf|</p>\n",
                parseReroute("|asdf|asdf|\n" +
                        "|---|---|---|\n" +
                        "|asdf|asdf|asdf|\n" +
                        "|asdf|asdf|asdf|"));
        assertEquals("<p>|asdf|asdf|asdf|\n" +
                        "---||---\n" +
                        "|asdf|asdf|asdf|\n" +
                        "|asdf|asdf|asdf|</p>\n",
                parseReroute("|asdf|asdf|asdf|\n" +
                        "---||---\n" +
                        "|asdf|asdf|asdf|\n" +
                        "|asdf|asdf|asdf|"));
        assertEquals("<table>\n" +
                        "<thead>\n" +
                        "<tr>\n" +
                        "<th>asdf</th>\n" +
                        "<th>asdf</th>\n" +
                        "</tr>\n" +
                        "</thead>\n" +
                        "<tbody>\n" +
                        "<tr>\n" +
                        "<td>asdf</td>\n" +
                        "<td>asdf</td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td>asdf</td>\n" +
                        "<td>asdf</td>\n" +
                        "</tr>\n" +
                        "</tbody>\n" +
                        "</table>\n",
                parseReroute("|asdf|asdf|\n" +
                        "|---|---|\n" +
                        "|asdf|asdf|asdf|\n" +
                        "|asdf|asdf|asdf|"));
        assertEquals("<table>\n" +
                        "<thead>\n" +
                        "<tr>\n" +
                        "<th>asdf</th>\n" +
                        "<th>asdf</th>\n" +
                        "<th>asdf</th>\n" +
                        "</tr>\n" +
                        "</thead>\n" +
                        "<tbody>\n" +
                        "<tr>\n" +
                        "<td>asdf</td>\n" +
                        "<td>asdf</td>\n" +
                        "<td>asdf</td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td>asdf</td>\n" +
                        "<td>asdf</td>\n" +
                        "<td>asdf</td>\n" +
                        "</tr>\n" +
                        "</tbody>\n" +
                        "</table>\n" +
                        "<p>asdfasdfasdf</p>\n",
                parseReroute("|asdf|asdf|asdf|\n" +
                        "|---|---|---|\n" +
                        "|asdf|asdf|asdf|\n" +
                        "|asdf|asdf|asdf|\n\n" +
                        "asdfasdfasdf"));
    }

    /**
     * Test checkbox grammar.
     */
    @Test
    void testParseCheckBox() {
        assertEquals("<ul>\n" +
                        "<li>[ ]</li>\n" +
                        "</ul>\n",
                parseReroute("- [ ]"));
        assertEquals("<ul>\n" +
                "<li>[x]</li>\n" +
                "</ul>\n", parseReroute("- [x]"));
        assertEquals("<ul>\n" +
                "<li>[ ]asdf</li>\n" +
                "</ul>\n", parseReroute("- [ ]asdf"));
        assertEquals("<ul>\n" +
                "<li>[x]asdf</li>\n" +
                "</ul>\n", parseReroute("- [x]asdf"));
        assertEquals("<ul>\n" +
                "<li><input type=\"checkbox\" disabled>asdf</li>\n" +
                "</ul>\n", parseReroute("- [ ] asdf"));
        assertEquals("<ul>\n" +
                "<li><input type=\"checkbox\" checked disabled>asdf</li>\n" +
                "</ul>\n", parseReroute("- [x] asdf"));
        assertEquals("<p>asdf - [ ] asdf</p>\n", parseReroute("asdf - [ ] asdf"));
        assertEquals("<p>asdf - [x] asdf</p>\n", parseReroute("asdf - [x] asdf"));
        assertEquals("<ul>\n" +
                "<li>[ ]</li>\n" +
                "</ul>\n", parseReroute("   - [ ]"));
        assertEquals("<ul>\n" +
                "<li>[x]</li>\n" +
                "</ul>\n", parseReroute("   - [x]"));
        assertEquals("<ul>\n" +
                "<li>[ ]asdf</li>\n" +
                "</ul>\n", parseReroute("   - [ ]asdf"));
        assertEquals("<ul>\n" +
                "<li>[x]asdf</li>\n" +
                "</ul>\n", parseReroute("   - [x]asdf"));
        assertEquals("<ul>\n" +
                "<li><input type=\"checkbox\" disabled>asdf</li>\n" +
                "</ul>\n", parseReroute("   - [ ] asdf"));
        assertEquals("<ul>\n" +
                "<li><input type=\"checkbox\" checked disabled>asdf</li>\n" +
                "</ul>\n", parseReroute("   - [x] asdf"));
    }
}
