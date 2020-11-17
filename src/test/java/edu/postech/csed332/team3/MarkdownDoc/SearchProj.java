package edu.postech.csed332.team3.MarkdownDoc;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;

public class SearchProjTest {
    ProjectSearcher projectSearcher = new ProjectSearcher();

    @Test
    public void testIsDocument (){
        File file = new File("./example.md");
        boolean result = file.createNewFile();
        assertTrue(isDocument(file));
        file.delete();
    }

    public void testIsJavaFile (){
        File file = new File("./example.java");
        boolean result = file.createNewFile();
        assertTrue(isJavaFile(file));
        file.delete();
    }

    public void testIsJavaDoc (){
        File file = new File("./example.html");
        boolean result = file.createNewFile();
        assertTrue(isJavaDoc(file));
        file.delete();
    }

    public boolean isCreated (){
        return true;
    }

    public boolean isDeleted (){
        return true;
    }

    public boolean isModified (){
        return true;
    }

    public void ModifyDocument (){
        return null;
    }
}
