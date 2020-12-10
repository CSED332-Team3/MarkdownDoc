package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.*;
import com.intellij.ui.treeStructure.Tree;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static edu.postech.csed332.team3.markdowndoc.explorer.ActiveProjectModel.getActiveProject;

/**
 * A singleton class for creating MD-HTML files.
 * <p/>
 * Use {@link #createProjectTreeModel(String)} for processing.
 */
public class ProjectModel {

    private static final String HTML = "html";

    private ProjectModel() {
    }

    /**
     * Make directory of html files.
     * <p/>
     * The method will also convert documents to files and write them.
     *
     * @param path Root path of the target(mainly opened one) project.
     * @return tree model of active project.
     */
    public static TreeModel createProjectTreeModel(String path) {
        // Make html directory
        File folder = new File(path, HTML);
        if (!folder.mkdirs())
            return null;

        return createProjectTreeModel();
    }

    public static TreeModel createProjectTreeModel() {
        Project activeProject = getActiveProject();
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode(activeProject);

        final JavaElementVisitor visitor = new MdDocElementVisitor(root);

        getRootPackages(activeProject).forEach(aPackage -> aPackage.accept(visitor));
        return new DefaultTreeModel(root);
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
