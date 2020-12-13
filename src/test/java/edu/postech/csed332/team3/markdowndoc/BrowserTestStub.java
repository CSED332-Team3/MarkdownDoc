package edu.postech.csed332.team3.markdowndoc;


import org.junit.Test;

import static org.junit.Assert.*;

public class BrowserTestStub {

    private BrowserControllerInterface browserController;

    @Test
    public void URLTest() {
        browserController = new BrowserControllerStub();
        browserController.loadURL("https://www.google.com");
        assertEquals("https://www.google.com", browserController.getURL());
    }

    @Test
    public void HTMLTest() {
        browserController = new BrowserControllerStub();
        browserController.loadHTML("<html><body>test</body></html>");
        assertEquals("data:text/html;base64,PGh0bWw+PGJvZHk+dGVzdDwvYm9keT48L2h0bWw+",
                browserController.getURL());
    }

    @Test
    public void goBackTest() {
        browserController = new BrowserControllerStub();
        assertFalse(browserController.canGoBack());

        browserController.loadURL("https://google.com");
        assertFalse(browserController.canGoBack());

        browserController.loadURL("https://naver.com");
        assertTrue(browserController.canGoBack());

        browserController.goBack();
        assertFalse(browserController.canGoBack());
        assertEquals("https://google.com", browserController.getURL());
    }

    @Test
    public void goForwardTest() {
        browserController = new BrowserControllerStub();
        assertFalse(browserController.canGoForward());

        browserController.loadURL("https://google.com");
        assertFalse(browserController.canGoForward());

        browserController.loadURL("https://naver.com");
        assertFalse(browserController.canGoForward());

        browserController.goBack();
        assertTrue(browserController.canGoForward());
        assertEquals("https://google.com", browserController.getURL());

        browserController.goForward();
        assertFalse(browserController.canGoForward());
        assertEquals("https://naver.com", browserController.getURL());
    }

    @Test
    public void goBackLoadNewTest() {
        browserController = new BrowserControllerStub();

        browserController.loadURL("https://google.com");
        browserController.loadURL("https://naver.com");
        browserController.loadURL("https://yahoo.com");
        browserController.goBack();
        browserController.goBack();
        assertEquals("https://google.com", browserController.getURL());

        browserController.loadURL("https://youtube.com");
        assertEquals("https://youtube.com", browserController.getURL());
        assertFalse(browserController.canGoForward());
    }
}
