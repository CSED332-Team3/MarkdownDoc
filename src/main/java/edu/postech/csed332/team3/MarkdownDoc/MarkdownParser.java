package edu.postech.csed332.team3.MarkdownDoc;

import edu.postech.csed332.team3.MarkdownDoc.mdcomponents.MarkdownString;
import edu.postech.csed332.team3.MarkdownDoc.mdcomponents.PlainText;
import edu.postech.csed332.team3.MarkdownDoc.mdcomponents.Underline;

import java.util.ArrayDeque;
import java.util.Deque;

public class MarkdownParser {
    private static final Deque<Character> stack = new ArrayDeque<>();

    private MarkdownParser() {
    }

    public static MarkdownString parse(String comment) {
        if (!isMarkdown(comment))
            throw new IllegalArgumentException("Not a md comment.");
        comment.chars().forEach(value -> {
            stack.pop();
            stack.pop();
        });
        return new Underline(PlainText.of("hi"));
    }

    public static boolean isMarkdown(String comment) {
        try {
            return comment.subSequence(0, 7).equals("!!mdDoc");
        } catch (Exception e) {
            return false;
        }
    }
}
