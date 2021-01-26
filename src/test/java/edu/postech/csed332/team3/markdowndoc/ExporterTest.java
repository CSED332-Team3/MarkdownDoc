package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.util.io.FileUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExporterTest {
    private static final String MDDOC = "mddoc";

    @BeforeClass
    public static void beforeClass() throws Exception {
        FileUtil.delete(Path.of(MDDOC));
    }

    @Test
    public void testExport() {
        File file = new File(MDDOC + "/testExport/Test.html");
        assertTrue(file.getParentFile().mkdirs());
        try {
            assertTrue(file.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Exporter.export("MarkdownDoc", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<String> zipSet = new HashSet<>();

        try {
            ZipFile zipFile = new ZipFile("MarkdownDoc.zip");
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                if (!zipEntry.isDirectory())
                    zipSet.add(zipEntry.getName());
            }
            zipFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(Set.of(MDDOC + "/testExport/Test.html"), zipSet);

        assertTrue(file.delete());
        assertTrue(file.getParentFile().delete());
        file = new File("MarkdownDoc.zip");
        assertTrue(file.delete());
    }

    @Test(expected = FileNotFoundException.class)
    public void testExportThrow() throws Exception {
        File file = new File(MDDOC);
        file.delete();
        Exporter.export("MarkdownDoc", null);
    }
}
