package edu.postech.csed332.team3.MarkdownDoc;

import com.sun.istack.NotNull;
import javax.swing.*;
import java.awt.*;

/**
 * Swing wrapper for Browser
 */
public class BrowserWindow {
    private final JPanel panel;
    private final Browser browser;

    /**
     * Creates a browser component
     */
    public BrowserWindow() {
        panel = new JPanel(new GridLayout(1, 1));
        browser = new Browser();
        panel.add(browser.getJBCefBrowser().getComponent());
    }

    /**
     * Returns the container of the browser
     *
     * @return JPanel component of the browser
     */
    @NotNull
    public JComponent getContent() {
        return panel;
    }

    /**
     * Returns the Browser class
     *
     * @return the Browser class
     */
    public Browser getBrowser() {
        return browser;
    }
}
