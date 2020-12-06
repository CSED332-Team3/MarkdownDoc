package edu.postech.csed332.team3.markdowndoc;

import java.security.InvalidParameterException;
import java.util.List;

public class TemplateUtilStub {
    private TemplateUtilStub() {
    }

    public static String appendFirst(String name, String pkg, String ext, List<String> impl) {
        StringBuilder html = new StringBuilder();

        // Check null
        if (name == null) throw new InvalidParameterException("Class name is null");

        html.append(pkg)
                .append("<h1>")
                .append(name)
                .append("</h1>");

        if (ext != null && ext.length() > 0)
            html.append("<div><strong>extends</strong> <a id=\"c-")
                    .append(ext)
                    .append("\">")
                    .append(ext)
                    .append("</a></div>");

        if (impl != null && !impl.isEmpty())
            for (String i : impl)
                html.append("<div><strong>implements</strong> <a id=\"c-")
                        .append(i)
                        .append("\">")
                        .append(i)
                        .append("</a></div>");


        html.append("</div>");

        return html.toString();
    }

    /**
     * Make string fit a form.
     *
     * @param element  "element type" (+ type).
     * @param name     a name of element.
     * @param modifier a modifier like `static` if element is "PsiMethod", or null.
     * @param comment  a comment of element.
     * @return edited string.
     */
    public static String append(String element, String name, String modifier, String comment) {
        StringBuilder html = new StringBuilder("<tr><td data-type=\"");
        String type = getType(element);
        String elementType = getElementType(element);
        html.append(type)
                .append("\"><h5>")
                .append(elementType)
                .append("</h5><h3>")
                .append(getId(element, name));

        if (elementType.equals("Method"))
            html.append(modifier)
                    .append(" ");

        if (type != null)
            html.append(type)
                    .append(" ");

        html.append(name)
                .append("</a></h3>")
                .append(comment);


//        // Tags
//        if (tags != null && !tags.isEmpty()) {
//            html.append("<p>");
//            for (String tag : tags) {
//                // Strip the leading @tag from this string
//                String[] subStr = tag.split(" ", 2);
//                if (subStr.length < 2) continue; // This indicates no tag or no description
//
//                html.append("<strong class=\"alert\">")
//                        .append(subStr[0])
//                        .append("</strong> ")
//                        .append(subStr[1])
//                        .append("<br>");
//            }
//            html.append("</p>");
//        }

        html.append("</td></tr>\n");

        return html.toString();
    }

    private static String getElementType(String str) {
        return str.split(":")[0].replaceFirst("Psi", "");
    }

    private static String getType(String str) {
        if (str.equals("PsiClass"))
            return "";
        if (str.contains("PsiMethod")) {
            String[] strings = str.split(":");
            if (strings.length == 1)
                return "";
            return strings[1];
        }
        if (str.contains("PsiField"))
            return str.split(":")[1];
        return null;
    }

    private static String getId(String str, String name) {
        StringBuilder builder = new StringBuilder("<a id=\"");
        if (str.equals("PsiClass"))
            builder.append("c");
        else if (str.contains("PsiMethod"))
            builder.append("m");
        else if (str.contains("PsiField"))
            builder.append("f");
        builder.append("-").append(name).append("\">\n");

        return builder.toString();
    }
}
