package edu.postech.csed332.team3.MarkdownDoc.SearchProjectTest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import edu.postech.csed332.team3.MarkdownDoc.SearchProject.ManageComment;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
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
    public void testAllJavadocExtractor() throws IOException {
        String path = new File("").getAbsolutePath();
        File file = new File(path + "/src/test/java/edu/postech/csed332/team3/MarkdownDoc/SearchProjectTest/example.java");
        JsonArray jsonArray = manageComment.AllJavadocExtractor(file);
        assertEquals(jsonArray.get(0).getAsJsonObject().get("Class: example").getAsString(), "/**\n * Hello, this is example class!\n */\n");
        assertEquals(jsonArray.get(1).getAsJsonObject().get("Field: a").getAsString(), "");
        assertEquals(jsonArray.get(2).getAsJsonObject().get("Field: b").getAsString(), "");
        assertEquals(jsonArray.get(3).getAsJsonObject().get("Field: c").getAsString(), "/**\n * This is c\n */\n");
        assertEquals(jsonArray.get(4).getAsJsonObject().get("Method: public void example_method()").getAsString(), "/**\n * Hello, this is example method!\n */\n");
        assertEquals(jsonArray.get(5).getAsJsonObject().get("Class: example2").getAsString(), "/**\n * Hello, this is example class2!\n */\n");
        assertEquals(jsonArray.get(6).getAsJsonObject().get("Method: public void example_method2(int a)").getAsString(), "/**\n * Hello, this is example method2!\n * @param a is example param\n */\n");
    }


}
