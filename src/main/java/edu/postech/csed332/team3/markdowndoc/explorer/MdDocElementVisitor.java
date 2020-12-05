package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocToken;
import edu.postech.csed332.team3.markdowndoc.MarkdownParser;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static edu.postech.csed332.team3.markdowndoc.explorer.ModifyComment.modifyComment;

public class MdDocElementVisitor extends JavaElementVisitor {
    private static final String JAVA_EXT = ".java";
    private static final String SRC_DIR = "src";
    private static final String HTML_EXT = ".html";
    private static final String HTML = "templates";
    final StringBuilder stringBuilder = new StringBuilder();
    final Deque<DefaultMutableTreeNode> stack;
    FileWriter fileWriter = null;

    MdDocElementVisitor(DefaultMutableTreeNode root) {
        stack = new ArrayDeque<>(Collections.singleton(root));
    }

    @Override
    public void visitPackage(PsiPackage pack) {
        final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(pack);
        stack.getFirst().add(newChild);
        stack.push(newChild);
        Arrays.stream(pack.getSubPackages()).forEach(psiPackage -> psiPackage.accept(this));
        Arrays.stream(pack.getClasses()).forEach(psiClass -> {
            VirtualFile virtualFile = psiClass.getContainingFile().getVirtualFile();
            String path = Objects.requireNonNull(virtualFile.getCanonicalPath())
                    .replace(SRC_DIR, HTML).replace(JAVA_EXT, "") + HTML_EXT;
            File file = new File(path);
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                fileWriter = new FileWriter(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            psiClass.accept(this);
            try {
                fileWriter.write(MarkdownParser.parse(stringBuilder.toString()));
                fileWriter.close();
                stringBuilder.setLength(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        stack.pop();
    }

    @Override
    public void visitClass(PsiClass aClass) {
        final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(aClass);
        stack.getFirst().add(newChild);
        stack.push(newChild);
        if (aClass.getName() != null) {
            stringBuilder.append("Class: ").append(aClass.getName()).append("\n\n");
            stringBuilder.append(appendComment(aClass.getDocComment()));
        }

        Arrays.stream(aClass.getInnerClasses()).forEach(psiClass -> psiClass.accept(this));
        Arrays.stream(aClass.getFields()).forEach(psiField -> psiField.accept(this));
        Arrays.stream(aClass.getMethods()).forEach(psiMethod -> psiMethod.accept(this));
        stack.pop();
    }

    @Override
    public void visitMethod(PsiMethod method) {
        final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(method);
        stack.getFirst().add(newChild);
        stringBuilder.append("Method: ").append(method.getName()).append("\n\n");
        stringBuilder.append(appendComment(method.getDocComment()));
    }

    @Override
    public void visitField(PsiField field) {
        final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(field);
        stack.getFirst().add(newChild);
        stringBuilder.append("Field: ").append(field.getName()).append("\n\n");
        stringBuilder.append(appendComment(field.getDocComment()));
    }

    private String appendComment(PsiDocComment docComment) {
        if (docComment != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("```\n");
            for (PsiElement descriptionElement : docComment.getDescriptionElements())
                if (descriptionElement instanceof PsiDocToken)
                    stringBuilder.append(modifyComment(descriptionElement.getText())).append('\n');
            stringBuilder.append("```\n\n");
            return MarkdownParser.parse(stringBuilder.toString());
        }
        return "";
    }
}
