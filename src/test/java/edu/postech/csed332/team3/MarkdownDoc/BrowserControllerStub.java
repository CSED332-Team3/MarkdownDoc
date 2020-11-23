package edu.postech.csed332.team3.MarkdownDoc;

import java.util.ArrayList;
import java.util.Base64;

/**
 * Stub of BrowserController for testing
 */
public class BrowserControllerStub {

    private final ArrayList<String> URL;
    private String base64URI;
    private int current;

    public BrowserControllerStub() {
        URL = new ArrayList<>();
        base64URI = null;
        current = -1;
    }

    /**
     * Loads URL in the browser
     *
     * @param url the URL
     */
    public void loadURL(String url) {
        current++;
        if (URL.size() > current + 1) {
            URL.set(current, url);
            URL.subList(current + 1, URL.size()).clear();
        } else {
            URL.add(current, url);
        }
    }

    /**
     * Loads HTML in the browser
     *
     * @param html HTML content
     */
    public void loadHTML(String html) {
        current++;
        base64URI = "data:text/html;base64,"
                + Base64.getUrlEncoder().withoutPadding().encodeToString(html.getBytes())
                .replaceAll("-", "+")
                .replaceAll("_", "/");

        if (URL.size() > current + 1) {
            URL.set(current, base64URI);
            URL.subList(current + 1, URL.size()).clear();
        } else {
            URL.add(current, base64URI);
        }
    }

    /**
     * Return the URL
     *
     * @return the URL
     */
    public String getURL() {
        if (current >= 0)
            return URL.get(current);
        else return null;
    }

    /**
     * Go back in history if possible
     */
    public void goBack() {
        if (canGoBack()) current--;
    }

    /**
     * Go forward in history if possible
     */
    public void goForward() {
        if (canGoForward()) current++;
    }

    /**
     * Return whether the browser can go back in history
     *
     * @return true if browser can go back
     */
    public boolean canGoBack() {
        return current > 0;
    }

    /**
     * Return whether the browser can go forward in history
     *
     * @return true if browser can go forward
     */
    public boolean canGoForward() {
        return current < URL.size() - 1;
    }
}
