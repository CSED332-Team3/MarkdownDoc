package edu.postech.csed332.team3.MarkdownDoc.SearchProjectTest;

import com.google.gson.JsonElement;
import edu.postech.csed332.team3.MarkdownDoc.SearchProject.ManageComment;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class ManageCommentTest {
    ManageComment manageComment = new ManageComment();

    @Test
    public void testIsDocument() throws IOException {
        File file = new File("example.md");
        System.out.print(file.getAbsolutePath());
        boolean result = file.createNewFile();
        assertTrue(manageComment.isDocument(file.getName()));
        file.delete();
    }


    @Test
    public void testIsJavaFile() throws IOException {
        File file = new File("example_.java");
        boolean result = file.createNewFile();
        assertTrue(manageComment.isJavaFile(file.getName()));
        file.delete();
    }

    @Test
    public void testIsJavaDoc() throws IOException {
        File file = new File("example.html");
        boolean result = file.createNewFile();
        assertTrue(manageComment.isJavaDoc(file.getName()));
        file.delete();
    }

    @Test
    public void testAllJavadocExtractor() throws IOException {
        String path = new File("").getAbsolutePath();
        File file = new File(path + "/src/test/java/edu/postech/csed332/team3/MarkdownDoc/SearchProjectTest/example.java");
        for (JsonElement jsonElement : manageComment.AllJavadocExtractor(file)) {
            String str = jsonElement.toString();
            System.out.println(str);
        }
    }


}
