package edu.postech.csed332.team3.MarkdownDoc.SearchProjectTest;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import edu.postech.csed332.team3.MarkdownDoc.SearchProject.ManageComment;
import edu.postech.csed332.team3.MarkdownDoc.SearchProject.SearchProject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class SearchProjTest {
    @Test
    public void testSearchCreatedFile() throws IOException, InterruptedException {
        SearchProject searchProject = new SearchProject();
        searchProject.start();

        Thread.sleep(10000);

        String pth = "./src/main/java/created.java";
        File file = new File(pth);
        file.createNewFile();

        Thread.sleep(10000);

        assertTrue(new File("./mdsaved/main/java/created.md").exists());
    }

    @Test
    public void testSearchModifiedFile() throws IOException, InterruptedException {
        SearchProject searchProject = new SearchProject();
        searchProject.start();

        Thread.sleep(10000);

        String pth = "./src/main/java/created.java";
        File file = new File(pth);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("/**\n * Hello\n */\npublic class created {\n  int a;\n}");
        writer.close();

        Thread.sleep(10000);
        assertTrue(new File("./mdsaved/main/java/created.md").exists());
    }

    @Test
    public void testSearchDeletedFile() throws IOException, InterruptedException {
        SearchProject searchProject = new SearchProject();
        searchProject.start();

        Thread.sleep(10000);

        String pth = "./src/main/java/created.java";
        File file = new File(pth);
        file.delete();

        Thread.sleep(10000);
        assertTrue(!new File("./mdsaved/main/java/created.md").exists());
    }

    @Test
    public void testInit() throws IOException, InterruptedException {
        SearchProject searchProject = new SearchProject();
        searchProject.init("./src");

        Thread.sleep(10000);

        String pth = "./mdsaved/main/java/edu/postech/csed332/team3/MarkdownDoc/SearchProject/ManageComment.md";
        File file = new File(pth);
        assertTrue(file.exists());
    }
}
