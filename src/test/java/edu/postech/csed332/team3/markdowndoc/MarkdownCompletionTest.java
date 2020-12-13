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
    }

    public void testBoldCompletion() throws Exception {
        setUp();
        myFixture.configureByFile("AutoPopUpCompletionTest.java");
        myFixture.type('&');
        myFixture.type('B');
        myFixture.type('o');
        myFixture.type('l');
        myFixture.type('d');
        myFixture.type('\t');
        myFixture.checkResultByFile("AutoPopUpCompletionTest2.java", false);
    }
}