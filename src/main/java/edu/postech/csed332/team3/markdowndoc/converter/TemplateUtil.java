package edu.postech.csed332.team3.markdowndoc.converter;


import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.psi.javadoc.PsiDocToken;
import com.intellij.psi.util.PsiTypesUtil;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for getting files from the Resources package
 */
public class TemplateUtil {

    /**
     * Get content of the header
     *
     * @return the header as String
     */
    public static String header() throws IOException {
        InputStream headerStream = TemplateUtil.class.getResourceAsStream(File.separator + "header.part");
        StringWriter headerWriter = new StringWriter();
        IOUtils.copy(headerStream, headerWriter, StandardCharsets.UTF_8);
        String header = headerWriter.toString() + "\n";
        headerStream.close();

        return header;
    }

    /**
     * Get content of the footer
     *
     * @return the footer as String
     */
    public static String footer() throws IOException {
        InputStream footerStream = TemplateUtil.class.getResourceAsStream(File.separator + "footer.part");
        StringWriter footerWriter = new StringWriter();
        IOUtils.copy(footerStream, footerWriter, StandardCharsets.UTF_8);
        String footer = footerWriter.toString() + "\n";
        footerStream.close();

        return footer;
    }

    /**
     * Returns HTML div element of class label section
     *
     * @param psiClass the PsiClass element
     * @return the HTML string
     * @throws InvalidParameterException className doesn't exist
     */
    public static String appendFirst(PsiClass psiClass) {
        String className = psiClass.getName();
        StringBuilder html = new StringBuilder();

        // Check null
        if (className == null) throw new InvalidParameterException("Class name is null");

        String pkg = getPkg(psiClass);
        String ext = getExtends(psiClass);
        List<String> impl = getImplements(psiClass);

        html.append(pkg)
                .append("<h1 id=\"class\">")
                .append(className)
                .append("</h1>");

        if (ext != null && ext.length() > 0)
            html.append("<div><strong>extends</strong> ")
                    .append("<a id=\"c-")
                    .append(ext)
                    .append("\">")
                    .append(ext)
                    .append("</a>")
                    .append("</div>");

        if (impl != null && !impl.isEmpty())
            for (String i : impl)
                html.append("<div><strong>implements</strong> ")
                        .append("<a id=\"c-")
                        .append(i)
                        .append("\">")
                        .append(i)
                        .append("</a>")
                        .append("</div>");


        html.append("\n")
                .append(getCommentFirst(psiClass)) // Opens table tag
                .append("<tr><td>")
                .append(getTagComment(psiClass.getDocComment()))
                .append("</td></tr>");

        return html.toString();
    }

    /**
     * Returns HTML table row of method
     * with method description and tags.
     *
     * @param element the PsiMethod element
     * @return the HTML string
     */
    public static String append(PsiNamedElement element) {
        String name = element.getName();
        String type = getType(element);

        StringBuilder html = new StringBuilder("<tr><td data-type=\"");
        html.append(type)
                .append("\"><h5>")
                .append(getElementType(element))
                .append("</h5><h3>")
                .append(getId(element));

        if (element instanceof PsiMethod)
            html.append(((PsiMethod) element).getModifierList().getText())
                    .append(" ");

        if (type != null)
            html.append(type)
                    .append(" ");

        html.append(name)
                .append("</a></h3>")
                .append(getComment(((PsiJavaDocumentedElement) element).getDocComment()))
                .append(getTagComment(((PsiJavaDocumentedElement) element).getDocComment()));

        html.append("</td></tr>\n");

        return html.toString();
    }

    /**
     * Append the table close tag at the end
     *
     * @return the table close tag
     */
    public static String appendLast() {
        return "</table>";
    }

    /**
     * Returns HTML list of all classes in the project
     * This acts as a quick index
     *
     * @param classes the list of all class names
     * @return the HTML string
     */
    // TODO: Add functionality
    public static String allClasses(List<String> classes) {
        StringBuilder html = new StringBuilder("<h2>All classes</h2><div class=\"all\">");

        for (String c : classes)
            html.append("<a id=\"c-")
                    .append(c)
                    .append("\">")
                    .append(c)
                    .append("</a><br>");


        html.append("</div>");

        return html.toString();
    }

    /**
     * Make the string fit the template
     *
     * @param psiClass target class
     * @return the edited package string
     */
    private static String getPkg(PsiClass psiClass) {
        String pkg = null;
        String qualifiedName = psiClass.getQualifiedName();
        if (qualifiedName != null) {
            int classNameIndex = qualifiedName.lastIndexOf(".");
            pkg = qualifiedName.substring(0, classNameIndex);
        }

        StringBuilder stringBuilder = new StringBuilder();
        if (pkg != null && pkg.length() > 0)
            stringBuilder.append("<div id=\"pkg\" class=\"pkg\">").append(pkg).append("</div>\n");
        return stringBuilder.toString();
    }

    @org.jetbrains.annotations.Nullable
    private static List<String> getImplements(PsiClass psiClass) {
        PsiReferenceList implList = psiClass.getImplementsList();
        List<String> impl = null;
        if (implList != null) {
            impl = new ArrayList<>();
            for (PsiClassType c : implList.getReferencedTypes()) {
                PsiClass implClass = PsiTypesUtil.getPsiClass(c);
                if (implClass != null) {
                    impl.add(implClass.getQualifiedName());
                }
            }
        }
        return impl;
    }

    @org.jetbrains.annotations.Nullable
    private static String getExtends(PsiClass psiClass) {
        PsiReferenceList extList = psiClass.getExtendsList();
        if (extList != null && extList.getReferencedTypes().length > 0) {
            PsiClass extClass = PsiTypesUtil.getPsiClass(extList.getReferencedTypes()[0]);
            if (extClass != null) {
                return extClass.getQualifiedName();
            }
        }
        return null;
    }

    private static String getElementType(PsiElement element) {
        return element.toString().split(":")[0].replaceFirst("Psi", "");
    }

    private static String getCommentFirst(PsiClass psiClass) {
        return "<h2>Description</h2>\n<table id = \"table\">\n" + "<tr><td>" +
                getComment(psiClass.getDocComment()) +
                "</td></tr>";
    }

    private static String getTagComment(PsiDocComment docComment) {
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
    private static String getComment(PsiDocComment docComment) {
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
    private static String getType(PsiElement element) {
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

    /**
     * A form of Id is "type"-"name". <br>
     * Type is one of "c", "m", "f".
     *
     * @param element target element
     * @return id of element
     */
    private static String getId(PsiNamedElement element) {
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
}
