package edu.postech.csed332.team3.markdowndoc;

import org.junit.Test;
import com.intellij.openapi.actionSystem.KeyboardShortcut;
import com.intellij.openapi.actionSystem.Shortcut;

import javax.swing.*;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_SEMICOLON;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ShortCutHandlerTest {
    @Test
    public void TestGetKeyStroke() {
        ShortCutHandler handler = new ShortCutHandler(VK_SEMICOLON, CTRL_DOWN_MASK, false);
        assertEquals(handler.GetKeyStroke(), KeyStroke.getKeyStroke(VK_SEMICOLON, CTRL_DOWN_MASK, false));
    }

    @Test
    public void TestGetShortcut() {
        ShortCutHandler handler = new ShortCutHandler(VK_SEMICOLON, CTRL_DOWN_MASK, false);
        assertEquals(handler.GetShortcut(), new KeyboardShortcut(handler.GetKeyStroke(), null));
    }

    @Test
    public void TestGetKeyCode() {
        ShortCutHandler handler = new ShortCutHandler(VK_SEMICOLON, CTRL_DOWN_MASK, false);
        assertEquals(handler.GetKeyCode(), VK_SEMICOLON);
    }

    @Test
    public void TestGetModifiers() {
        ShortCutHandler handler = new ShortCutHandler(VK_SEMICOLON, CTRL_DOWN_MASK, false);
        assertEquals(handler.GetModifiers(), CTRL_DOWN_MASK);
    }

    @Test
    public void TestGetOnKeyRelease() {
        ShortCutHandler handler = new ShortCutHandler(VK_SEMICOLON, CTRL_DOWN_MASK, false);
        assertFalse(handler.GetOnKeyRelease());
    }
}

