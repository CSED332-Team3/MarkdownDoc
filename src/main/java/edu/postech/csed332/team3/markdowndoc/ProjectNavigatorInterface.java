package edu.postech.csed332.team3.markdowndoc;

import com.intellij.psi.*;

public interface ProjectNavigatorInterface {
    /**
     * Get the class instance with a given name
     *
     * @param name the class name
     * @return the PsiClass instance
     */
    PsiClass getClass(String name);

    /**
     * Navigate to the class
     *
     * @param psiClass the PsiClass instance
     */
    void navigateToClass(PsiClass psiClass);

    /**
     * Get the method with a given name exists in a class
     *
     * @param methodName the method name
     * @param className the class name
     * @return the PsiMethod instance
     */
    PsiMethod getMethod(String methodName, String className);

    /**
     * Navigate to the method
     *
     * @param psiMethod the PsiMethod instance
     */
    void navigateToMethod(PsiMethod psiMethod);

    /**
     * Check if a field with a given name exists in a class
     *
     * @param fieldName the field name
     * @param className the class name
     * @return the PsiField instance
     */
    PsiField getField(String fieldName, String className);

    /**
     * Navigate to the field
     *
     * @param psiField the PsiField instance
     */
    void navigateToField(PsiField psiField);
}
