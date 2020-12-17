package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.awt.event.KeyEvent;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_SEMICOLON;

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
    }

}
