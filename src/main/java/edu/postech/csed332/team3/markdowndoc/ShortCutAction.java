package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.keymap.KeymapManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * This class deals with Action about Rearranging members
 * When this action invokes using ShortCut(CTRL + ;),
 * finding context where cursor is located and rearranged document by that context
 */
public class ShortCutAction extends AnAction {

    /**
     * Constructor of ShortCutAction
     */
    public ShortCutAction() {
        super();
        addShortcut();
    }

    /**
     * When Action is invoked, this method is occurred
     * if ShortCut is pressed then 'sort' method is occurred
     */
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        char semicolon;
        InputEvent inputEvent = anActionEvent.getInputEvent();
        if (inputEvent instanceof KeyEvent) {
            KeyEvent keyEvent = (KeyEvent) inputEvent;
            semicolon = keyEvent.getKeyChar();
            if (inputEvent.isControlDown() && semicolon == ';') {
                EditorEx editor = (EditorEx) CommonDataKeys.EDITOR.getData(anActionEvent.getDataContext());
                if (editor != null) {
                    Project project = editor.getProject();
                    if (project != null) {
                        PsiFile psiFile = PsiManager.getInstance(project).findFile(editor.getVirtualFile());
                        RearrangeMembers.callSort(psiFile, editor);
                    }
                }
            }
        }
    }

    /**
     * Add ShortCut CTRL + ;
     */
    private void addShortcut() {
        ShortCutHandler shortCutHandler = new ShortCutHandler(KeyEvent.VK_SEMICOLON, InputEvent.CTRL_DOWN_MASK, false);
        KeymapManager.getInstance().getActiveKeymap().addShortcut("ShortCutAction", shortCutHandler.getShortcut());
    }
}