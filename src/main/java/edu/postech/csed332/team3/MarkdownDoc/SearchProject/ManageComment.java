package edu.postech.csed332.team3.MarkdownDoc.SearchProject;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ManageComment {
    List
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

    public JsonObject AllJavadocExtractor(File file) throws IOException {
        CompilationUnit cu = StaticJavaParser.parse(file);
        JsonObject json = null;
        return json;
    }

    private static String ElementsInfo(Node node) {
        if (node instanceof ClassOrInterfaceDeclaration) {
            ClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration)node;
            if (classOrInterfaceDeclaration.isInterface()) {
                return "###Interface " + classOrInterfaceDeclaration.getName();
            } else {
                return "###Class " + classOrInterfaceDeclaration.getName();
            }
        }
        if (node instanceof ConstructorDeclaration) {
            ConstructorDeclaration constructorDeclaration = (ConstructorDeclaration)node;
            return "- Constructor: " + constructorDeclaration.getDeclarationAsString();
        }
        if (node instanceof FieldDeclaration) {
            FieldDeclaration fieldDeclaration = (FieldDeclaration)node;
            List<String> varNames = fieldDeclaration.getVariables().stream().map(v -> v.getName().getId()).collect(Collectors.toList());
            return "- Field: " + String.join(", ", varNames);
        }
        if (node instanceof MethodDeclaration) {
            MethodDeclaration methodDeclaration = (MethodDeclaration)node;
            return "- Method: " + methodDeclaration.getDeclarationAsString();
        }
        if (node instanceof EnumDeclaration) {
            EnumDeclaration enumDeclaration = (EnumDeclaration)node;
            return "- Enum: " + enumDeclaration.getName();
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
