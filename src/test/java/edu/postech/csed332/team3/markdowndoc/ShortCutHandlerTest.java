package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.actionSystem.KeyboardShortcut;
import org.junit.Test;

import javax.swing.*;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_SEMICOLON;
import static org.junit.Assert.assertEquals;

public class ShortCutHandlerTest {
    @Test
    public void TestGetKeyStroke() {
        ShortCutHandler handler = new ShortCutHandler(VK_SEMICOLON, CTRL_DOWN_MASK, false);
        assertEquals(handler.getKeyStroke(), KeyStroke.getKeyStroke(VK_SEMICOLON, CTRL_DOWN_MASK, false));
    }

    @Test
    public void TestGetShortcut() {
        ShortCutHandler handler = new ShortCutHandler(VK_SEMICOLON, CTRL_DOWN_MASK, false);
        assertEquals(handler.getShortcut(), new KeyboardShortcut(handler.getKeyStroke(), null));
    }
}

