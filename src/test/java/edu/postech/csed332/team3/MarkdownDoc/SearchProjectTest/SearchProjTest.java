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

public class SearchProjTest {
    SearchProject searchProject = new SearchProject();
    @Test
    public void testSearchCreatedFile() throws IOException, InterruptedException {
        searchProject.Search();
    }

}
