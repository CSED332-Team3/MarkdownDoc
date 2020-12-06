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

    /**
     * Changed child handler.
     *
     * @param event The event.
     */
    @Override
    public void childrenChanged(@NotNull PsiTreeChangeEvent event) {
        LoggerUtil.info("childrenChanged");
        handleEvent(event);
    }

    /**
     * Added child handler.
     *
     * @param event The event.
     */
    @Override
    public void childAdded(@NotNull PsiTreeChangeEvent event) {
        LoggerUtil.info("childAdded");
        handleEvent(event);
    }

    /**
     * Removed child handler.
     *
     * If the removed Element is a file and the browser is on the file,
     * then goes back the browser.
     *
     * @param event The event.
     */
    @Override
    public void childRemoved(@NotNull PsiTreeChangeEvent event) {
        LoggerUtil.info("childRemoved");

        PsiElement child = event.getChild();
        if (child instanceof PsiFile) {
            String childPath = ((PsiFile) child).getVirtualFile().getCanonicalPath();
            String url = controller.getURL();
            if (childPath == null) return;
            if (url.contains(childPath.replace("src", "html").replace(".java", "")))
                controller.goBack();
        }

        handleEvent(event);
    }

    /**
     * Replaced child handler.
     *
     * @param event The event.
     */
    @Override
    public void childReplaced(@NotNull PsiTreeChangeEvent event) {
        LoggerUtil.info("childReplaced");
        handleEvent(event);
    }

    /**
     * Handle the event as refreshing the document.
     *
     * @param event The event.
     */
    private void handleEvent(@NotNull PsiTreeChangeEvent event) {
        Project activeProject = getActiveProject();
        PsiFile file = event.getFile();
        if (file == null) return;
        PsiDirectory directory = file.getContainingDirectory();
        PsiPackage psiPackage = JavaDirectoryService.getInstance().getPackage(directory);
        if (psiPackage == null) return;
        psiPackage.accept(new MdDocElementVisitor(new DefaultMutableTreeNode(activeProject)));
        controller.reload();
    }
}
