package edu.postech.csed332.team3.MarkdownDoc;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BrowserTest {

    private Browser browser;

    @BeforeEach
    public void setUp() {
        browser = new Browser();
    }

    @Test
    public void loadURLTest() {
        browser.loadURL("https://google.com");
        assertEquals(browser.getURL(), "https://google.com");
    }
}
