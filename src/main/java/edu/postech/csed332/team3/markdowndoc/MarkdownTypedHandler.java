package edu.postech.csed332.team3.markdowndoc;

import com.intellij.codeInsight.completion.CodeCompletionHandlerBase;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.editorActions.CompletionAutoPopupHandler;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * We want to use Markdown grammar completion inside of comments,
 * so we use custom parameter(&) that triggered completion popup
 * Yet, this class doesn't implement perfect
 */
public class MarkdownTypedHandler extends CompletionAutoPopupHandler {

    /**
     * check if input is triggered character
     * @param input to be checked
     */
    private boolean isParameter(char input){
        return input == '&';
    }

    /**
     * Override checkAutoPopup to insert DoAutoPopup
     */
    @Override
    public @NotNull Result checkAutoPopup(char charTyped, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        DoAutoPopup(charTyped);
        return super.checkAutoPopup(charTyped, project, editor, file);
    }

    /**
     * Override checkAutoPopup to insert DoAutoPopup
     */
    @Override
    public Result charTyped(char c, final Project project, @NotNull final Editor editor, @NotNull PsiFile file) {
        DoAutoPopup(c);
        return super.charTyped(c, project, editor, file);
    }

    /**
     * When we type custom parameter like &, Completion Pop-upped automatically
     * @param charTyped to be checked
     */
    private Result DoAutoPopup(char charTyped){
        if (isParameter(charTyped)) {
            CompletionAutoPopupHandler.runLaterWithCommitted(project, editor.getDocument(), new Runnable() {
                @Override
                public void run() {
                    if (PsiDocumentManager.getInstance(project).isCommitted(editor.getDocument())) {
                        new CodeCompletionHandlerBase(CompletionType.BASIC).invokeCompletion(project, editor, 1);
                    }
                }
            });
            return Result.STOP;
        }
    }

}