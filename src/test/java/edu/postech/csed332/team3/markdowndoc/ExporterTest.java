package edu.postech.csed332.team3.markdowndoc;

import org.junit.Test;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.junit.Assert.assertEquals;

public class ExporterTest {

    @Test
    public void testExport() {
        Exporter.export("MarkdownDoc");
        Set<String> zipSet = new HashSet<>();

        try {
            ZipFile zipFile = new ZipFile("MarkdownDoc.zip");
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                if (!zipEntry.isDirectory())
                    zipSet.add(zipEntry.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(Set.of("html/test/java/edu/postech/csed332/team3/markdowndoc/MarkdownPairCompletionTest.html",
                "html/test/java/edu/postech/csed332/team3/markdowndoc/converter/TemplateUtilTest.html",
                "html/test/java/edu/postech/csed332/team3/markdowndoc/converter/MarkdownParserTest.html",
                "html/test/java/edu/postech/csed332/team3/markdowndoc/converter/TemplateUtilStub.html",
                "html/test/java/edu/postech/csed332/team3/markdowndoc/explorer/FileManagerStub.html",
                "html/test/java/edu/postech/csed332/team3/markdowndoc/explorer/ProjectModelTest.html",
                "html/test/java/edu/postech/csed332/team3/markdowndoc/explorer/FileManagerStubTest.html",
                "html/test/java/edu/postech/csed332/team3/markdowndoc/explorer/FileManagerTest.html",
                "html/test/java/edu/postech/csed332/team3/markdowndoc/explorer/MdDocElementVisitorTest.html",
                "html/test/java/edu/postech/csed332/team3/markdowndoc/BrowserControllerStub.html",
                "html/test/java/edu/postech/csed332/team3/markdowndoc/BrowserTestStub.html",
                "html/test/java/edu/postech/csed332/team3/markdowndoc/MarkdownCompletionTest.html",
                "html/main/java/edu/postech/csed332/team3/markdowndoc/converter/MarkdownParser.html",
                "html/main/java/edu/postech/csed332/team3/markdowndoc/converter/TemplateUtil.html",
                "html/main/java/edu/postech/csed332/team3/markdowndoc/util/LoggerUtil.html",
                "html/main/java/edu/postech/csed332/team3/markdowndoc/ProjectNavigator.html",
                "html/main/java/edu/postech/csed332/team3/markdowndoc/explorer/ProjectModel.html",
                "html/main/java/edu/postech/csed332/team3/markdowndoc/explorer/ActiveProjectModel.html",
                "html/main/java/edu/postech/csed332/team3/markdowndoc/explorer/MdDocElementVisitor.html",
                "html/main/java/edu/postech/csed332/team3/markdowndoc/explorer/UpdateListener.html",
                "html/main/java/edu/postech/csed332/team3/markdowndoc/explorer/FileManager.html",
                "html/main/java/edu/postech/csed332/team3/markdowndoc/BrowserView.html",
                "html/main/java/edu/postech/csed332/team3/markdowndoc/BrowserControllerInterface.html",
                "html/main/java/edu/postech/csed332/team3/markdowndoc/BrowserController.html",
                "html/main/java/edu/postech/csed332/team3/markdowndoc/MarkdownTypedHandler.html",
                "html/main/java/edu/postech/csed332/team3/markdowndoc/BrowserWindowFactory.html"),
                zipSet);
    }
}
