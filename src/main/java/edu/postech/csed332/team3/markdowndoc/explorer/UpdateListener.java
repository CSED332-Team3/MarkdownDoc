package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import edu.postech.csed332.team3.markdowndoc.browser.BrowserController;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        PsiFile file = event.getFile();
        if (file != null)
            handleEvent(file);
    }

    /**
     * Removed child handler.<br>
     * If the removed Element is a file and the browser is on the file,
     * then goes back the browser.
     *
     * @param event The event.
     */
    @Override
    public void childRemoved(@NotNull PsiTreeChangeEvent event) {
        PsiElement child = event.getChild();
        if (child instanceof PsiFile)
            handleFileRemoval((PsiFile) child);

        PsiFile file = event.getFile();
        if (file != null)
            handleEvent(file);
    }

    /**
     * Handle the event as refreshing the document.
     *
     * @param file The file which event is happened.
     */
    private void handleEvent(@NotNull PsiFile file) {
        PsiDirectory directory = file.getContainingDirectory();
        PsiPackage psiPackage = JavaDirectoryService.getInstance().getPackage(directory);
        if (psiPackage == null) return;
        Project activeProject = getActiveProject();
        psiPackage.accept(new MdDocElementVisitor(new DefaultMutableTreeNode(activeProject)));
        controller.reload();
    }

    private void handleFileRemoval(PsiFile child) {
        final VirtualFile virtualFile = child.getVirtualFile();
        final Project activeProject = getActiveProject();
        final String relPath = ProjectUtil.calcRelativeToProjectPath(virtualFile, activeProject)
                .replace(File.separator, "/")
                .replace(".../", "");
        final String htmlPath = relPath.replace("src/main", "html")
                .replace("src/test", "html")
                .replace(".java", "");
        if (controller.getURL().contains(htmlPath)) {
            handleEvent(child);
            controller.goHome();
        }
        final Path path = Path.of(activeProject.getBasePath(), htmlPath + ".html");
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
