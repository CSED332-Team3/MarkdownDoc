package edu.postech.csed332.team3.MarkdownDoc;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import edu.postech.csed332.team3.MarkdownDoc.SearchProject.ManageComment;
import edu.postech.csed332.team3.MarkdownDoc.SearchProject.SearchProject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ManageCommentTest{
    ManageComment manageComment = new ManageComment();

    @Test
    public void testIsDocument() {
        File file = new File("./example.md");
        boolean result = file.createNewFile();
        assertTrue(manageComment.isDocument(file.getName()));
        file.delete();
    }


    @Test
    public void testIsJavaFile() {
        File file = new File("./example.java");
        boolean result = file.createNewFile();
        assertTrue(manageComment.isJavaFile(file.getName()));
        file.delete();
    }

    @Test
    public void testIsJavaDoc() {
        File file = new File("./example.html");
        boolean result = file.createNewFile();
        assertTrue(manageComment.isJavaDoc(file.getName()));
        file.delete();
    }

    @Test
    public void testModifiyDocument() {
        File file = new File("./example.html");
        boolean result = file.createNewFile();
        File markdown = new File("./example.md");
        boolean result2 = markdown.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("Hello");
        writer.close();
        String strings = FileUtils.readFileToString(markdown, "UTF-8");

        assertTrue("Hello", strings);
        file.delete();
        markdown.delete();
    }

}
public class SearchProjTest {
    ProjectSearcher projectSearcher = new ProjectSearcher();


}
