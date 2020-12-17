package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;

import java.util.Objects;

/**
 * This class gives method that rearrange members about TYPE
 * When ShortCut is pressed, method of this class 'CallSort' is called
 */
public class RearrangeMembers {
    private static BrowserController controller;

    private RearrangeMembers() {
    }

    /**
     * Set controller.
     *
     * @param controller that Control UI browser to use sort
     */
    public static void setController(BrowserController controller) {
        RearrangeMembers.controller = controller;
    }

    /**
     * Given PsiFile and editor, find context of location of cursor
     */
    private static PsiElement findContext(PsiFile current, Editor editor) {
        if (current != null) {
            CaretModel caretModel = editor.getCaretModel();
            return Objects.requireNonNull(current.findElementAt(caretModel.getOffset())).getContext();
        }
        return null;
    }

    /**
     * Call sort in BrowserController to RearrangeMembers according to Type
     *
     * @param current PsiFile that cursor is located
     * @param editor  Editor that cursor is located
     */
    public static void callSort(PsiFile current, Editor editor) {
        PsiElement context = findContext(current, editor);
        if (context != null) {
            if (context instanceof PsiField)
                controller.sort(((PsiField) context).getType());
            else if (context instanceof PsiMethod)
                controller.sort(Objects.requireNonNull(((PsiMethod) context).getReturnType()));
            else if (context instanceof PsiClass)
                controller.sort(((PsiClass) context).getName());
            else if (context instanceof PsiReferenceExpression)
                controller.sort(Objects.requireNonNull(((PsiReferenceExpression) context).getType()));
            else if (context instanceof PsiParameter)
                controller.sort(((PsiParameter) context).getType());
            else if (context instanceof PsiTypeElement)
                controller.sort(((PsiTypeElement) context).getType());
            else if (context instanceof PsiJavaCodeReferenceElement)
                controller.sort(context.getText());
        }
    }


}
