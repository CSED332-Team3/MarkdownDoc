package edu.postech.csed332.team3.markdowndoc;


import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiReferenceList;
import com.sun.istack.Nullable;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for getting files from the Resources package
 */
public class TemplateUtil {
    private ClassLoader classLoader;
    private InputStream header;
    private InputStream footer;

    public TemplateUtil() {
        classLoader = Thread.currentThread().getContextClassLoader();
        header = classLoader.getResourceAsStream("");
    }

    /**
     * Returns HTML div element of class label section
     *
     * @param className the class name
     * @param pkg the package name
     * @param ext any class this extends
     * @param impl (a list of) any interface this implements
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
    public String classLabel(PsiClass psiClass) throws InvalidParameterException {
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
}
