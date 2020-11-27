package edu.postech.csed332.team3.markdowndoc.searchproject;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.JavadocComment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ManageComment {
    /**
     * Extract javadoc comments and classes, methods info etc. from given file
     *
     * @param file file to be extracted
     * @return JsonArray that contains comments and classes, methods info etc.
     */
    public JsonArray AllJavadocExtractor(File file) throws IOException {
        //parsing java file using java parser
        CompilationUnit cu = StaticJavaParser.parse(file);
        JsonArray jArray = new JsonArray();

        //add classes, methods info. to jArray using recursion
        explore(cu, jArray);

        return jArray;
    }

    private void explore(Node node, JsonArray jArray) {
        JsonObject jsonObject = new JsonObject();
        try {
            if(node.getComment().isEmpty())
                jsonObject.addProperty(ElementsInfo(node), "");
            else {
                if(node.getComment().get() instanceof JavadocComment)
                    jsonObject.addProperty(ElementsInfo(node), String.valueOf(node.getComment().get()));
            }
        } catch (NullPointerException e) {
            jsonObject.addProperty(ElementsInfo(node), "");
        }
        if(IsElement(node))
            jArray.add(jsonObject);
        if (node.getChildNodes() != null) {
            for (Node child : node.getChildNodes()) {
                explore(child, jArray);
            }
        }
    }

    /**
     * Check whether Node is element or not
     * @param node node to be checked
     * @return is element or not
     */
    public static boolean IsElement(Node node) {
        if (node instanceof ClassOrInterfaceDeclaration) {
            return true;
        }
        else if (node instanceof ConstructorDeclaration) {
            return true;
        }
        else if (node instanceof FieldDeclaration) {
            return true;
        }
        else if (node instanceof MethodDeclaration) {
            return true;
        }
        else if (node instanceof EnumDeclaration) {
            return true;
        }

        return false;
    }

    private String ElementsInfo(Node node) {
        if (node instanceof ClassOrInterfaceDeclaration) {
            ClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration) node;
            if (classOrInterfaceDeclaration.isInterface()) {
                return "Interface: " + classOrInterfaceDeclaration.getName();
            } else {
                return "Class: " + classOrInterfaceDeclaration.getName();
            }
        }
        if (node instanceof ConstructorDeclaration) {
            ConstructorDeclaration constructorDeclaration = (ConstructorDeclaration) node;
            return "Constructor: " + constructorDeclaration.getDeclarationAsString();
        }
        if (node instanceof FieldDeclaration) {
            FieldDeclaration fieldDeclaration = (FieldDeclaration) node;
            List<String> varNames = fieldDeclaration.getVariables().stream().map(v -> v.getName().getId()).collect(Collectors.toList());
            return "Field: " + String.join(", ", varNames);
        }
        if (node instanceof MethodDeclaration) {
            MethodDeclaration methodDeclaration = (MethodDeclaration) node;
            return "Method: " + methodDeclaration.getDeclarationAsString();
        }
        if (node instanceof EnumDeclaration) {
            EnumDeclaration enumDeclaration = (EnumDeclaration) node;
            return "Enum: " + enumDeclaration.getName();
        }
        return node.toString();
    }

    /**
     * check file is Javafile
     * @param file file to be checked
     * @return if file is Javafile then return true, else return false
     */
    public static boolean isJavaFile(String file) {
        return file.endsWith(".java");
    }

}
