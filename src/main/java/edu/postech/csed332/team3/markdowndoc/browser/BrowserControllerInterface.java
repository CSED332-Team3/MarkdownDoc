package edu.postech.csed332.team3.markdowndoc.browser;

/**
 * Interface for BrowserController
 */
public interface BrowserControllerInterface {

    /**
     * Loads URL in the browser
     *
     * @param url the URL
     */
    void loadURL(String url);

    /**
     * Loads HTML in the browser
     *
     * @param html HTML content
     */
    void loadHTML(String html);

    /**
     * Return the URL
     *
     * @return the URL
     */
    String getURL();

    /**
     * Go back in history if possible
     */
    void goBack();

    /**
     * Go forward in history if possible
     */
    void goForward();

    /**
     * Go to index.html
     */
    void goHome();

    /**
     * Return whether the browser can go back in history
     *
     * @return true if browser can go back
     */
    boolean canGoBack();

    /**
     * Return whether the browser can go forward in history
     *
     * @return true if browser can go forward
     */
    boolean canGoForward();

    /**
     * Reload the current page
     */
    void reload();
}
