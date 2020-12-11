package edu.postech.csed332.team3.markdowndoc;

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    public void testCheckAutoPopup() throws Exception {
        setUp();
        myFixture.configureByFile("AutoPopUpCompletionTest.java");
        myFixture.type('&');
        assertNotNull(myFixture);
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