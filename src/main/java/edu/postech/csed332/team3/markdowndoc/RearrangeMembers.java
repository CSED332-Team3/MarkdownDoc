package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import java.util.Objects;
import java.util.Optional;

/**
 * This class gives method that rearrange members about TYPE
 * When ShortCut is pressed, method of this class 'CallSort' is called
 */
public class RearrangeMembers {
    private static BrowserController controller;

    /**
     * Constructor of RearrangeMembers
     * @param controller that Control UI browser to use sort
     */
    public RearrangeMembers(BrowserController controller) {
        this.controller = controller;
    }

    /**
     * Given PsiFile and editor, find context of location of cursor
     */
    private static Optional FindContext(PsiFile current, Editor editor) {
        if (current != null) {
            CaretModel caretModel = editor.getCaretModel();
            PsiElement context = Objects.requireNonNull(current.findElementAt(caretModel.getOffset())).getContext();
            return Optional.of(context);
        }
        return Optional.empty();
    }

    /**
     * Call sort in BrowserController to RearrangeMembers according to Type
     * @param current PsiFile that cursor is located
     * @param editor Editor that cursor is located
     */
    public static void CallSort(PsiFile current, Editor editor) {
        Optional context = FindContext(current, editor);
        if (context.isPresent()) {
            PsiElement context_ = ((PsiElement) context.get());
            if (context_ instanceof PsiField)
                controller.sort(((PsiField) context_).getType());
            else if (context_ instanceof PsiMethod)
                controller.sort(Objects.requireNonNull(((PsiMethod) context_).getReturnType()));
            else if (context_ instanceof PsiClass)
                controller.sort(((PsiClass) context_).getName());
            else if (context_ instanceof PsiReferenceExpression)
                controller.sort(Objects.requireNonNull(((PsiReferenceExpression) context_).getType()));
            else if (context_ instanceof PsiParameter)
                controller.sort(((PsiParameter) context_).getType());
            else if (context_ instanceof PsiTypeElement)
                controller.sort(((PsiTypeElement) context_).getType());
            else if (context_ instanceof PsiJavaCodeReferenceElement)
                controller.sort(context_.getText());
        }
    }


}
