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

    /**
     * Writes a target class in its HTML string form. <br>
     * This form is emphasized after rendering.
     * </p>
     *
     * @param aClass Class of java file
     */
    public void writeFirst(PsiClass aClass) {
        stringBuilder.append(TemplateUtil.appendFirst(aClass));
    }

    /**
     * Writes a target element in its HTML string form. <br>
     * If it has a document, it will also be written together.
     * </p>
     *
     * @param element Named element to be written.
     */
    public void write(PsiNamedElement element) {
        stringBuilder.append(TemplateUtil.append(element));
    }

    /**
     * Write string to file & close file writer.
     * Reset string.
     *
     * @param currentClass the current PsiClass
     */
    public void close(PsiClass currentClass) {
        try {
            if (currentClass != null)
                stringBuilder.append(TemplateUtil.appendLast()); // Close table tag
            stringBuilder.append(TemplateUtil.allClasses(currentClass)); // Add class index
            stringBuilder.append(TemplateUtil.footer());
            fileWriter.write(MarkdownParser.parse(stringBuilder.toString()));
            fileWriter.close();
            stringBuilder.setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
