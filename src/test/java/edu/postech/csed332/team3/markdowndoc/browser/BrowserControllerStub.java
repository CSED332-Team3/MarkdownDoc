package edu.postech.csed332.team3.markdowndoc.browser;

import edu.postech.csed332.team3.markdowndoc.browser.BrowserControllerInterface;

import java.util.ArrayList;
import java.util.Base64;

/**
 * Stub of BrowserController for testing
 */
public class BrowserControllerStub implements BrowserControllerInterface {

    private final ArrayList<String> URL;
    private String base64URI;
    private int current;

    public BrowserControllerStub() {
        URL = new ArrayList<>();
        base64URI = null;
        current = -1;
    }

    @Override
    public void loadURL(String url) {
        current++;
        if (URL.size() > current + 1) {
            URL.set(current, url);
            URL.subList(current + 1, URL.size()).clear();
        } else {
            URL.add(current, url);
        }
    }

    @Override
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

    @Override
    public String getURL() {
        if (current >= 0)
            return URL.get(current);
        else return null;
    }

    @Override
    public void goBack() {
        if (canGoBack()) current--;
    }

    @Override
    public void goForward() {
        if (canGoForward()) current++;
    }

    @Override
    public boolean canGoBack() {
        return current > 0;
    }

    @Override
    public boolean canGoForward() {
        return current < URL.size() - 1;
    }

    @Override
    public void reload() {
    }
}
