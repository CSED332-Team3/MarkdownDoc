package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class ShortCutActionTest extends BasePlatformTestCase {
    @Override
    protected String getTestDataPath() {
        return "testData";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("shortCutTest/ShortCutActionTest.java");
    }

    public void testShortCutAction() {
        Presentation presentation = myFixture.testAction(new ShortCutAction());
        assertTrue(presentation.isVisible());
        //TODO: add more test operation
    }

}
