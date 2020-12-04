package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocToken;
import edu.postech.csed332.team3.markdowndoc.MarkdownParser;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a target element in its HTML string form.
     * <p/>
     * If it has a document, it will also be written together.
     *
     * @param element Named element to be written.
     */
    public void write(PsiNamedElement element) {
        stringBuilder
                .append(appendId(element))
                .append(element.getName());
        if (element instanceof PsiJavaDocumentedElement)
            stringBuilder.append(appendComment(((PsiJavaDocumentedElement) element).getDocComment()));
        stringBuilder.append("\n</p>\n");
    }

    /**
     * Write string to file & close file writer.
     * Reset string.
     */
    public void close() {
        try {
            fileWriter.write(MarkdownParser.parse(stringBuilder.toString()));
            fileWriter.close();
            stringBuilder.setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Apply code markdown grammar to visualize doc comment.
     *
     * @param docComment comment for editing.
     * @return edited doc comment.
     */
    private String appendComment(PsiDocComment docComment) {
        if (docComment != null) {
            StringBuilder builder = new StringBuilder();
            builder.append("\n");
            for (PsiElement descriptionElement : docComment.getDescriptionElements())
                if (descriptionElement instanceof PsiDocToken)
                    builder.append(descriptionElement.getText()).append('\n');
            return MarkdownParser.parse(builder.toString());
        }
        return "";
    }

    private String appendId(PsiNamedElement element) {
        StringBuilder builder = new StringBuilder("<p id=\"");
        if (element instanceof PsiClass)
            builder.append("c");
        else if (element instanceof PsiMethod)
            builder.append("m");
        else if (element instanceof PsiField)
            builder.append("f");
        builder.append("-").append(element.getName()).append("\">\n");

        return builder.toString();
    }
}
