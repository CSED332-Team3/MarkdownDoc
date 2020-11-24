package edu.postech.csed332.team3.MarkdownDoc;

import com.intellij.psi.*;

public interface ProjectNavigatorInterface {
    /**
     * Check if a class with a given name exists
     *
     * @param name the class name
     * @return true if the class exists
     */
    Boolean classExists(String name);

    PsiClass getClass(String name);

    /**
     * Navigate to the class
     *
     * @param name the class name
     */
    void navigateToClass(String name);

    /**
     * Check if a method with a given name exists in a class
     *
     * @param methodName the method name
     * @param className the class name
     * @return true if the class exists
     */
    Boolean methodExists(String methodName, String className);

    /**
     * Navigate to the method
     *
     * @param methodName the method name
     * @param className the class name
     */
    void navigateToMethod(String methodName, String className);

    /**
     * Check if a field with a given name exists in a class
     *
     * @param fieldName the field name
     * @param className the class name
     * @return true if the class exists
     */
    Boolean fieldExists(String fieldName, String className);

    /**
     * Navigate to the field
     *
     * @param fieldName the field name
     * @param className the class name
     */
    void navigateToField(String fieldName, String className);
}
