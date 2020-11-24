package edu.postech.csed332.team3.MarkdownDoc;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;

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
}
