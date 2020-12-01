package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import edu.postech.csed332.team3.markdowndoc.BrowserController;
import edu.postech.csed332.team3.markdowndoc.util.LoggerUtil;
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
        LoggerUtil.info("child added");
        childrenChanged(event);
    }

    @Override
    public void childRemoved(@NotNull PsiTreeChangeEvent event) {
        LoggerUtil.info("child removed");
        PsiElement child = event.getChild();
        if (child instanceof PsiFile) {
            String childPath = ((PsiFile) child).getVirtualFile().getCanonicalPath();
            String url = controller.getURL();
            if (childPath == null) return;
            if (url.contains(childPath.replace("src", "html").replace(".java", "")))
                controller.goBack();
        }
        childrenChanged(event);
    }

    @Override
    public void childReplaced(@NotNull PsiTreeChangeEvent event) {
        LoggerUtil.info("child replaced");
        childrenChanged(event);
    }

    @Override
    public void childMoved(@NotNull PsiTreeChangeEvent event) {
        LoggerUtil.info("child moved");
    }

    @Override
    public void propertyChanged(@NotNull PsiTreeChangeEvent event) {
        LoggerUtil.info("property changed");
    }
}
