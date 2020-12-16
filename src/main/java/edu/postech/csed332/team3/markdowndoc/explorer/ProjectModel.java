package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.util.*;

/**
 * A singleton class for creating MD-HTML files.
 * <p/>
 * Use {@link #createProjectTreeModel(Project)} for processing.
 */
public class ProjectModel {

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
     * Grab root packages and return the set containing them.
     * <p/>
     * The code snippet from HW06-2.
     */
    public static Set<PsiPackage> getRootPackages(Project project) {
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
