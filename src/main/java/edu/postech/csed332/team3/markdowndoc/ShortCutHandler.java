package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.actionSystem.KeyboardShortcut;
import com.intellij.openapi.actionSystem.Shortcut;

import javax.swing.*;

/**
 * This class deals with ShortCutHandler
 * Using this class, we can define ShortCut and using it to invoke Action
 */
public class ShortCutHandler {
    private final KeyStroke keyStroke;
    private final Shortcut shortcut;

    /**
     * Constructor of ShortCutHandler
     *
     * @param keyCode      that represents a key action on the keyboard
     * @param modifiers    like CTRL, ALT, SHIFT
     * @param onKeyRelease that represents whether key is released or not
     */
    public ShortCutHandler(int keyCode, int modifiers, boolean onKeyRelease) {
        this.keyStroke = KeyStroke.getKeyStroke(keyCode, modifiers, onKeyRelease);
        this.shortcut = new KeyboardShortcut(keyStroke, null);
    }

    /**
     * Get keyStroke that represents a key action on the keyboard in this class
     *
     * @return keyStroke
     */
    public KeyStroke getKeyStroke() {
        return this.keyStroke;
    }

    /**
     * Get shortcut in this class
     *
     * @return shortcut
     */
    public Shortcut getShortcut() {
        return this.shortcut;
    }
}