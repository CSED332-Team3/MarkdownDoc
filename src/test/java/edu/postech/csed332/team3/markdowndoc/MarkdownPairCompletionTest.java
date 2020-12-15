package edu.postech.csed332.team3.markdowndoc;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class MarkdownPairCompletionTest extends BasePlatformTestCase {
    @Override
    protected String getTestDataPath() {
        return "testData";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myFixture.configureByFile("pairCompletionTest/PairCompletionTestOriginal.java");
    }

    public void testBoldCompletion() {
        myFixture.type("**\t");
        myFixture.checkResultByFile("pairCompletionTest/PairCompletionBoldTest.java");
        assertEquals(0, myFixture.completeBasic().length);
    }

    public void testBold2Completion() {
        myFixture.type("__\t");
        myFixture.checkResultByFile("pairCompletionTest/PairCompletionBold2Test.java");
        assertEquals(11, myFixture.completeBasic().length);
    }
    public void testHeaderLineCompletion() {
        myFixture.type("-\t");
        myFixture.checkResultByFile("pairCompletionTest/PairCompletionHeaderLineTest.java");
        assertEquals(0, myFixture.completeBasic().length);
    }
    public void testHeaderLine2Completion() {
        myFixture.type("=\t");
        myFixture.checkResultByFile("pairCompletionTest/PairCompletionHeaderLine2Test.java");
        assertEquals(0, myFixture.completeBasic().length);
    }
    public void testItalicCompletion() {
        myFixture.type("*\t");
        myFixture.checkResultByFile("pairCompletionTest/PairCompletionItalicTest.java");
        assertEquals(0, myFixture.completeBasic().length);
    }
    public void testItalic2Completion() {
        myFixture.type("_\t");
        myFixture.checkResultByFile("pairCompletionTest/PairCompletionItalic2Test.java");
        assertEquals(11, myFixture.completeBasic().length);
    }
}