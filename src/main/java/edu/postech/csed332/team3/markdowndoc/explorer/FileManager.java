package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiNamedElement;
import edu.postech.csed332.team3.markdowndoc.converter.MarkdownParser;
import edu.postech.csed332.team3.markdowndoc.converter.TemplateUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Manage contents of html files.
 */
public class FileManager {
    private final StringBuilder stringBuilder = new StringBuilder();
    private FileWriter fileWriter = null;

    public FileManager(String path) {
        File file = new File(path);
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            fileWriter = new FileWriter(path);
            stringBuilder.append(TemplateUtil.header());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFirst(PsiClass aClass) {
        stringBuilder.append(TemplateUtil.appendFirst(aClass));
    }

    /**
     * Writes a target element in its HTML string form.
     * <p/>
     * If it has a document, it will also be written together.
     *
     * @param element Named element to be written.
     */
    public void write(PsiNamedElement element) {
        stringBuilder.append(TemplateUtil.append(element));
    }

    /**
     * Write string to file & close file writer.
     * Reset string.
     */
    public void close() {
        try {
            stringBuilder.append(TemplateUtil.footer());
            fileWriter.write(MarkdownParser.parse(stringBuilder.toString()));
            fileWriter.close();
            stringBuilder.setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
