package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.psi.*;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;

public class MdDocElementVisitor extends JavaElementVisitor {
    private static final String JAVA_EXT = ".java";
    private static final String SRC_DIR = "src";
    private static final String HTML_EXT = ".html";
    private static final String HTML = "html";
    private final Deque<DefaultMutableTreeNode> stack;
    private FileManager fileManager;
    private boolean first = true;

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

            fileManager = new FileManager(path);
            first = true;
            psiClass.accept(this);
            fileManager.close();
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

        if (first) {
            fileManager.writeFirst(aClass);
            first = false;
        }
        else
            fileManager.write(aClass);

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
        visitLast(method);
    }

    /**
     * Visit a field.
     *
     * @param field Target field.
     */
    @Override
    public void visitField(PsiField field) {
        visitLast(field);
    }

    private void visitLast(PsiNamedElement element) {
        final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(element);
        stack.getFirst().add(newChild);
        fileManager.write(element);
    }
}
