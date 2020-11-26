package edu.postech.csed332.team3.markdowndoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownParserTest {

    @Test
    void testIsMarkdown() {
        assertTrue(MarkdownParser.isMarkdown("!!mdDoc"));
        assertTrue(MarkdownParser.isMarkdown("!!mdDoc\nCSED332"));
        assertFalse(MarkdownParser.isMarkdown("??mdDoc"));
        assertFalse(MarkdownParser.isMarkdown("??md"));
    }

    @Test
    void testParseFilter() {
        assertThrows(IllegalArgumentException.class, () -> MarkdownParser.parse("Not a md comment!"));
        assertDoesNotThrow(() -> MarkdownParser.parse("!!mdDoc\nThis is a md comment!"));
    }

    @Test
    void testHrRecognition() {
        assertFalse("-".matches("-{3,}"));
        assertFalse("--".matches("-{3,}"));
        assertTrue("---".matches("-{3,}"));
        assertTrue("----".matches("-{3,}"));
    }

    @Test
    void testTableRecognition() {
        assertTrue("---".matches("\\|?-{3,}(\\|-{3,})*\\|?"));
        assertTrue("|---".matches("\\|?-{3,}(\\|-{3,})*\\|?"));
        assertTrue("---|".matches("\\|?-{3,}(\\|-{3,})*\\|?"));
        assertTrue("|---|".matches("\\|?-{3,}(\\|-{3,})*\\|?"));
        assertTrue("---|---|".matches("\\|?-{3,}(\\|-{3,})*\\|?"));
        assertFalse("--|--|---|----|---".matches("\\|?-{3,}(\\|-{3,})*\\|?"));
        assertFalse("-|--".matches("\\|?-{3,}(\\|-{3,})*\\|?"));
        assertTrue("|---|----".matches("\\|?-{3,}(\\|-{3,})*\\|?"));
        assertFalse("|---|--".matches("\\|?-{3,}(\\|-{3,})*\\|?"));
        assertTrue("|---|---|----|---|----|---|".matches("\\|?-{3,}(\\|-{3,})*\\|?"));
    }

    @Test
    void testEscape() {
        assertEquals("<p><strike>test</strike></p>\n", MarkdownParser.parse("!!mdDoc\n<strike>test</strike>"));
    }

    @Test
    void testIsHeader() {
        assertEquals("<h1>header1</h1>\n", MarkdownParser.parse("!!mdDoc\n# header1"));
        assertEquals("<h2>header2</h2>\n", MarkdownParser.parse("!!mdDoc\n## header2"));
        assertEquals("<h3>header3</h3>\n", MarkdownParser.parse("!!mdDoc\n### header3"));
        assertEquals("<h4>header4</h4>\n", MarkdownParser.parse("!!mdDoc\n#### header4"));
        assertEquals("<h5>header5</h5>\n", MarkdownParser.parse("!!mdDoc\n##### header5"));
        assertEquals("<h6>header6</h6>\n", MarkdownParser.parse("!!mdDoc\n###### header6"));
    }

    @Test
    void testIsList() {
        assertEquals("<ul>\n" +
                "<li>list</li>\n" +
                "</ul>\n", MarkdownParser.parse("!!mdDoc\n* list"));
    }

    @Test
    void testIsImage() {
        assertEquals("<p><img src=\"\" alt=\"Image\" /></p>\n", MarkdownParser.parse("!!mdDoc\n![Image]()"));
        assertEquals("<p><a href=\"#\"><img src=\"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png\"></a></p>\n",
                MarkdownParser.parse("!!mdDoc\n<a href=\"#\"><img src=\"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png\"></a> "));
    }

    @Test
    void testIsLink() {
        assertEquals("<p><a href=\"https://csed332.postech.ac.kr/team3-2020/MarkdownDoc\">MarkdownDoc</a></p>\n",
                MarkdownParser.parse("!!mdDoc\n[MarkdownDoc](https://csed332.postech.ac.kr/team3-2020/MarkdownDoc)"));
    }

    @Test
    void testIsCodeBlock() {
        assertEquals("<pre><code class=\"language-javascript\">function test() {\n" +
                " console.log(&quot;hello world!&quot;);\n" +
                "}\n" +
                "</code></pre>\n", MarkdownParser.parse("!!mdDoc\n```javascript\nfunction test() {\n console.log(\"hello world!\");\n}\n```"));
    }

    @Test
    void testIsBlockquote() {
        assertEquals("<blockquote>\n" +
                "<p>MarkdownDoc</p>\n" +
                "</blockquote>\n", MarkdownParser.parse("!!mdDoc\n> MarkdownDoc"));
    }

    @Test
    void testIsInlineCode() {
        assertEquals("<p><code>printf(&quot;Hello world!&quot;)</code></p>\n",
                MarkdownParser.parse("!!mdDoc\n`printf(\"Hello world!\")`"));
    }

    @Test
    void testIsHorizontal() {
        assertEquals("<hr />\n", MarkdownParser.parse("!!mdDoc\n---"));
        assertEquals("<hr />\n", MarkdownParser.parse("!!mdDoc\n***"));
        assertEquals("<hr />\n", MarkdownParser.parse("!!mdDoc\n___"));
    }

    @Test
    void testIsBadge() {
        assertEquals("<p><a href=\"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png\">https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png</a></p>\n",
                MarkdownParser.parse("!!mdDoc\n<https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png>"));
    }

    @Test
    void testIsBold() {
        assertEquals("<p><strong>bold</strong></p>\n", MarkdownParser.parse("!!mdDoc\n**bold**"));
        assertEquals("<p><strong>bold</strong></p>\n", MarkdownParser.parse("!!mdDoc\n__bold__"));
    }

    @Test
    void testIsItalic() {
        assertEquals("<p><em>italic</em></p>\n", MarkdownParser.parse("!!mdDoc\n*italic*"));
        assertEquals("<p><em>italic</em></p>\n", MarkdownParser.parse("!!mdDoc\n_italic_"));
    }

    @Test
    void testIsUnderline() {
        assertEquals("<p><u>underline</u></p>\n", MarkdownParser.parse("!!mdDoc\n<u>underline</u>"));
    }

    @Test
    void testIsStrikeThrough() {
        assertEquals("<p><strike>StrikeThrough</strike></p>\n", MarkdownParser.parse("!!mdDoc\n~~StrikeThrough~~"));
    }

    @Test
    void testOddStrikeThrough() {
        assertEquals("<p><strike>StrikeThrough</strike>odd tilde~~</p>\n", MarkdownParser.parse("!!mdDoc\n~~StrikeThrough~~odd tilde~~"));
        assertEquals("<p>odd tilde~~</p>\n", MarkdownParser.parse("!!mdDoc\nodd tilde~~"));
    }

    @Test
    void testParseTableHeader() {
        assertEquals("<tr>\n<th>asdf</th>\n<th>qwer</th>\n<th>asdf</th>\n</tr>\n"
                , MarkdownParser.parseTableHeader("asdf|qwer|asdf"));
        assertEquals("<tr>\n<th>asdf</th>\n<th>qwer</th>\n<th>asdf</th>\n</tr>\n"
                , MarkdownParser.parseTableHeader("|asdf|qwer|asdf"));
        assertEquals("<tr>\n<th>asdf</th>\n<th>qwer</th>\n<th>asdf</th>\n</tr>\n"
                , MarkdownParser.parseTableHeader("asdf|qwer|asdf|"));
        assertEquals("<tr>\n<th>asdf</th>\n<th>qwer</th>\n<th>asdf</th>\n</tr>\n"
                , MarkdownParser.parseTableHeader("|asdf|qwer|asdf|"));
    }

    @Test
    void testParseTableDetails() {
        assertEquals("<tr>\n<td>asdf</td>\n<td>qwer</td>\n<td>asdf</td>\n</tr>\n"
                , MarkdownParser.parseTableDetails("asdf|qwer|asdf", 3));
        assertEquals("<tr>\n<td>asdf</td>\n<td>qwer</td>\n</tr>\n"
                , MarkdownParser.parseTableDetails("asdf|qwer", 3));
        assertEquals("<tr>\n<td>asdf</td>\n<td>qwer</td>\n</tr>\n"
                , MarkdownParser.parseTableDetails("asdf|qwer|", 3));
        assertEquals("<tr>\n<td>asdf</td>\n<td>qwer</td>\n</tr>\n"
                , MarkdownParser.parseTableDetails("|asdf|qwer|", 3));
        assertEquals("<tr>\n<td>asdf</td>\n<td>qwer</td>\n<td>asdf</td>\n</tr>\n"
                , MarkdownParser.parseTableDetails("asdf|qwer|asdf|qwer|", 3));
        assertEquals("<tr>\n<td>asdf</td>\n<td>qwer</td>\n<td>asdf</td>\n</tr>\n"
                , MarkdownParser.parseTableDetails("asdf|qwer|asdf|qwer", 3));
        assertEquals("<tr>\n<td>asdf</td>\n<td>qwer</td>\n<td>asdf</td>\n</tr>\n"
                , MarkdownParser.parseTableDetails("|asdf|qwer|asdf|", 3));
        assertEquals("<tr>\n<td>asdf</td>\n<td>qwer</td>\n</tr>\n"
                , MarkdownParser.parseTableDetails("|asdf|qwer||", 3));
        assertEquals("<tr>\n<td>asdf</td>\n<td></td>\n<td>qwer</td>\n</tr>\n"
                , MarkdownParser.parseTableDetails("|asdf||qwer|", 3));
        assertEquals("<tr>\n<td></td>\n<td>asdf</td>\n<td>qwer</td>\n</tr>\n"
                , MarkdownParser.parseTableDetails("||asdf|qwer|", 3));
        assertEquals("<tr>\n<td>asdf</td>\n<td>qwer</td>\n</tr>\n"
                , MarkdownParser.parseTableDetails("|asdf|qwer||||", 3));
        assertEquals("<tr>\n<td></td>\n<td></td>\n<td>asdf</td>\n</tr>\n"
                , MarkdownParser.parseTableDetails("|||asdf|qwer||", 3));
    }

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
                MarkdownParser.parseLoop("|asdf|asdf|asdf|\n" +
                        "---|---|---\n" +
                        "asdf|asdf|asdf\n" +
                        "asdf|asdf|asdf"));
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
                MarkdownParser.parseLoop("|asdf|asdf|asdf|\n" +
                        "---|---|---\n" +
                        "asdf|asdf|asdf\n" +
                        "asdf|asdf"));
        assertEquals("|asdf|asdf|asdf|\n" +
                        "---|---\n" +
                        "asdf|asdf|asdf\n" +
                        "asdf|asdf|asdf\n",
                MarkdownParser.parseLoop("|asdf|asdf|asdf|\n" +
                        "---|---\n" +
                        "asdf|asdf|asdf\n" +
                        "asdf|asdf|asdf"));
        assertEquals("|asdf|asdf|\n" +
                "---|---|---\n" +
                "asdf|asdf|asdf\n" +
                "asdf|asdf|asdf\n",
                MarkdownParser.parseLoop("|asdf|asdf|\n" +
                        "---|---|---\n" +
                        "asdf|asdf|asdf\n" +
                        "asdf|asdf|asdf"));
        assertEquals("|asdf|asdf|asdf|\n" +
                        "---||---\n" +
                        "asdf|asdf|asdf\n" +
                        "asdf|asdf|asdf\n",
                MarkdownParser.parseLoop("|asdf|asdf|asdf|\n" +
                        "---||---\n" +
                        "asdf|asdf|asdf\n" +
                        "asdf|asdf|asdf"));
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
                MarkdownParser.parseLoop("|asdf|asdf|\n" +
                        "---|---|\n" +
                        "asdf|asdf|asdf\n" +
                        "asdf|asdf|asdf"));
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
                        "\n" +
                        "asdfasdfasdf\n",
                MarkdownParser.parseLoop("|asdf|asdf|asdf|\n" +
                        "---|---|---\n" +
                        "asdf|asdf|asdf\n" +
                        "asdf|asdf|asdf\n\n" +
                        "asdfasdfasdf"));
    }

    @Test
    void testParseTableBranching() {
        assertEquals("dummy!\n" +
                        "---\n",
                MarkdownParser.parseLoop("dummy!\n---"));
        assertEquals("dummy!\n" +
                        "|---|\n",
                MarkdownParser.parseLoop("dummy!\n|---|"));
        assertEquals("<table>\n" +
                        "<thead>\n" +
                        "<tr>\n" +
                        "<th>dummy!</th>\n" +
                        "</tr>\n" +
                        "</thead>\n" +
                        "<tbody>\n" +
                        "</tbody>\n" +
                        "</table>\n",
                MarkdownParser.parseLoop("|dummy!|\n|---|"));
    }

    @Test
    void testParseCheckBox() {
        assertEquals("- [ ]", MarkdownParser.parseCheckBox("- [ ]"));
        assertEquals("- [x]", MarkdownParser.parseCheckBox("- [x]"));
        assertEquals("- [ ]asdf", MarkdownParser.parseCheckBox("- [ ]asdf"));
        assertEquals("- [x]asdf", MarkdownParser.parseCheckBox("- [x]asdf"));
        assertEquals("<input type=\"checkbox\" disabled>asdf", MarkdownParser.parseCheckBox("- [ ] asdf"));
        assertEquals("<input type=\"checkbox\" checked disabled>asdf", MarkdownParser.parseCheckBox("- [x] asdf"));
        assertEquals("asdf - [ ] asdf", MarkdownParser.parseCheckBox("asdf - [ ] asdf"));
        assertEquals("asdf - [x] asdf", MarkdownParser.parseCheckBox("asdf - [x] asdf"));
        assertEquals("   - [ ]", MarkdownParser.parseCheckBox("   - [ ]"));
        assertEquals("   - [x]", MarkdownParser.parseCheckBox("   - [x]"));
        assertEquals("   - [ ]asdf", MarkdownParser.parseCheckBox("   - [ ]asdf"));
        assertEquals("   - [x]asdf", MarkdownParser.parseCheckBox("   - [x]asdf"));
        assertEquals("   - [ ] asdf", MarkdownParser.parseCheckBox("   - [ ] asdf"));
        assertEquals("   - [x] asdf", MarkdownParser.parseCheckBox("   - [x] asdf"));
    }
}