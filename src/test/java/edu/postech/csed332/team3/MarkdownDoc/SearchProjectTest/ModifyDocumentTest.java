package edu.postech.csed332.team3.MarkdownDoc.SearchProjectTest;
import edu.postech.csed332.team3.MarkdownDoc.SearchProject.ModifyDocument;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ModifyDocumentTest {
    ModifyDocument modifyDocument = new ModifyDocument();

    @Test
    public void testModifyDocument() throws IOException {
        String pth = new File("").getAbsolutePath() + "/src/test/java/edu/postech/csed332/team3/MarkdownDoc/SearchProjectTest/example.java";
        File file = new File("./mdsaved/" + Path.of(pth).getFileName().toString().replace(".java", "") + ".md");
        assertTrue(file.createNewFile());
        assertTrue(modifyDocument.ModifyDocument(Path.of(pth), file));
        file.delete();
    }

    @Test
    public void testModifyDocumentNotJavaFile() throws IOException {
        Path pth = Path.of("./src/example.cpp");
        assertFalse(modifyDocument.ModifyDocument(pth, null));
    }
}
