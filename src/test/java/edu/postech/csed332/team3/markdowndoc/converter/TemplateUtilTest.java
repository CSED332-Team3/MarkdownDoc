package edu.postech.csed332.team3.markdowndoc.converter;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.io.IOException;

public class TemplateUtilTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testData";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("templateUtilTest/TestTemplateUtil.java");
        myFixture.copyFileToProject("templateUtilTest/ExtendsTest.java");
        myFixture.copyFileToProject("templateUtilTest/ImplementsTest.java");
    }

    public void testHeader() {
        try {
            assertEquals("<!DOCTYPE html><html><head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <title>MarkdownDoc</title> <style>html{font-family: Arial, Helvetica, sans-serif; font-size: 16px; padding: 1rem; overflow-wrap: break-word;}img{max-width: 100%;}h1{margin-top: 0.5rem;}h1 + div{margin-top: -0.5rem;}h2{font-style: italic; color: #555555; margin-top: 2rem;}h3{font-family:'Courier New', Courier, monospace;}h5{margin-bottom: -1rem; color: #777777;}.pkg{color: #777777; margin-top: 1rem;}table{border-collapse: collapse; margin: 1rem 0; width: 100%;}td{border: 1px solid #aaaaaa; border-width: 1px 0; padding: 0.25rem 1rem;}td table td{border-width: 1px;}p{line-height: 1.6;}.alert{color: #FF3333;}a{text-decoration: none; font-weight: 500; color: #0000FF;}a:hover{text-decoration: underline; cursor: pointer;}.disabled{text-decoration: none !important; cursor: default !important; color: #555555 !important; pointer-events: none !important;}.all{margin-left: 1rem; line-height: 1.2; font-family: 'Courier New', Courier, monospace;}.credits{text-align: right; margin-top: 2rem; color: #555555;}</style></head><body>\n",
                    TemplateUtil.header());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testFooter() {
        try {
            assertEquals("<div class=\"credits\"> Generated with MarkdownDoc </div><script type=\"text/javascript\">document.addEventListener(\"DOMContentLoaded\", function(){var links=document.getElementsByTagName('a'); for (var i=0; i < links.length; i++){links[i].addEventListener(\"click\", function(){var id=this.getAttribute('id'); var qn=document.getElementById('pkg').innerText + \".\" + document.getElementById('class').innerText; if (id){console.log(id + \";\" + qn);}}); var classId=links[i].getAttribute('id'); if (classId !=null && classId.startsWith(\"c\")){if (links[i].getAttribute('href')==\"\"){links[i].classList.add(\"disabled\");}}}for (var i=0; i < links.length; i++){var id=links[i].getAttribute('id'); if (id !=null && id.startsWith(\"m\")){for (var j=i.valueOf(), n=1; j < links.length; j++){var mid=links[j].getAttribute('id'); if (mid.includes(\"/\")){continue;}if (mid.localeCompare(id)==0){links[j].id +=\"/\" + n; n++;}}}}}); function sortTable(method){var table, rows, switching, i, x, y, shouldSwitch, dir; table=document.getElementById('table'); switching=true; while (switching){switching=false; rows=table.rows; for (i=2; i < rows.length - 1; i++){shouldSwitch=false; x=rows[i].getElementsByTagName(\"td\")[0]; y=rows[i + 1].getElementsByTagName(\"td\")[0]; if (x.getAttribute(\"data-type\") !=method){if (y.getAttribute(\"data-type\")==method){shouldSwitch=true; break;}}}if (shouldSwitch){rows[i].parentNode.insertBefore(rows[i + 1], rows[i]); switching=true;}}}</script></body></html>\n",
                    TemplateUtil.footer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testAppendLast() {
        assertEquals("</table>",
                TemplateUtil.appendLast());
    }

    private PsiClass getPsiClass() {
        Project project = getProject();
        return JavaPsiFacade.getInstance(project).findClass("templateUtilTest.TestTemplateUtil", GlobalSearchScope.allScope(project));
    }

    public void testAppendFirst() {
        PsiClass aClass = getPsiClass();
        assertNotNull(aClass);

        assertEquals("<div id=\"pkg\" class=\"pkg\">templateUtilTest</div>\n" +
                        "<h1 id=\"class\">TestTemplateUtil</h1><div><strong>extends</strong> <a id=\"c-templateUtilTest.ExtendsTest\" href=\"\">ExtendsTest</a></div><div><strong>implements</strong> <a id=\"c-templateUtilTest.ImplementsTest\" href=\"\">ImplementsTest</a></div>\n" +
                        "<h2>Description</h2>\n" +
                        "<table id = \"table\">\n" +
                        "<tr><td></td></tr><tr><td></td></tr>",
                TemplateUtil.appendFirst(aClass));
    }

    public void testAppend() {
        // class
        PsiClass aClass = getPsiClass();
        assertEquals("<tr><td data-type=\"\"><h5>Class</h5><h3><a id=\"c-TestTemplateUtil\">\n" +
                        "public  TestTemplateUtil</a></h3></td></tr>\n",
                TemplateUtil.append(aClass));

        // method
        PsiMethod[] methods = aClass.getMethods();
        assertEquals("<tr><td data-type=\"void\"><h5>Method</h5><h3><a id=\"m-test\">\n" +
                        "protected void test</a></h3></td></tr>\n",
                TemplateUtil.append(methods[0]));
        assertEquals("<tr><td data-type=\"char\"><h5>Method</h5><h3><a id=\"m-method\">\n" +
                        "public static char method</a></h3><p>Test Doc Comment</p>\n" +
                        "<p><strong class=\"alert\">@return</strong> test return tag comment <br></p></td></tr>\n",
                TemplateUtil.append(methods[1]));

        // field
        PsiField[] fields = aClass.getFields();
        assertEquals("<tr><td data-type=\"int\"><h5>Field</h5><h3><a id=\"f-a\">\n" +
                        "int a</a></h3></td></tr>\n",
                TemplateUtil.append(fields[0]));
        assertEquals("<tr><td data-type=\"boolean\"><h5>Field</h5><h3><a id=\"f-b\">\n" +
                        "boolean b</a></h3></td></tr>\n",
                TemplateUtil.append(fields[1]));
    }

    public void testAppendEscaping() {
        PsiClass aClass = getPsiClass();
        assertEquals("<tr><td data-type=\"List&lt;int&gt;\"><h5>Method</h5><h3><a id=\"m-escape\">\n" +
                        "private List&lt;int&gt; escape</a></h3><p><strong class=\"alert\">@param</strong> c test parameter tag comment <br></p></td></tr>\n",
                TemplateUtil.append(aClass.getMethods()[2]));

        assertEquals("<tr><td data-type=\"List&lt;char&gt;\"><h5>Field</h5><h3><a id=\"f-c\">\n" +
                        "List&lt;char&gt; c</a></h3></td></tr>\n",
                TemplateUtil.append(aClass.getFields()[2]));
    }
}