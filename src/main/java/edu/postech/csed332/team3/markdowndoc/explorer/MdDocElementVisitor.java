package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import com.intellij.openapi.vfs.ex.temp.TempFileSystem;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.util.*;

import static edu.postech.csed332.team3.markdowndoc.explorer.ActiveProjectModel.getActiveProject;

public class MdDocElementVisitor extends JavaElementVisitor {
    private static final String JAVA_EXT = ".java";
    private static final String SRC_MAIN_DIR = "src/main";
    private static final String SRC_TEST_DIR = "src/test";
    private static final String HTML_EXT = ".html";
    private static final String HTML = "html";
    private static final Set<PsiClass> allClasses = new HashSet<>();
    private final Deque<DefaultMutableTreeNode> stack;
    private FileManager fileManager;
    private boolean first = true;
    private boolean isParentPkg = true;
    private boolean isSubClass = false;

    MdDocElementVisitor(DefaultMutableTreeNode root) {
        stack = new ArrayDeque<>(Collections.singleton(root));
    }

    /**
     * Get the list of all classes in this project, sorted
     *
     * @return the list of all classes
     */
    public static List<PsiClass> getAllClasses() {
        // Sort in alphabetical order
        List<PsiClass> sorted = new ArrayList<>(allClasses);
        sorted.sort((o1, o2) -> {
            if (o1.getName() == null) {
                if (o2.getName() == null) return 0;
                else return 1;
            } else {
                if (o2.getName() == null) return -1;
                else return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        return sorted;
    }

    /**
     * Get the set of all classes in this project
     *
     * @return the set of all classes
     */
    public static Set<PsiClass> getAllClassesSet() {
        return allClasses;
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
        if (isParentPkg) {
            // Empty classes set on re-search
            allClasses.clear();
            isParentPkg = false;
        }

        final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(aPackage);
        stack.getFirst().add(newChild);
        stack.push(newChild);

        // Run visit twice since all classes must be visited
        // in order to build the index
        for (int i = 0; i < 2; i++) {
            Arrays.stream(aPackage.getSubPackages()).forEach(psiPackage -> psiPackage.accept(this));
            Arrays.stream(aPackage.getClasses()).forEach(psiClass -> {
                VirtualFile virtualFile = psiClass.getContainingFile().getVirtualFile();
                final String canonicalPath = virtualFile.getCanonicalPath();
                VirtualFileSystem fileSystem = virtualFile.getFileSystem();
                if (canonicalPath == null) return;

                String path = canonicalPath.replace(SRC_MAIN_DIR, HTML)
                        .replace(SRC_TEST_DIR, HTML)
                        .replace(JAVA_EXT, HTML_EXT);
                if (fileSystem instanceof TempFileSystem && path.startsWith("/"))
                    path = path.replaceFirst("/", "");

                fileManager = new FileManager(path);
                first = true;
                isSubClass = false;
                psiClass.accept(this);
                fileManager.close(psiClass);
            });
        }
        stack.pop();

        // Create index.html last
        if (stack.size() == 1) createIndex();
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

        // Add this class to the set of all classes
        if (!isSubClass)
            allClasses.add(aClass);

        // Write to file
        if (first) {
            fileManager.writeFirst(aClass);
            first = false;
        } else
            fileManager.write(aClass);

        isSubClass = true;
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

    /**
     * Create index.html
     */
    private void createIndex() {
        @NotNull VirtualFile projectRoot = ModuleRootManager.getInstance(
                ModuleManager.getInstance(getActiveProject()).getModules()[0]
        ).getContentRoots()[0];
        String path = projectRoot.getCanonicalPath()
                + File.separator + "html"
                + File.separator + "index.html";

        if (projectRoot.getFileSystem() instanceof TempFileSystem && path.startsWith("/"))
            path = path.replaceFirst("/", "");

        FileManager fm = new FileManager(path);
        fm.close(null);
    }

    protected Collection<DefaultMutableTreeNode> getStack() {
        return Collections.unmodifiableCollection(stack);
    }
}
