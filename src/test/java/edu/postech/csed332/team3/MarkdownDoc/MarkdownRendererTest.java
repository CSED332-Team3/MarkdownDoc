package edu.postech.csed332.team3.MarkdownDoc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MarkdownRendererTest {

    @Test
    public void testIsMarkdown() {
        assertTrue(MarkdownParser.isMarkdown("!!mdDoc"));
    }

    @Test
    public void testIsHeader() {
        assertEquals("<h1>header1</h1>\n", MarkdownParser.parse("# header1"));
        assertEquals("<h2>header2</h2>\n", MarkdownParser.parse("## header2"));
        assertEquals("<h3>header3</h3>\n", MarkdownParser.parse("### header3"));
        assertEquals("<h4>header4</h4>\n", MarkdownParser.parse("#### header4"));
        assertEquals("<h5>header5</h5>\n", MarkdownParser.parse("##### header5"));
        assertEquals("<h6>header6</h6>\n", MarkdownParser.parse("###### header6"));
    }

    @Test
    public void testIsList() {
        assertEquals("<ul>\n" +
                "<li>list</li>\n" +
                "</ul>\n", MarkdownParser.parse("* list"));
    }

    @Test
    public void testIsImage() {
        assertEquals("<p><img src=\"\" alt=\"Image\" /></p>\n", MarkdownParser.parse("![Image]()"));
        assertEquals("<p><a href=\"#\"><img src=\"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png\"></a></p>\n",
                MarkdownParser.parse("<a href=\"#\"><img src=\"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png\"></a> "));
    }

    @Test
    public void testIsLink() {
        assertEquals("<p><a href=\"https://csed332.postech.ac.kr/team3-2020/MarkdownDoc\">MarkdownDoc</a></p>\n",
                MarkdownParser.parse("[MarkdownDoc](https://csed332.postech.ac.kr/team3-2020/MarkdownDoc)"));
    }

    @Test
    public void testIsCodeBlock() {
        assertEquals("<pre><code class=\"language-javascript\">function test() {\n" +
                " console.log(&quot;hello world!&quot;);\n" +
                "}\n" +
                "</code></pre>\n", MarkdownParser.parse("```javascript\nfunction test() {\n console.log(\"hello world!\");\n}\n```"));
    }

    @Test
    public void testIsBlockquote() {
        assertEquals("<blockquote>\n" +
                "<p>MarkdownDoc</p>\n" +
                "</blockquote>\n", MarkdownParser.parse("> MarkdownDoc"));
    }

    @Test
    public void testIsInlineCode() {
        assertEquals("<p><code>printf(&quot;Hello world!&quot;)</code></p>\n", MarkdownParser.parse("`printf(\"Hello world!\")`"));
    }

    @Test
    public void testIsHorizontal() {
        assertEquals("<hr />\n", MarkdownParser.parse("---"));
        assertEquals("<hr />\n", MarkdownParser.parse("***"));
        assertEquals("<hr />\n", MarkdownParser.parse("___"));
    }

    @Test
    public void testIsBadge() {
        assertEquals("<p><a href=\"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png\">https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png</a></p>\n",
                MarkdownParser.parse("<https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png>"));
    }

    @Test
    public void testIsBold() {
        assertEquals("<p><strong>bold</strong></p>\n", MarkdownParser.parse("**bold**"));
        assertEquals("<p><strong>bold</strong></p>\n", MarkdownParser.parse("__bold__"));
    }

    @Test
    public void testIsItalic() {
        assertEquals("<p><em>italic</em></p>\n", MarkdownParser.parse("*italic*"));
        assertEquals("<p><em>italic</em></p>\n", MarkdownParser.parse("_italic_"));
    }

    @Test
    public void testIsUnderline() {
        assertEquals("<p><u>underline</u></p>\n", MarkdownParser.parse("<u>underline</u>"));
    }

//    @Test
//    public void testIsTable() {
//        assertEquals("", MarkdownParser.parse("|a|b|c|\n|---|---|---|\n|d|e|f|"));
//    }

//    @Test
//    public void testIsTaskList() {
//        assertEquals("<ul>\n" +
//                "<li>[ ] Test task list</li>\n" +
//                "</ul>\n", MarkdownParser.parse("- [ ] Test task list"));
//    }

//    @Test
//    public void testIsStrikeThrough() {
//        assertEquals("<strike>StrikeThrough</strike>\n", MarkdownParser.parse("~~StrikeThrough~~"));
//    }

//    @Test
//    public void testIsEmoji() {
//        assertEquals("", MarkdownParser.parse(":+1:"));
//    }
}
