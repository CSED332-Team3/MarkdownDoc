package edu.postech.csed332.team3.MarkdownDoc;

import com.intellij.openapi.externalSystem.service.execution.NotSupportedException;
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefJSQuery;
import org.cef.browser.CefBrowser;

public class Browser {

    private final JBCefBrowser browser;
    private final CefBrowser cefBrowser;

    public Browser() {
        if (!JBCefApp.isSupported()) {
            throw new NotSupportedException("This IDE version is not supported.");
        }

        browser = new JBCefBrowser();
        cefBrowser = browser.getCefBrowser();
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
        return browser;
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
