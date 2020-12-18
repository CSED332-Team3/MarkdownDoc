package edu.postech.csed332.team3.markdowndoc;

import com.intellij.codeInsight.completion.CodeCompletionHandlerBase;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.application.AppUIExecutor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * We want to use Markdown grammar completion inside of comments,
 * so we use custom parameter(/) that triggered completion popup
 */
public class MarkdownTypedHandler extends TypedHandlerDelegate {

    /**
     * check if input is triggered character
     *
     * @param input to be checked
     */
    private boolean isParameter(char input) {
        return input == '/';
    }

    /**
     * check if current location is in JavaDoc context
     */
    private boolean isJavaDocComment(Editor editor, PsiFile file) {
        return Objects.requireNonNull(Objects.requireNonNull(file.findElementAt(editor.getCaretModel().getOffset())).getContext()).toString().equals("PsiDocComment");
    }

    /**
     * If the specified character triggers auto-popup, schedules the auto-popup appearance
     * Override checkAutoPopup to insert DoAutoPopup
     */
    @Override
    public @NotNull Result checkAutoPopup(char charTyped, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        if (isParameter(charTyped) && isJavaDocComment(editor, file)) {
            doAutoPopup(project, editor);
            return Result.STOP;
        }
        return super.checkAutoPopup(charTyped, project, editor, file);
    }

    /**
     * Called after the specified character typed by the user has been inserted in the editor.
     * Override checkAutoPopup to insert DoAutoPopup
     */
    @Override
    public @NotNull Result charTyped(char c, final @NotNull Project project, @NotNull final Editor editor, @NotNull PsiFile file) {
        if (isParameter(c) && isJavaDocComment(editor, file)) {
            doAutoPopup(project, editor);
            return Result.STOP;
        }
        return super.charTyped(c, project, editor, file);
    }

    /**
     * When we type custom parameter like /, Completion Pop-upped automatically
     */
    private void doAutoPopup(final Project project, @NotNull final Editor editor) {
        AppUIExecutor.onUiThread().later().withDocumentsCommitted(project).execute(() -> {
            if (PsiDocumentManager.getInstance(project).isCommitted(editor.getDocument())) {
                new CodeCompletionHandlerBase(CompletionType.BASIC).invokeCompletion(project, editor, 1);
            }
        });
    }

}