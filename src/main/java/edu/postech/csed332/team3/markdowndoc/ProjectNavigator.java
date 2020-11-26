package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ProjectNavigator implements ProjectNavigatorInterface {
    @Override
    public PsiClass getClass(String name) {
        return null;
    }

    @Override
    public void navigateToClass(PsiClass psiClass) {

    }

    @Override
    public PsiMethod getMethod(String methodName, String className) {
        return null;
    }

    @Override
    public void navigateToMethod(PsiMethod psiMethod) {

    }

    @Override
    public PsiField getField(String fieldName, String className) {
        return null;
    }

    @Override
    public void navigateToField(PsiField psiField) {

    }

    @NotNull
    public Project getActiveProject() {
        for (Project project : ProjectManager.getInstance().getOpenProjects()) {
            Window window = WindowManager.getInstance().suggestParentWindow(project);
            if (window != null && window.isActive())
                return project;
        }
        // if there is no active project, return an arbitrary project (the first)
        return ProjectManager.getInstance().getOpenProjects()[0];
    }
}
