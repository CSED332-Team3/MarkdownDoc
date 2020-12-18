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
    private final int keyCode;
    private final int modifiers;
    private final boolean onKeyRelease;

    /**
     * Constructor of ShortCutHandler
     *
     * @param keyCode      that represents a key action on the keyboard
     * @param modifiers    like CTRL, ALT, SHIFT
     * @param onKeyRelease that represents whether key is released or not
     */
    public ShortCutHandler(int keyCode, int modifiers, boolean onKeyRelease) {
        this.keyCode = keyCode;
        this.modifiers = modifiers;
        this.onKeyRelease = onKeyRelease;
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

    /**
     * Get keyCode that represents of character number in this class
     *
     * @return keyCode
     */
    public int getKeyCode() {
        return this.keyCode;
    }

    /**
     * Get modifiers like CTRL, ALT, SHIFT in this class
     *
     * @return modifiers
     */
    public int getModifiers() {
        return this.modifiers;
    }

    /**
     * Get onKeyRelease that represents whether key is released or not in this class
     *
     * @return onKeyRelease
     */
    public boolean getOnKeyRelease() {
        return this.onKeyRelease;
    }
}