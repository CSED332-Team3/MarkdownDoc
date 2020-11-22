package edu.postech.csed332.team3.MarkdownDoc;

import com.intellij.openapi.externalSystem.service.execution.NotSupportedException;
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import org.cef.browser.CefBrowser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrowserController {

    private final JBCefBrowser model;
    private BrowserView view;

    private final CefBrowser cefBrowser;

    /**
     * Create an empty browser controller instance
     */
    public BrowserController(BrowserView view) {
        if (!JBCefApp.isSupported()) {
            throw new NotSupportedException("This IDE version is not supported.");
        }

        model = new JBCefBrowser();
        this.view = view;
        cefBrowser = model.getCefBrowser();

        // Add the browser JComponent to the view
        addComponents();
    }

    private void addComponents() {
        JPanel buttons = new JPanel(new GridLayout(1, 2));
        JButton backButton = new JButton();
        JButton forwardButton = new JButton();

        // Set text and actions
        backButton.setText("<-");
        forwardButton.setText("->");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        forwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttons.add(backButton);
        buttons.add(forwardButton);

        // Finalize the view
        view.addComponent(model.getComponent(), "Center"); // Add browser
        view.addComponent(buttons, "South"); // Add buttons
    }

    /**
     * Update the view (button status, etc.)
     */
    public void updateView() {
        if (canGoBack()) {

        }
    }

    /**
     * Loads URL in the browser
     *
     * @param url the URL
     */
    public void loadURL(String url) {
        model.loadURL(url);
    }

    /**
     * Loads HTML in the browser
     *
     * @param html HTML content
     */
    public void loadHTML(String html) {
        model.loadHTML(html);
    }

    /**
     * Return the URL
     *
     * @return the URL
     */
    public String getURL() {
        return cefBrowser.getURL();
    }

    /**
     * Go back in history if possible
     */
    public void goBack() {
        if (cefBrowser.canGoBack()) cefBrowser.goBack();
    }

    /**
     * Go forward in history if possible
     */
    public void goForward() {
        if (cefBrowser.canGoForward()) cefBrowser.goForward();
    }

    /**
     * Return whether the browser can go back in history
     *
     * @return true if browser can go back
     */
    public boolean canGoBack() {
        return cefBrowser.canGoBack();
    }

    /**
     * Return whether the browser can go forward in history
     *
     * @return true if browser can go forward
     */
    public boolean canGoForward() {
        return cefBrowser.canGoForward();
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
        cefBrowser.executeJavaScript(code, url, line);
    }

    /**
     * Return the JBCefBrowser instance
     * This is a JetBrains wrapper for CefBrowser
     *
     * @return the JBCefBrowser instance
     */
    public JBCefBrowser getJBCefBrowser() {
        return model;
    }

    /**
     * Return the CefBrowser instance
     *
     * @return the CefBrowser instance
     */
    public CefBrowser getCefBrowser() {
        return cefBrowser;
    }
}
