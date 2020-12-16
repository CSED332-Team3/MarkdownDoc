package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FileManagerTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testData";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testConstructor() {
        String path = "testData/fileManagerTest/file1.html";
        FileManager fileManager = new FileManager(path);
        File file = new File(path);
        assertTrue(file.exists());

        fileManager.close(null);
    }

    public void testClose() {
        String path = "testData/fileManagerTest/file2.html";
        FileManager fileManager = new FileManager(path);

        myFixture.copyFileToProject("fileManagerTest/Test.java");

        Project project = getProject();
        PsiClass aClass = JavaPsiFacade.getInstance(project).findClass("fileManagerTest.TestJavaFile", GlobalSearchScope.allScope(project));
        assertNotNull(aClass);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(project);
        MdDocElementVisitor visitor = new MdDocElementVisitor(root) {
            @Override
            public void visitClass(PsiClass aClass) {
                getAllClassesSet().clear();
                getAllClassesSet().add(aClass);
            }
        };
        visitor.visitClass(aClass);

        fileManager.writeFirst(aClass);
        Arrays.stream(aClass.getMethods()).forEach(fileManager::write);
        Arrays.stream(aClass.getFields()).forEach(fileManager::write);

        File file = new File(path);
        fileManager.close(aClass);
        try {
            assertEquals("<!DOCTYPE html><html><head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <title>MarkdownDoc</title> <style>html{font-family: Arial, Helvetica, sans-serif; font-size: 16px; padding: 1rem; overflow-wrap: break-word;}img{max-width: 100%;}h1{margin-top: 0.5rem;}h1 + div{margin-top: -0.5rem;}h2{font-style: italic; color: #555555; margin-top: 2rem;}h3{font-family:'Courier New', Courier, monospace;}h5{margin-bottom: -1rem; color: #777777;}.pkg{color: #777777; margin-top: 1rem;}table{border-collapse: collapse; margin: 1rem 0; width: 100%;}td{border: 1px solid #aaaaaa; border-width: 1px 0; padding: 0.25rem 1rem;}td table td{border-width: 1px;}p{line-height: 1.6;}.alert{color: #FF3333;}a{text-decoration: none; font-weight: 500; color: #0000FF;}a:hover{text-decoration: underline; cursor: pointer;}.disabled{text-decoration: none !important; cursor: default !important; color: #555555 !important; pointer-events: none !important;}.all{margin-left: 1rem; line-height: 1.2; font-family: 'Courier New', Courier, monospace;}.credits{text-align: right; margin-top: 2rem; color: #555555;}</style></head><body>\n" +
                            "<div id=\"pkg\" class=\"pkg\">fileManagerTest</div>\n" +
                            "<h1 id=\"class\">TestJavaFile</h1>\n" +
                            "<h2>Description</h2>\n" +
                            "<table id = \"table\">\n" +
                            "<tr><td></td></tr><tr><td></td></tr><tr><td data-type=\"\"><h5>Method</h5><h3><a id=\"m-TestJavaFile\">\n" +
                            "  TestJavaFile</a></h3></td></tr>\n" +
                            "<tr><td data-type=\"List&lt;String&gt;\"><h5>Method</h5><h3><a id=\"m-testMethod\">\n" +
                            "public List&lt;String&gt; testMethod</a></h3></td></tr>\n" +
                            "<tr><td data-type=\"List&lt;String&gt;\"><h5>Field</h5><h3><a id=\"f-testField\">\n" +
                            "List&lt;String&gt; testField</a></h3></td></tr>\n" +
                            "</table><h2>All classes</h2><div class=\"all\"><a id=\"c-fileManagerTest.TestJavaFile\" href=\"../fileManagerTest/TestJavaFile.html\">TestJavaFile</a><br></div><div class=\"credits\"> Generated with MarkdownDoc </div><script type=\"text/javascript\">document.addEventListener(\"DOMContentLoaded\", function(){var links=document.getElementsByTagName('a'); for (var i=0; i < links.length; i++){links[i].addEventListener(\"click\", function(){var id=this.getAttribute('id'); var qn=document.getElementById('pkg').innerText + \".\" + document.getElementById('class').innerText; if (id){console.log(id + \";\" + qn);}}); var classId=links[i].getAttribute('id'); if (classId !=null && classId.startsWith(\"c\")){if (links[i].getAttribute('href')==\"\"){links[i].classList.add(\"disabled\");}}}for (var i=0; i < links.length; i++){var id=links[i].getAttribute('id'); if (id !=null && id.startsWith(\"m\")){for (var j=i.valueOf(), n=1; j < links.length; j++){var mid=links[j].getAttribute('id'); if (mid.includes(\"/\")){continue;}if (mid.localeCompare(id)==0){links[j].id +=\"/\" + n; n++;}}}}}); function sortTable(method){var table, rows, switching, i, x, y, shouldSwitch, dir; table=document.getElementById('table'); switching=true; while (switching){switching=false; rows=table.rows; for (i=2; i < rows.length - 1; i++){shouldSwitch=false; x=rows[i].getElementsByTagName(\"td\")[0]; y=rows[i + 1].getElementsByTagName(\"td\")[0]; if (x.getAttribute(\"data-type\") !=method){if (y.getAttribute(\"data-type\")==method){shouldSwitch=true; break;}}}if (shouldSwitch){rows[i].parentNode.insertBefore(rows[i + 1], rows[i]); switching=true;}}}</script></body></html>\n",
                    FileUtil.loadFile(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}