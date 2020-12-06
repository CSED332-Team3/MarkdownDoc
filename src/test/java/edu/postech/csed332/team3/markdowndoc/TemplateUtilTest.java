package edu.postech.csed332.team3.markdowndoc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TemplateUtilTest {

    private final TemplateUtil templateUtil = new TemplateUtil();

    @Test
    void headerTest() {
        assertEquals(templateUtil.header(), "<!DOCTYPE html><html><head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <title>MarkdownDoc</title> <style>html{font-family: Arial, Helvetica, sans-serif; font-size: 16px; padding: 1rem;}h1{margin-top: 0.5rem;}h1 + div{margin-top: -0.5rem;}h2{font-style: italic; color: #555555; margin-top: 2rem;}h3{font-family:'Courier New', Courier, monospace;}h5{margin-bottom: -1rem; color: #777777;}.pkg{color: #777777; margin-top: 1rem;}table{border-collapse: collapse; margin: 1rem 0; width: 100%;}td{border: 1px solid #aaaaaa; border-width: 1px 0; padding: 0.25rem 1rem;}td table td{border-width: 1px;}p{line-height: 1.6;}.alert{color: #FF3333;}a{text-decoration: none; font-weight: 500; color: #0000FF;}a:hover{text-decoration: underline; cursor: pointer;}.all{margin-left: 1rem; line-height: 1.2; font-family: 'Courier New', Courier, monospace;}.credits{text-align: right; margin-top: 2rem; color: #555555;}</style></head><body>");
    }

    @Test
    void footerTest() {
        assertEquals(templateUtil.footer(), "<div class=\"credits\"> Generated with MarkdownDoc</div><script type=\"text/javascript\">document.addEventListener(\"DOMContentLoaded\", function(){var links=document.getElementsByTagName('a'); for (var i=links.length - 1; i >=0; --i){links[i].addEventListener(\"click\", function(){var id=this.getAttribute('id'); if (id){console.log(id);}});}}); function sortTable(method){var table, rows, switching, i, x, y, shouldSwitch, dir; table=document.getElementById('table'); switching=true; while (switching){switching=false; rows=table.rows; for (i=2; i < rows.length - 1; i++){shouldSwitch=false; x=rows[i].getElementsByTagName(\"td\")[0]; y=rows[i + 1].getElementsByTagName(\"td\")[0]; if (x.getAttribute(\"data-type\") !=method){if (y.getAttribute(\"data-type\")==method){shouldSwitch=true; break;}}}if (shouldSwitch){rows[i].parentNode.insertBefore(rows[i + 1], rows[i]); switching=true;}}}</script></body></html>");
    }

    @Test
    void classLabelTest() {
        assertEquals(templateUtil.classLabel("MyClass", "com.example.package", "MyParentClass", List.of("MyInterface", "MyInterface2")),
                "<div><div class=\"pkg\">com.example.package</div><h1>MyClass</h1><div><strong>extends</strong> <a id=\"c-MyParentClass\">MyParentClass</a></div><div><strong>implements</strong> <a id=\"c-MyInterface\">MyInterface</a></div><div><strong>implements</strong> <a id=\"c-MyInterface2\">MyInterface2</a></div></div>");
    }

    @Test
    void classDescTest() {
        assertEquals(templateUtil.classDesc("<p>An HTML description</p>", List.of("@param some param", "@author myself")),
                "<h2>Description</h2><table id = \"table\"><tr><td><p>An HTML description</p></td></tr><tr><td><p><strong class=\"alert\">@param</strong> some param<br><strong class=\"alert\">@author</strong> myself<br></p></td></tr>");
    }

    @Test
    void fieldTest() {
        assertEquals(templateUtil.field("myInt", "int", "<p>An HTML description</p>", null),
                "<tr><td data-type=\"int\"><h5>Field</h5><h3><a id=\"f-myInt\">int myInt</a></h3><p>An HTML description</p></td></tr>");
    }

    @Test
    void methodTest() {
        assertEquals(templateUtil.method("myMethod", "void", "public static", "<p>An HTML description</p>", List.of("@param var1 param", "@param var2 param 2")),
                "<tr><td data-type=\"void\"><h5>Method</h5><h3><a id=\"m-myMethod\">public static void myMethod</a></h3><p>An HTML description</p><p><strong class=\"alert\">@param</strong> var1 param<br><strong class=\"alert\">@param</strong> var2 param 2<br></p></td></tr>");
    }

    @Test
    void closeDescTest() {
        assertEquals(templateUtil.closeDesc(), "</table>");
    }

    @Test
    void allClassesTest() {
        assertEquals(templateUtil.allClasses(List.of("Class1", "Class2", "Class3")),
                "<h2>All classes</h2><div class=\"all\"><a id=\"c-Class1\">Class1</a><br><a id=\"c-Class2\">Class2</a><br><a id=\"c-Class3\">Class3</a><br></div>");
    }

    @Test
    void fullHTMLTest() {
        StringBuilder html = new StringBuilder(templateUtil.header());
        html.append(templateUtil.classLabel("MyClass", "com.example.package", "MyParentClass", List.of("MyInterface", "MyInterface2")))
                .append(templateUtil.classDesc("<p>An HTML description</p>", List.of("@param some param", "@author myself")))
                .append(templateUtil.field("myInt", "int", "<p>An HTML description</p>", null))
                .append(templateUtil.method("myMethod", "void", "public static", "<p>An HTML description</p>", List.of("@param var1 param", "@param var2 param 2")))
                .append(templateUtil.closeDesc())
                .append(templateUtil.allClasses(List.of("Class1", "Class2", "Class3")))
                .append(templateUtil.footer());

        assertEquals(html.toString(), "<!DOCTYPE html><html><head> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <title>MarkdownDoc</title> <style>html{font-family: Arial, Helvetica, sans-serif; font-size: 16px; padding: 1rem;}h1{margin-top: 0.5rem;}h1 + div{margin-top: -0.5rem;}h2{font-style: italic; color: #555555; margin-top: 2rem;}h3{font-family:'Courier New', Courier, monospace;}h5{margin-bottom: -1rem; color: #777777;}.pkg{color: #777777; margin-top: 1rem;}table{border-collapse: collapse; margin: 1rem 0; width: 100%;}td{border: 1px solid #aaaaaa; border-width: 1px 0; padding: 0.25rem 1rem;}td table td{border-width: 1px;}p{line-height: 1.6;}.alert{color: #FF3333;}a{text-decoration: none; font-weight: 500; color: #0000FF;}a:hover{text-decoration: underline; cursor: pointer;}.all{margin-left: 1rem; line-height: 1.2; font-family: 'Courier New', Courier, monospace;}.credits{text-align: right; margin-top: 2rem; color: #555555;}</style></head><body><div><div class=\"pkg\">com.example.package</div><h1>MyClass</h1><div><strong>extends</strong> <a id=\"c-MyParentClass\">MyParentClass</a></div><div><strong>implements</strong> <a id=\"c-MyInterface\">MyInterface</a></div><div><strong>implements</strong> <a id=\"c-MyInterface2\">MyInterface2</a></div></div><h2>Description</h2><table id = \"table\"><tr><td><p>An HTML description</p></td></tr><tr><td><p><strong class=\"alert\">@param</strong> some param<br><strong class=\"alert\">@author</strong> myself<br></p></td></tr><tr><td data-type=\"int\"><h5>Field</h5><h3><a id=\"f-myInt\">int myInt</a></h3><p>An HTML description</p></td></tr><tr><td data-type=\"void\"><h5>Method</h5><h3><a id=\"m-myMethod\">public static void myMethod</a></h3><p>An HTML description</p><p><strong class=\"alert\">@param</strong> var1 param<br><strong class=\"alert\">@param</strong> var2 param 2<br></p></td></tr></table><h2>All classes</h2><div class=\"all\"><a id=\"c-Class1\">Class1</a><br><a id=\"c-Class2\">Class2</a><br><a id=\"c-Class3\">Class3</a><br></div><div class=\"credits\"> Generated with MarkdownDoc</div><script type=\"text/javascript\">document.addEventListener(\"DOMContentLoaded\", function(){var links=document.getElementsByTagName('a'); for (var i=links.length - 1; i >=0; --i){links[i].addEventListener(\"click\", function(){var id=this.getAttribute('id'); if (id){console.log(id);}});}}); function sortTable(method){var table, rows, switching, i, x, y, shouldSwitch, dir; table=document.getElementById('table'); switching=true; while (switching){switching=false; rows=table.rows; for (i=2; i < rows.length - 1; i++){shouldSwitch=false; x=rows[i].getElementsByTagName(\"td\")[0]; y=rows[i + 1].getElementsByTagName(\"td\")[0]; if (x.getAttribute(\"data-type\") !=method){if (y.getAttribute(\"data-type\")==method){shouldSwitch=true; break;}}}if (shouldSwitch){rows[i].parentNode.insertBefore(rows[i + 1], rows[i]); switching=true;}}}</script></body></html>");
    }
}
