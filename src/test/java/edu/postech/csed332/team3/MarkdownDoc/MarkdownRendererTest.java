package edu.postech.csed332.team3.MarkdownDoc;

import edu.postech.csed332.team3.MarkdownDoc.mdcomponents.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MarkdownRendererTest{

    @Test
    public void testIsMarkdown() {
        assertTrue(MarkdownParser.isMarkdown("!!mdDoc"));
    }

    @Test
    public void testIsHeader() {
        assertTrue(MarkdownParser.parse("# header1") instanceof Header);
        assertTrue(MarkdownParser.parse("## header2") instanceof Header);
        assertTrue(MarkdownParser.parse("### header3") instanceof Header);
        assertTrue(MarkdownParser.parse("#### header4") instanceof Header);
        assertTrue(MarkdownParser.parse("##### header5") instanceof Header);
        assertTrue(MarkdownParser.parse("###### header6") instanceof Header);
    }

    @Test
    public void testIsList() {
        assertTrue(MarkdownParser.parse("* bold") instanceof List);
    }

    @Test
    public void testIsImage() {
        assertTrue(MarkdownParser.parse("![Image]()") instanceof Image);
        assertTrue(MarkdownParser.parse("<a href=\"#\"><img src=\"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png\"></a> ") instanceof Image);
    }

    @Test
    public void testIsLink() {
        assertTrue(MarkdownParser.parse("[MarkdownDoc](https://csed332.postech.ac.kr/team3-2020/MarkdownDoc)") instanceof Link);
    }

    @Test
    public void testIsCodeBlock() {
        assertTrue(MarkdownParser.parse("```javascript\nfunction test() {\n console.log(\"hello world!\");\n}\n```") instanceof CodeBlock);
    }

    @Test
    public void testIsBlockquote() {
        assertTrue(MarkdownParser.parse("> MarkdownDoc") instanceof Blockquote);
    }

    @Test
    public void testIsTable() {
        assertTrue(MarkdownParser.parse("|a|b|c|\n|-|-|-|\n|d|e|f|") instanceof Table);
    }

    @Test
    public void testIsTaskList() {
        assertTrue(MarkdownParser.parse("- [ ] Test task list") instanceof TaskList);
    }

    @Test
    public void testIsInlineCode() {
        assertTrue(MarkdownParser.parse("`printf(\"Hello world!\")`") instanceof InlineCode);
    }

    @Test
    public void testIsHorizontal() {
        assertTrue(MarkdownParser.parse("---") instanceof Horizontal);
        assertTrue(MarkdownParser.parse("***") instanceof Horizontal);
        assertTrue(MarkdownParser.parse("___") instanceof Horizontal);
    }

    @Test
    public void testIsEmoji() {
        assertTrue(MarkdownParser.parse(":+1:") instanceof Emoji);
    }

    @Test
    public void testIsBadge() {
        assertTrue(MarkdownParser.parse("<https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png>") instanceof Badge);
    }

    @Test
    public void testIsBold() {
        assertTrue(MarkdownParser.parse("**bold**") instanceof Bold);
        assertTrue(MarkdownParser.parse("__bold__") instanceof Bold);
    }

    @Test
    public void testIsItalic() {
        assertTrue(MarkdownParser.parse("*italic*") instanceof Italic);
        assertTrue(MarkdownParser.parse("_italic_") instanceof Italic);
    }

    @Test
    public void testIsStrikeThrough() {
        assertTrue(MarkdownParser.parse("~~StrikeThrough~~") instanceof Strikethrough);
    }

    @Test
    public void testIsUnderline() {
        assertTrue(MarkdownParser.parse("<u>underline</u>") instanceof Underline);
    }
}
