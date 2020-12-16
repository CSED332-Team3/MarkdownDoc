package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.keymap.KeymapManager;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

/**
 * This class deals with Action about Rearranging members
 * When this action invokes using ShortCut(CTRL + ;),
 * finding context where cursor is located and rearranged document by that context
 */
public class ShortCutAction extends AnAction {
    private ArrayList<ShortCutHandler> keyStrokes;

    /**
     * Constructor of ShortCutAction
     */
    public ShortCutAction() {
        super();
        this.keyStrokes = new ArrayList<>();
        this.AddShortcut(VK_SEMICOLON, CTRL_DOWN_MASK, false);
    }

    /**
     * When Action is invoked, this method is occurred
     */
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Character semicolon;
        InputEvent inputEvent = anActionEvent.getInputEvent();
        if (inputEvent instanceof KeyEvent) {
            KeyEvent keyEvent = (KeyEvent) inputEvent;
            semicolon = keyEvent.getKeyChar();
            if (inputEvent.isControlDown() && semicolon == ';') {
                //TODO : Fill action - Press Sort Button according to context

            }
        }
    }

    /**
     * Add ShortCut given parameter
     * @param keyCode
     * @param modifiers
     * @param onKeyRelease
     */
    private void AddShortcut(int keyCode, int modifiers, boolean onKeyRelease) {
        ShortCutHandler shortCutHandler = new ShortCutHandler(keyCode, modifiers, onKeyRelease);
        this.keyStrokes.add(shortCutHandler);
        KeymapManager.getInstance().getActiveKeymap().addShortcut("ShortCutAction", shortCutHandler.GetShortcut());
    }
}