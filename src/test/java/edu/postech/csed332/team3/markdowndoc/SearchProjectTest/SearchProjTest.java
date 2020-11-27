package edu.postech.csed332.team3.markdowndoc.SearchProjectTest;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

import edu.postech.csed332.team3.markdowndoc.SearchProject.SearchProject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SearchProjTest {
    @Test
    public void testSearchCreatedFile() throws IOException, InterruptedException {
        SearchProject searchProject = new SearchProject();
        searchProject.init(".");
        searchProject.start();

        Thread.sleep(10000);

        String pth = "./src/main/java/created.java";
        File file = new File(pth);
        file.createNewFile();

        Thread.sleep(10000);

        assertTrue(new File("./mdsaved/main/java/created.md").exists());
        file.delete();
        FileUtils.deleteDirectory(new File("./mdsaved"));
    }

    @Test
    public void testSearchModifiedFile() throws IOException, InterruptedException {
        SearchProject searchProject = new SearchProject();
        searchProject.init(".");
        searchProject.start();

        Thread.sleep(10000);

        String pth = "./src/main/java/created.java";
        File file = new File(pth);
        file.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("/**\n * Hello\n */\npublic class created {\n  int a;\n}");
        writer.close();

        Thread.sleep(10000);
        assertTrue(new File("./mdsaved/main/java/created.md").exists());
        file.delete();
        FileUtils.deleteDirectory(new File("./mdsaved"));
    }

    @Test
    public void testSearchDeletedFile() throws IOException, InterruptedException {
        SearchProject searchProject = new SearchProject();
        searchProject.init(".");
        searchProject.start();

        Thread.sleep(10000);

        String pth = "./src/main/java/created.java";
        File file = new File(pth);
        file.createNewFile();

        Thread.sleep(10000);

        file.delete();

        Thread.sleep(10000);
        assertTrue(!new File("./mdsaved/main/java/created.md").exists());
        FileUtils.deleteDirectory(new File("./mdsaved"));
    }

    @Test
    public void testInit() throws IOException, InterruptedException {
        SearchProject searchProject = new SearchProject();
        searchProject.init(".");
        String pth = "./mdsaved/main";
        File file = new File(pth);
        assertTrue(file.exists());

        FileUtils.deleteDirectory(new File("./mdsaved"));
    }
}
