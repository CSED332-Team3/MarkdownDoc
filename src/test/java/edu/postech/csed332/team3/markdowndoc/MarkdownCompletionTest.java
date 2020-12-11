package edu.postech.csed332.team3.markdowndoc;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MarkdownCompletionTest extends BasePlatformTestCase {
    @Override
    protected String getTestDataPath() {
        return "testData";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
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