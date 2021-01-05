package edu.postech.csed332.team3.markdowndoc;

import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.psi.javadoc.PsiDocToken;
import edu.postech.csed332.team3.markdowndoc.converter.MarkdownParser;

public class PsiInfo {

    private PsiInfo() {
    }

    /**
     * A form of Id is "type"-"name". <br>
     * Type is one of "c", "m", "f".
     *
     * @param element target element
     * @return id of element
     */
    public static String getId(PsiNamedElement element) {
        StringBuilder builder = new StringBuilder("<a id=\"");
        if (element instanceof PsiClass)
            builder.append("c");
        else if (element instanceof PsiMethod)
            builder.append("m");
        else if (element instanceof PsiField)
            builder.append("f");
        builder.append("-").append(element.getName()).append("\">\n");

        return builder.toString();
    }

    public static String getElementType(PsiElement element) {
        return element.toString().split(":")[0].replaceFirst("Psi", "");
    }

    public static String getTagComment(PsiDocComment docComment) {
        if (docComment != null) {
            StringBuilder html = new StringBuilder();
            PsiDocTag[] docTags = docComment.getTags();
            if (docTags.length > 0) {
                html.append("<p>");
                for (PsiDocTag tag : docTags) {
                    html.append("<strong class=\"alert\">@")
                            .append(tag.getName())
                            .append("</strong> ");
                    PsiElement[] dataElements = tag.getDataElements();
                    for (PsiElement dataElement : dataElements) html.append(dataElement.getText()).append(" ");
                    html.append("<br>");
                }
                html.append("</p>");
            }
            return html.toString();
        }
        return "";
    }

    /**
     * Apply code markdown grammar to visualize doc comment.
     *
     * @param docComment comment for editing.
     * @return edited doc comment.
     */
    public static String getComment(PsiDocComment docComment) {
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

    /**
     * Get type of element. <br>
     * If element is PsiMethod, return a return type of method. <br>
     * If element is PsiField, return a type of field.
     *
     * @param element target element
     * @return type of element, return null if element is not PsiClass, PsiMethod, PsiField.
     */
    public static String getType(PsiElement element) {
        if (element instanceof PsiClass)
            return "";
        if (element instanceof PsiMethod) {
            PsiType returnType = ((PsiMethod) element).getReturnType();
            if (returnType == null)
                return "";
            return returnType.getPresentableText();
        }
        if (element instanceof PsiField)
            return ((PsiField) element).getType().toString().split(":")[1];
        return null;
    }
}
