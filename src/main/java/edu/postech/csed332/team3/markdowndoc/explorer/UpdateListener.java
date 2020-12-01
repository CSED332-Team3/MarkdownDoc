package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import edu.postech.csed332.team3.markdowndoc.BrowserController;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;

import static edu.postech.csed332.team3.markdowndoc.explorer.ActiveProjectModel.getActiveProject;

public class UpdateListener extends PsiTreeChangeAdapter {

    private final BrowserController controller;

    public UpdateListener(BrowserController controller) {
        this.controller = controller;
    }

    @Override
    public void childrenChanged(@NotNull PsiTreeChangeEvent event) {
        Project activeProject = getActiveProject();
        PsiFile file = event.getFile();
        if (file == null) return;
        PsiDirectory directory = file.getContainingDirectory();
        PsiPackage psiPackage = JavaDirectoryService.getInstance().getPackage(directory);
        if (psiPackage == null) return;
        psiPackage.accept(new MdDocElementVisitor(new DefaultMutableTreeNode(activeProject)));

        controller.reload();
    }

    @Override
    public void childAdded(@NotNull PsiTreeChangeEvent event) {
        childrenChanged(event);
    }

    @Override
    public void childRemoved(@NotNull PsiTreeChangeEvent event) {
        childrenChanged(event);
    }

    @Override
    public void childReplaced(@NotNull PsiTreeChangeEvent event) {
        childrenChanged(event);
    }
}
