package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.ex.temp.TempFileSystem;
import com.intellij.openapi.wm.WindowManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ActiveProjectModel {

    private ActiveProjectModel() {
    }

    /**
     * Returns the open project of the current IntelliJ IDEA window
     *
     * @return the project
     */
    @NotNull
    public static Project getActiveProject() {
        for (Project project : ProjectManager.getInstance().getOpenProjects()) {
            Window window = WindowManager.getInstance().suggestParentWindow(project);
            if (window != null && window.isActive())
                return project;
        }
        // if there is no active project, return an arbitrary project (the first)
        return ProjectManager.getInstance().getOpenProjects()[0];
    }

    public static String getProjectDir() {
        VirtualFile projectRoot = ModuleRootManager.getInstance(
                ModuleManager.getInstance(getActiveProject()).getModules()[0]
        ).getContentRoots()[0];
        String path = projectRoot.getCanonicalPath();
        if (projectRoot.getFileSystem() instanceof TempFileSystem && path != null && path.startsWith("/"))
            path = path.replaceFirst("/", "");
        return path;
    }
}
