package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.ui.treeStructure.Tree;
import com.sun.istack.NotNull;
import com.thaiopensource.xml.dtd.om.Def;
import edu.postech.csed332.team3.markdowndoc.explorer.ActiveProjectModel;
import edu.postech.csed332.team3.markdowndoc.explorer.ProjectModel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import java.security.InvalidParameterException;

public class ProjectNavigator {

    private final BrowserController controller;
    private final String projectPath;

    private TreeModel model;
    private DefaultMutableTreeNode root;
    private final Project project;
    private String qualifiedClassName;
    private String elementName;
    private String elementType;

    public ProjectNavigator(BrowserController controller, String projectPath) {
        this.controller = controller;
        this.projectPath = projectPath;
        project = ActiveProjectModel.getActiveProject();
        loadModel();
    }

    private void loadModel() {
        model = ProjectModel.createProjectTreeModel();
        root = (DefaultMutableTreeNode) (model.getRoot());
    }

    // Input is of form m-method;com.example.package.Class
    private void parseInput(String input) {
        String[] part = input.split(";");
        if (part.length != 2) throw new InvalidParameterException("Invalid parameter");

        elementName = part[0].substring(2);
        elementType = part[0].substring(0, 1);
        qualifiedClassName = part[1];
    }

    // Find element using qualifiedClassName, elementName, elementType
    private PsiElement find() {
        // Get the class element
        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(qualifiedClassName, GlobalSearchScope.projectScope(project));
        DefaultMutableTreeNode classNode = find(root, psiClass);

        for (int i = 0; i < classNode.getChildCount(); i++) {
            // TODO: Account for overloaded names

            if (elementType.equals("m") && ((DefaultMutableTreeNode) classNode.getChildAt(i)).getUserObject() instanceof PsiMethod) {
                PsiMethod node = (PsiMethod) ((DefaultMutableTreeNode) classNode.getChildAt(i)).getUserObject();
                if (node.getName().equals(elementName)) return node;

            } else if (elementType.equals("f") && ((DefaultMutableTreeNode) classNode.getChildAt(i)).getUserObject() instanceof PsiField) {
                PsiField node = (PsiField) ((DefaultMutableTreeNode) classNode.getChildAt(i)).getUserObject();
                if (node.getName().equals(elementName)) return node;
            }
        }

        return null;
    }

    // Find PsiElement in tree
    private DefaultMutableTreeNode find(@NotNull DefaultMutableTreeNode node, PsiElement target){
        if (node.getUserObject() == target){
            return node;
        }
        DefaultMutableTreeNode n = null;
        for (int i = 0; i < node.getChildCount(); i++) {
            n = find((DefaultMutableTreeNode) node.getChildAt(i), target);
            if (n != null) break;
        }
        return n;
    }

    public void navigateToClass(String input) {

    }

    public void navigateToMethodField(String input) {
        loadModel();
        parseInput(input);
        PsiElement element = find();

        SwingUtilities.invokeLater(() -> {
            if (element instanceof PsiMethod) {
                ((PsiMethod) element).navigate(true);
            } else if (element instanceof PsiField) {
                ((PsiField) element).navigate(true);
            }
        });
    }
}
