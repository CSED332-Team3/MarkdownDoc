package edu.postech.csed332.team3.markdowndoc;


import com.intellij.psi.*;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
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

    private String header;
    private String footer;

    public TemplateUtil() {
        try {
            InputStream headerStream = getClass().getResourceAsStream(File.separator + "header.part");
            StringWriter headerWriter = new StringWriter();
            IOUtils.copy(headerStream, headerWriter, StandardCharsets.UTF_8);
            header = headerWriter.toString();
            headerStream.close();

            InputStream footerStream = getClass().getResourceAsStream(File.separator + "footer.part");
            StringWriter footerWriter = new StringWriter();
            IOUtils.copy(footerStream, footerWriter, StandardCharsets.UTF_8);
            footer = footerWriter.toString();
            footerStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            header = null;
            footer = null;
        }
    }

    /**
     * Get content of the header
     *
     * @return the header as String
     */
    public String header() {
        return header;
    }

    /**
     * Get content of the footer
     *
     * @return the footer as String
     */
    public String footer() {
        return footer;
    }

    /**
     * Returns HTML div element of class label section
     *
     * @param className the class name
     * @param pkg       the package name
     * @param ext       any class this extends
     * @param impl      (a list of) any interface this implements
     * @return the HTML string
     */
    public String classLabel(String className, String pkg, @Nullable String ext, @Nullable List<String> impl) {
        StringBuilder html = new StringBuilder("<div>");

        if (pkg != null && pkg.length() > 0) {
            html.append("<div class=\"pkg\">").append(pkg).append("</div>");
        }
        html.append("<h1>").append(className).append("</h1>");
        if (ext != null && ext.length() > 0) {
            html.append("<div><strong>extends</strong> <a id=\"c-")
                    .append(ext)
                    .append("\">")
                    .append(ext)
                    .append("</a></div>");
        }
        if (impl != null && !impl.isEmpty()) {
            for (String i : impl) {
                html.append("<div><strong>implements</strong> <a id=\"c-")
                        .append(i)
                        .append("\">")
                        .append(i)
                        .append("</a></div>");
            }
        }

        html.append("</div>");
        return html.toString();
    }

    /**
     * Returns HTML div element of class label section
     *
     * @param psiClass the PsiClass element
     * @return the HTML string
     * @throws InvalidParameterException className doesn't exist
     */
    public String classLabel(PsiClass psiClass) {
        String className = psiClass.getName();
        String pkg = null;
        String ext = null;
        List<String> impl = null;

        String qualifiedName = psiClass.getQualifiedName();
        PsiReferenceList extList = psiClass.getExtendsList();
        PsiReferenceList implList = psiClass.getImplementsList();

        // Check null
        if (className == null) throw new InvalidParameterException("Class name is null");

        // Get package name from qualified name
        if (qualifiedName != null) {
            int classNameIndex = qualifiedName.lastIndexOf(".");
            pkg = qualifiedName.substring(0, classNameIndex);
        }

        if (extList != null) {
            ext = extList.getReferencedTypes()[0].getClassName();
        }

        if (implList != null) {
            impl = new ArrayList<>();
            for (PsiClassType c : implList.getReferencedTypes()) {
                impl.add(c.getClassName());
            }
        }

        return classLabel(className, pkg, ext, impl);
    }

    /**
     * Returns HTML table starting element
     * with class description and tags.
     * NOTE: this class does not close the HTML table tag.
     *
     * @param desc the class description (html surrounded with p tag)
     * @param tags list of tag strings, such as @param or @author, etc.
     * @return the HTML string
     */
    public String classDesc(String desc, @Nullable List<String> tags) {
        StringBuilder html = new StringBuilder("<h2>Description</h2><table id = \"table\">");

        // Description
        html.append("<tr><td>")
                .append(desc)
                .append("</td></tr>");

        // Tags
        if (tags != null && !tags.isEmpty()) {
            html.append("<tr><td><p>");
            for (String tag : tags) {
                // Strip the leading @tag from this string
                String[] substr = tag.split(" ", 2);
                if (substr.length < 2) continue; // This indicates no tag or no description

                html.append("<strong class=\"alert\">")
                        .append(substr[0])
                        .append("</strong> ")
                        .append(substr[1])
                        .append("<br>");
            }
            html.append("</p></td></tr>");
        }

        return html.toString();
    }

    /**
     * Returns HTML table row of field
     * with field description.
     *
     * @param fieldName the field name
     * @param type      the field type
     * @param desc      the field description (html surrounded with p tag)
     * @param tags      list of tag strings, such as @param or @author, etc.
     * @return the HTML string
     */
    public String field(String fieldName, String type, @Nullable String desc, @Nullable List<String> tags) {
        StringBuilder html = new StringBuilder("<tr><td data-type=\"");
        html.append(type)
                .append("\"><h5>Field</h5><h3><a id=\"f-")
                .append(fieldName)
                .append("\">")
                .append(type)
                .append(" ")
                .append(fieldName)
                .append("</a></h3>");

        if (desc != null) {
            html.append(desc);
        }

        // Tags
        if (tags != null && !tags.isEmpty()) {
            html.append("<p>");
            for (String tag : tags) {
                // Strip the leading @tag from this string
                String[] subStr = tag.split(" ", 2);
                if (subStr.length < 2) continue; // This indicates no tag or no description

                html.append("<strong class=\"alert\">")
                        .append(subStr[0])
                        .append("</strong> ")
                        .append(subStr[1])
                        .append("<br>");
            }
            html.append("</p>");
        }

        html.append("</td></tr>");

        return html.toString();
    }

    /**
     * Returns HTML table row of field
     * with field description.
     *
     * @param psiField the PsiField element
     * @param desc     the field description (html surrounded with p tag)
     * @param tags     list of tag strings, such as @param or @author, etc.
     * @return the HTML string
     */
    public String field(PsiField psiField, @Nullable String desc, @Nullable List<String> tags) {
        @NotNull String fieldName = psiField.getName();
        String type = psiField.getType().getPresentableText();

        return field(fieldName, type, desc, tags);
    }

    /**
     * Returns HTML table row of method
     * with method description and tags.
     *
     * @param methodName the method name
     * @param returnType the return type, null for constructors
     * @param modifier   the modifiers (public static ...)
     * @param desc       the method description (html surrounded with p tag)
     * @param tags       list of tag strings, such as @param or @author, etc.
     * @return the HTML string
     */
    public String method(String methodName, @Nullable String returnType, String modifier, @Nullable String desc, @Nullable List<String> tags) {
        StringBuilder html = new StringBuilder("<tr><td data-type=\"");
        html.append(returnType)
                .append("\"><h5>Method</h5><h3><a id=\"m-")
                .append(methodName)
                .append("\">")
                .append(modifier)
                .append(" ");
        if (returnType != null) {
            html.append(returnType)
                    .append(" ");
        }
        html.append(methodName)
                .append("</a></h3>");

        // Description
        if (desc != null) {
            html.append(desc);
        }

        // Tags
        if (tags != null && !tags.isEmpty()) {
            html.append("<p>");
            for (String tag : tags) {
                // Strip the leading @tag from this string
                String[] subStr = tag.split(" ", 2);
                if (subStr.length < 2) continue; // This indicates no tag or no description

                html.append("<strong class=\"alert\">")
                        .append(subStr[0])
                        .append("</strong> ")
                        .append(subStr[1])
                        .append("<br>");
            }
            html.append("</p>");
        }

        html.append("</td></tr>");

        return html.toString();
    }

    /**
     * Returns HTML table row of method
     * with method description and tags.
     *
     * @param psiMethod the PsiMethod element
     * @param desc      the method description (html surrounded with p tag)
     * @param tags      list of tag strings, such as @param or @author, etc.
     * @return the HTML string
     */
    public String method(PsiMethod psiMethod, @Nullable String desc, @Nullable List<String> tags) {
        @NotNull String methodName = psiMethod.getName();
        String returnType = psiMethod.getReturnType() == null ? null : psiMethod.getReturnType().getPresentableText();
        String accessMod = psiMethod.getModifierList().getText();

        return method(methodName, returnType, accessMod, desc, tags);
    }

    /**
     * Close the Description table
     *
     * @return the HTML string
     */
    public String closeDesc() {
        return "</table>";
    }

    /**
     * Returns HTML list of all classes in the project
     * This acts as a quick index
     *
     * @param classes the list of all class names
     * @return the HTML string
     */
    public String allClasses(List<String> classes) {
        StringBuilder html = new StringBuilder("<h2>All classes</h2><div class=\"all\">");

        for (String c : classes) {
            html.append("<a id=\"c-")
                    .append(c)
                    .append("\">")
                    .append(c)
                    .append("</a><br>");
        }

        html.append("</div>");

        return html.toString();
    }
}
