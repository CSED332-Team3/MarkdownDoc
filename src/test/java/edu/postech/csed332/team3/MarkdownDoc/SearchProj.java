package edu.postech.csed332.team3.MarkdownDoc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import edu.postech.csed332.team3.MarkdownDoc.SearchProject;
import java.io.File;
import java.io.IOException;

public class SearchProjTest {
    ProjectSearcher projectSearcher = new ProjectSearcher();

    @Test
    public void testIsDocument() {
        File file = new File("./example.md");
        boolean result = file.createNewFile();
        assertTrue(isDocument(file));
        file.delete();
    }

    @Test
    public void testIsJavaFile() {
        File file = new File("./example.java");
        boolean result = file.createNewFile();
        assertTrue(isJavaFile(file));
        file.delete();
    }

    @Test
    public void testIsJavaDoc() {
        File file = new File("./example.html");
        boolean result = file.createNewFile();
        assertTrue(isJavaDoc(file));
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
