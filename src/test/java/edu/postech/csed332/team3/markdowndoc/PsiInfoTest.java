package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import static edu.postech.csed332.team3.markdowndoc.PsiInfo.*;

public class PsiInfoTest extends BasePlatformTestCase {

    private PsiClass psiClass;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("templateUtilTest/TestTemplateUtil.java");
        myFixture.copyFileToProject("templateUtilTest/ExtendsTest.java");
        myFixture.copyFileToProject("templateUtilTest/ImplementsTest.java");

        psiClass  = getPsiClass();
    }

    @Override
    protected String getTestDataPath() {
        return "testData";
    }

    private PsiClass getPsiClass() {
        Project project = getProject();
        return JavaPsiFacade.getInstance(project).findClass("templateUtilTest.TestTemplateUtil", GlobalSearchScope.allScope(project));
    }

    public void testGetId() {
        assertEquals("<a id=\"c-TestTemplateUtil\">\n", getId(psiClass));
        assertEquals("<a id=\"f-a\">\n", getId(psiClass.getFields()[0]));
        assertEquals("<a id=\"f-b\">\n", getId(psiClass.getFields()[1]));
        assertEquals("<a id=\"m-test\">\n", getId(psiClass.getMethods()[0]));
        assertEquals("<a id=\"m-method\">\n", getId(psiClass.getMethods()[1]));
    }

    public void testGetElementType() {
        assertEquals("Class", getElementType(psiClass));
        assertEquals("Field", getElementType(psiClass.getFields()[0]));
        assertEquals("Field", getElementType(psiClass.getFields()[1]));
        assertEquals("Method", getElementType(psiClass.getMethods()[0]));
        assertEquals("Method", getElementType(psiClass.getMethods()[1]));
    }

    public void testGetTagComment() {
        assertEquals("", getTagComment(psiClass.getDocComment()));
        assertEquals("", getTagComment(psiClass.getFields()[0].getDocComment()));
        assertEquals("", getTagComment(psiClass.getFields()[1].getDocComment()));
        assertEquals("", getTagComment(psiClass.getMethods()[0].getDocComment()));
        assertEquals("<p><strong class=\"alert\">@return</strong> test return tag comment <br></p>", getTagComment(psiClass.getMethods()[1].getDocComment()));
    }

    public void testGetComment() {
        assertEquals("", getComment(psiClass.getDocComment()));
        assertEquals("", getComment(psiClass.getFields()[0].getDocComment()));
        assertEquals("", getComment(psiClass.getFields()[1].getDocComment()));
        assertEquals("", getComment(psiClass.getMethods()[0].getDocComment()));
        assertEquals("<p>Test Doc Comment</p>\n", getComment(psiClass.getMethods()[1].getDocComment()));
    }

    public void testGetType() {
        assertEquals("", getType(psiClass));
        assertEquals("int", getType(psiClass.getFields()[0]));
        assertEquals("boolean", getType(psiClass.getFields()[1]));
        assertEquals("void", getType(psiClass.getMethods()[0]));
        assertEquals("char", getType(psiClass.getMethods()[1]));
    }
}
