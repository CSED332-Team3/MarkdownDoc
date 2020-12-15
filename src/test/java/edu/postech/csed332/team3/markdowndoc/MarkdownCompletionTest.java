package edu.postech.csed332.team3.markdowndoc;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class MarkdownCompletionTest extends BasePlatformTestCase {
    @Override
    protected String getTestDataPath() {
        return "testData";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myFixture.configureByFile("autoPopupTest/AutoPopUpCompletionTestOriginal.java");
    }

    public void testBoldCompletion() {
        System.out.println(getProject());
        //myFixture.type('/');
        myFixture.type("Bold\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionBoldTest.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testTableCompletion() {
        //myFixture.type('/');
        myFixture.type("Table\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionTableTest.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testBlockquotesCompletion() {
        //myFixture.type('/');
        myFixture.type("Blockquotes\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionBlockquotesTest.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testCodeCompletion() {
        //myFixture.type('/');
        myFixture.type("Code\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionCodeTest.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testHeading1Completion() {
        //myFixture.type('/');
        myFixture.type("Heading1\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionHeading1Test.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testHeading2Completion() {
       //myFixture.type('/');
        myFixture.type("Heading2\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionHeading2Test.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testHeading3Completion() {
        //myFixture.type('/');
        myFixture.type("Heading3\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionHeading3Test.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testHeading4Completion() {
        //myFixture.type('/');
        myFixture.type("Heading4\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionHeading4Test.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testHeading5Completion() {
        //myFixture.type('/');
        myFixture.type("Heading5\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionHeading5Test.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testHeading6Completion() {
        //myFixture.type('/');
        myFixture.type("Heading6\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionHeading6Test.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testHorizontalRuleCompletion() {
        //myFixture.type('/');
        myFixture.type("HorizontalRule\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionHorizontalRuleTest.java");
        assertEquals(9, myFixture.completeBasic().length);
    }

    public void testImageCompletion() {
        //myFixture.type('/');
        myFixture.type("Image\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionImageTest.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testItalicCompletion() {
        //myFixture.type('/');
        myFixture.type("Italic\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionItalicTest.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testLinkCompletion() {
        //myFixture.type('/');
        myFixture.type("Link\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionLinkTest.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testOrderedListCompletion() {
        //myFixture.type('/');
        myFixture.type("OrderedList\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionOrderedListTest.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testUnorderedListCompletion() {
        //myFixture.type('/');
        myFixture.type("UnorderedList\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionUnorderedListTest.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testURLCompletion() {
        //myFixture.type("/");
        myFixture.type("URL\t");
        myFixture.checkResultByFile("autoPopupTest/AutoPopUpCompletionURLTest.java");
        assertEquals(0, myFixture.completeBasic().length);
    }
}