package edu.postech.csed332.team3.MarkdownDoc;

import com.intellij.ui.jcef.JBCefBrowser;
import com.sun.istack.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Swing wrapper for JCEF browser
 */
public class BrowserWindow {
    private final JPanel panel;
    private final JBCefBrowser browser;

    /**
     * Creates a browser component
     */
    public BrowserWindow() {
        panel = new JPanel(new GridLayout(1, 1));
        browser = new JBCefBrowser();
        panel.add(browser.getComponent());
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
     * Loads URL in the browser
     *
     * @param url the URL
     */
    public void loadURL(String url) {
        browser.loadURL(url);
    }

    /**
     * Loads HTML in the browser
     *
     * @param html HTML content
     */
    public void loadHTML(String html) {
        browser.loadHTML(html);
    }

    /**
     * Execute a string of JavaScript code in this frame. The url
     * parameter is the URL where the script in question can be found, if any.
     * The renderer may request this URL to show the developer the source of the
     * error. The line parameter is the base line number to use for error
     * reporting.
     *
     * @param code The code to be executed.
     * @param url The URL where the script in question can be found.
     * @param line line The base line number to use for error reporting.
     */
    public void executeJavaScript(String code, String url, int line) {
        browser.getCefBrowser().executeJavaScript(code, url, line);
    }
}
