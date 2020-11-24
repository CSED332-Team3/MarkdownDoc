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

    private FileHandler fileHandler;
    private Filter filter;

    private static class MethodNamePrinter extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration md, Void arg) {
            super.visit(md, arg);
            System.out.println("Method Name Printed: " + md.getName());
        }
    }

    public JsonObject ParsingComment(List<Comment> comments) {
        //leave only JavaDoc comments
        List<Comment> JavaDocComments = comments.stream()
                .filter(c -> c.isJavadocComment())
                .collect(Collectors.toList());

        //get method information

        return null;

    }

    //문제1. 어떤 순서로 저장되는지 모른다
    //문제2. 정상작동하는지 모른다
    public JsonArray AllJavadocExtractor(File filename) throws IOException {

        CompilationUnit cu = StaticJavaParser.parse(filename);
        JsonArray jArray = new JsonArray();
        explore(cu, jArray);

        return jArray;
    }

    public void explore(Node node, JsonArray jArray) {
        JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty(ElementsInfo(node), (node.getComment()).toString());
        } catch (NullPointerException e) {
            jsonObject.addProperty(ElementsInfo(node), false);
        }
        if(IsElement(node))
            jArray.add(jsonObject);
        if (node.getChildNodes() != null) {
            for (Node child : node.getChildNodes()) {
                explore(child, jArray);
            }
        }
    }


    private boolean IsElement(Node node) {
        if (node instanceof ClassOrInterfaceDeclaration) {
            return true;
        }
        if (node instanceof ConstructorDeclaration) {
            return true;
        }
        if (node instanceof FieldDeclaration) {
            return true;
        }
        if (node instanceof MethodDeclaration) {
            return true;
        }
        if (node instanceof EnumDeclaration) {
            return true;
        }

        return false;
    }

    private static String ElementsInfo(Node node) {
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

    public static boolean isJavaDoc(String file) {
        return file.endsWith(".html");
    }

}
