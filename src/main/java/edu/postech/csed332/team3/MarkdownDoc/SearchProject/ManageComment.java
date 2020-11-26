package edu.postech.csed332.team3.MarkdownDoc.SearchProject;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kotlin.reflect.jvm.internal.impl.utils.DFS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.stream.Collectors;

public class ManageComment {
    public JsonArray AllJavadocExtractor(File file) throws IOException {
        CompilationUnit cu = StaticJavaParser.parse(file);
        JsonArray jArray = new JsonArray();
        explore(cu, jArray);

        return jArray;
    }

    private void explore(Node node, JsonArray jArray) {
        JsonObject jsonObject = new JsonObject();
        try {
            if(node.getComment().isEmpty())
                jsonObject.addProperty(ElementsInfo(node), "");
            else
                jsonObject.addProperty(ElementsInfo(node), String.valueOf(node.getComment().get()));
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

    public static String ElementsInfo(Node node) {
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

    public static boolean isDocument(String file) {
        return file.endsWith(".md");
    }

    public static boolean isJavaFile(String file) {
        return file.endsWith(".java");
    }

}
