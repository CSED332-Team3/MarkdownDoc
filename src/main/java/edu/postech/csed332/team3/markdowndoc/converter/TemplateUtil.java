package edu.postech.csed332.team3.markdowndoc.converter;


import com.intellij.psi.*;
import com.intellij.psi.util.PsiTypesUtil;
import edu.postech.csed332.team3.markdowndoc.explorer.MdDocElementVisitor;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static edu.postech.csed332.team3.markdowndoc.PsiInfo.*;
import static edu.postech.csed332.team3.markdowndoc.explorer.MdDocElementVisitor.getAllClasses;

/**
 * Utility class for getting files from the Resources package
 */
public class TemplateUtil {

    private TemplateUtil() {
    }

    /**
     * Get content of the header
     *
     * @return the header as String
     */
    public static String header() throws IOException {
        InputStream headerStream = TemplateUtil.class.getResourceAsStream("/header.part");
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
        InputStream footerStream = TemplateUtil.class.getResourceAsStream("/footer.part");
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
            html.append(ext);

        if (impl != null && !impl.isEmpty())
            for (String i : impl)
                html.append(i);


        html.append("\n<h2>Description</h2>\n<table id = \"table\">\n<tr><td>")
                .append(getComment(psiClass.getDocComment()))
                .append("</td></tr><tr><td>")
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
        html.append(escapeString(type))
                .append("\"><h5>")
                .append(getElementType(element))
                .append("</h5><h3>")
                .append(getId(element));

        if (element instanceof PsiMethod)
            html.append(((PsiMethod) element).getModifierList().getText())
                    .append(" ");
        else if (element instanceof PsiClass) {
            PsiModifierList modifierList = ((PsiClass) element).getModifierList();
            if (modifierList != null && modifierList.getText() != null)
                html.append(modifierList.getText())
                        .append(" ");
        }

        html.append(escapeString(type))
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
     * @param currentClass the current PsiClass
     * @return the HTML string
     */
    public static String allClasses(@Nullable PsiClass currentClass) {
        StringBuilder html = new StringBuilder("<h2>All classes</h2><div class=\"all\">");

        for (PsiClass c : getAllClasses()) {
            html.append("<a id=\"c-")
                    .append(c.getQualifiedName())
                    .append("\" href=\"");

            // Link to the document using relative paths
            html.append(getRelativeLink(currentClass, c));

            html.append("\">")
                    .append(c.getName())
                    .append("</a><br>");
        }

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

    @Nullable
    private static List<String> getImplements(PsiClass psiClass) {
        PsiReferenceList implList = psiClass.getImplementsList();
        List<String> impl = null;
        if (implList != null) {
            impl = new ArrayList<>();
            for (PsiClassType c : implList.getReferencedTypes()) {
                PsiClass implClass = PsiTypesUtil.getPsiClass(c);
                if (implClass != null) {
                    String implHTML = "<div><strong>implements</strong> " + "<a id=\"c-" +
                            implClass.getQualifiedName() +
                            "\" href=\"" +
                            // Create relative link
                            getRelativeLink(psiClass, implClass) +
                            "\">" +
                            implClass.getName() +
                            "</a></div>";

                    impl.add(implHTML);
                }
            }
        }
        return impl;
    }

    @Nullable
    private static String getExtends(PsiClass psiClass) {
        PsiReferenceList extList = psiClass.getExtendsList();
        if (extList != null && extList.getReferencedTypes().length > 0) {
            PsiClass extClass = PsiTypesUtil.getPsiClass(extList.getReferencedTypes()[0]);
            if (extClass != null && extClass.getQualifiedName() != null) {
                return "<div><strong>extends</strong> " + "<a id=\"c-" +
                        extClass.getQualifiedName() +
                        "\" href=\"" +

                        // Create relative link
                        getRelativeLink(psiClass, extClass) +
                        "\">" +
                        extClass.getName() +
                        "</a></div>";
            }
        }
        return null;
    }

    // Create relative URL between two PsiClass documents
    private static String getRelativeLink(@Nullable PsiClass from, PsiClass to) {
        // Check of to-class's document exists
        if (!MdDocElementVisitor.getAllClassesSet().contains(to))
            return ""; // Do not link to any page

        StringBuilder url = new StringBuilder();

        if (from != null && from.getQualifiedName() != null) {
            String[] pkgFrom = from.getQualifiedName().split("\\.");
            url.append("../".repeat(Math.max(0, pkgFrom.length - 1)));
        } else {
            url.append("./");
            url.append("java/");
        }

        if (to.getQualifiedName() != null) {
            String[] pkgTo = to.getQualifiedName().split("\\.");
            for (String path : pkgTo) {
                url.append(path)
                        .append("/");
            }

            // Replace last path with .html
            url.replace(url.length() - 1, url.length(), ".")
                    .append("html");
        }

        return url.toString();
    }

    private static String escapeString(String str) {
        return str.replace("<", "&lt;").replace(">", "&gt;");
    }
}
