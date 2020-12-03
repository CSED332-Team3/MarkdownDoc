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
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;

import static edu.postech.csed332.team3.markdowndoc.explorer.ModifyComment.modifyComment;

public class MdDocElementVisitor extends JavaElementVisitor {
    private static final String JAVA_EXT = ".java";
    private static final String SRC_DIR = "src";
    private static final String HTML_EXT = ".html";
    private static final String HTML = "html";
    final StringBuilder stringBuilder = new StringBuilder();
    final Deque<DefaultMutableTreeNode> stack;
    FileWriter fileWriter = null;

    MdDocElementVisitor(DefaultMutableTreeNode root) {
        stack = new ArrayDeque<>(Collections.singleton(root));
    }

    /**
     * Visits a package.
     * <p/>
     * Note that a file is created here; when the visitor visits a class from the package.
     *
     * @param aPackage Target package.
     */
    @Override
    public void visitPackage(PsiPackage aPackage) {
        final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(aPackage);
        stack.getFirst().add(newChild);
        stack.push(newChild);
        Arrays.stream(aPackage.getSubPackages()).forEach(psiPackage -> psiPackage.accept(this));
        Arrays.stream(aPackage.getClasses()).forEach(psiClass -> {
            final String canonicalPath = psiClass.getContainingFile().getVirtualFile().getCanonicalPath();
            if (canonicalPath == null) return;
            String path = canonicalPath.replace(SRC_DIR, HTML).replace(JAVA_EXT, HTML_EXT);
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

    /**
     * Visit a class.
     *
     * @param aClass Target class.
     */
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

    /**
     * Visit a method.
     *
     * @param method Target method.
     */
    @Override
    public void visitMethod(PsiMethod method) {
        final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(method);
        stack.getFirst().add(newChild);
        stringBuilder.append("Method: ").append(method.getName()).append("\n\n");
        stringBuilder.append(appendComment(method.getDocComment()));
    }

    /**
     * Visit a field.
     *
     * @param field Target field.
     */
    @Override
    public void visitField(PsiField field) {
        final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(field);
        stack.getFirst().add(newChild);
        stringBuilder.append("Field: ").append(field.getName()).append("\n\n");
        stringBuilder.append(appendComment(field.getDocComment()));
    }

    /**
     * Apply code markdown grammar to visualize doc comment.
     *
     * @param docComment comment for editing.
     * @return edited doc comment.
     */
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
