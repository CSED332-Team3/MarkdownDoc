package edu.postech.csed332.team3.markdowndoc.converter;

import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TemplateUtilStubTest {

    @Test
    void appendFirstTest() {
        assertThrows(InvalidParameterException.class,
                () -> TemplateUtilStub.appendFirst(null, "test", "", null),
                "Class name is null");
        assertEquals("pkg<h1>class</h1><div><strong>extends</strong> <a id=\"c-ext\">ext</a></div><div><strong>implements</strong> <a id=\"c-impl1\">impl1</a></div><div><strong>implements</strong> <a id=\"c-impl2\">impl2</a></div></div>",
                TemplateUtilStub.appendFirst("class", "pkg", "ext", List.of("impl1", "impl2")));
    }

    @Test
    void appendTest() {
        assertEquals("<tr><td data-type=\"\"><h5>Class</h5><h3><a id=\"c-class\">\n" +
                        " class</a></h3>class for test</td></tr>\n",
                TemplateUtilStub.append("PsiClass", "class", null, "class for test"));
        assertEquals("<tr><td data-type=\"void\"><h5>Method</h5><h3><a id=\"m-method1\">\n" +
                        "public void method1</a></h3>method1 for test</td></tr>\n",
                TemplateUtilStub.append("PsiMethod:void", "method1", "public", "method1 for test"));
        assertEquals("<tr><td data-type=\"String\"><h5>Method</h5><h3><a id=\"m-method2\">\n" +
                        "static String method2</a></h3>method1 for test</td></tr>\n",
                TemplateUtilStub.append("PsiMethod:String", "method2", "static", "method1 for test"));
        assertEquals("<tr><td data-type=\"boolean\"><h5>Method</h5><h3><a id=\"m-method3\">\n" +
                        "private static boolean method3</a></h3>method3 for test</td></tr>\n",
                TemplateUtilStub.append("PsiMethod:boolean", "method3", "private static", "method3 for test"));
        assertEquals("<tr><td data-type=\"int\"><h5>Field</h5><h3><a id=\"f-field1\">\n" +
                        "int field1</a></h3>field1 for test</td></tr>\n",
                TemplateUtilStub.append("PsiField:int", "field1", null, "field1 for test"));
    }
}