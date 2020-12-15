package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.io.File;
import java.util.*;

/**
 * A singleton class for creating MD-HTML files.
 * <p/>
 * Use {@link #createProjectTreeModel(Project)} for processing.
 */
public class ProjectModel {

    private static final String HTML = "html";
    private static final Set<PsiClass> allClasses = new HashSet<>();

    private ProjectModel() {
    }

    /**
     * Make directory of html files. <br>
     * The method will also convert documents to files and write them.
     *
     * @return tree model of active project.
     */
    public static TreeModel createProjectTreeModel(Project project) {
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode(project);
        final JavaElementVisitor visitor = new MdDocElementVisitor(root);

        getRootPackages(project).forEach(aPackage -> aPackage.accept(visitor));
        return new DefaultTreeModel(root);
    }

    /**
     * Get the list of all classes in this project, sorted
     *
     * @return the list of all classes
     */
    public static List<PsiClass> getAllClasses() {
        // Sort in alphabetical order
        List<PsiClass> sorted = new ArrayList<>(allClasses);
        sorted.sort(new Comparator<PsiClass>() {
            @Override
            public int compare(PsiClass o1, PsiClass o2) {
                if (o1.getName() == null) {
                    if (o2.getName() == null) return 0;
                    else return 1;
                } else {
                    if (o2.getName() == null) return -1;
                    else return o1.getName().compareToIgnoreCase(o2.getName());
                }
            }
        });

        return sorted;
    }

    public static Set<PsiClass> getAllClassesSet() {
        return allClasses;
    }

    /**
     * Add PsiClass to the set
     *
     * @param psiClass the PsiClass object
     */
    public static void addClass(PsiClass psiClass) {
        allClasses.add(psiClass);
    }

    public static void emptySet() {
        allClasses.clear();
    }

    /**
     * Grab root packages and return the set containing them.
     * <p/>
     * The code snippet from HW06-2.
     */
    private static Set<PsiPackage> getRootPackages(Project project) {
        final Set<PsiPackage> rootPackages = new HashSet<>();
        PsiElementVisitor visitor = new PsiElementVisitor() {
            @Override
            public void visitDirectory(@NotNull PsiDirectory dir) {
                final PsiPackage psiPackage = JavaDirectoryService.getInstance().getPackage(dir);
                if (psiPackage != null && !PackageUtil.isPackageDefault(psiPackage))
                    rootPackages.add(psiPackage);
                else
                    Arrays.stream(dir.getSubdirectories()).forEach(sd -> sd.accept(this));
            }
        };

        ProjectRootManager rootManager = ProjectRootManager.getInstance(project);
        PsiManager psiManager = PsiManager.getInstance(project);
        Arrays.stream(rootManager.getContentSourceRoots())
                .map(psiManager::findDirectory)
                .filter(Objects::nonNull)
                .forEach(dir -> dir.accept(visitor));

        return rootPackages;
    }
}
