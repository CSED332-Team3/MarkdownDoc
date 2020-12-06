package edu.postech.csed332.team3.markdowndoc.explorer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileManagerStubTest {
    private FileManagerStub fileManagerStub;

    @BeforeEach
    void init() {
        fileManagerStub = new FileManagerStub("path");
    }

    @Test
    void testWrite() {
        assertEquals("<p id=\"c\">\n" +
                "test\n" +
                "<p>test doc</p>\n" +
                "</p>\n", fileManagerStub.write("class", "test", "test doc"));
        assertEquals("<p id=\"c\">\n" +
                "test\n" +
                "<p>test doc</p>\n" +
                "</p>\n" +
                "<p>\n" +
                "test\n" +
                "<p>test doc</p>\n" +
                "</p>\n", fileManagerStub.write("", "test", "test doc"));
        assertEquals("<p id=\"c\">\n" +
                "test\n" +
                "<p>test doc</p>\n" +
                "</p>\n" +
                "<p>\n" +
                "test\n" +
                "<p>test doc</p>\n" +
                "</p>\n" +
                "<p id=\"c\">\n" +
                "\n" +
                "<p>test doc</p>\n" +
                "</p>\n", fileManagerStub.write("class", "", "test doc"));
        assertEquals("<p id=\"c\">\n" +
                "test\n" +
                "<p>test doc</p>\n" +
                "</p>\n" +
                "<p>\n" +
                "test\n" +
                "<p>test doc</p>\n" +
                "</p>\n" +
                "<p id=\"c\">\n" +
                "\n" +
                "<p>test doc</p>\n" +
                "</p>\n" +
                "<p id=\"c\">\n" +
                "test\n" +
                "</p>\n", fileManagerStub.write("class", "test", null));
    }

    @Test
    void testClose() {
        fileManagerStub.write("class", "test1", "test1-doc");
        fileManagerStub.write("method", "test2", "test2-doc");
        fileManagerStub.write("method", "test1", "test1-doc");
        fileManagerStub.write("field", "test3", "test3-doc");
        fileManagerStub.write("class", "test5", "test5-doc");

        assertEquals("<p id=\"c\">\n" +
                "test1\n" +
                "<p>test1-doc</p>\n" +
                "</p>\n" +
                "<p id=\"m\">\n" +
                "test2\n" +
                "<p>test2-doc</p>\n" +
                "</p>\n" +
                "<p id=\"m\">\n" +
                "test1\n" +
                "<p>test1-doc</p>\n" +
                "</p>\n" +
                "<p id=\"f\">\n" +
                "test3\n" +
                "<p>test3-doc</p>\n" +
                "</p>\n" +
                "<p id=\"c\">\n" +
                "test5\n" +
                "<p>test5-doc</p>\n" +
                "</p>\n", fileManagerStub.close());
        assertEquals("", fileManagerStub.close());
    }

    @Test
    void testAppendComment() {
        assertEquals("<p>this is test comment</p>\n", fileManagerStub.appendComment("this is test comment"));
        assertEquals("<p>*this is test comment</p>\n", fileManagerStub.appendComment("\\*this is test comment"));
        assertEquals("<p>this is\n*test comment</p>\n", fileManagerStub.appendComment("this is\n *test comment"));
        assertEquals("<p>this is test comment*\\</p>\n", fileManagerStub.appendComment("this is test comment*\\"));
        assertEquals("", fileManagerStub.appendComment(null));
    }

    @Test
    void testAppendId() {
        assertEquals("<p id=\"c\">\n", fileManagerStub.appendId("class"));
        assertEquals("<p id=\"m\">\n", fileManagerStub.appendId("method"));
        assertEquals("<p id=\"f\">\n", fileManagerStub.appendId("field"));
        assertEquals("<p>\n", fileManagerStub.appendId("interface"));
    }
}