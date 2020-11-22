package edu.postech.csed332.team3.MarkdownDoc;

import com.sun.istack.NotNull;
import javax.swing.*;
import java.awt.*;

/**
 * Swing wrapper for Browser
 */
public class BrowserView {

    private final JPanel panel;
    private final JButton backButton;
    private final JButton forwardButton;

    /**
     * Creates a browser component
     */
    public BrowserView() {
        panel = new JPanel(new BorderLayout());

        // Button layout
        JPanel buttons = new JPanel(new GridLayout(1, 2));
        backButton = new JButton();
        forwardButton = new JButton();

        // Set text
        backButton.setText("<-");
        forwardButton.setText("->");

        // Set enabled to false
        backButton.setEnabled(false);
        forwardButton.setEnabled(false);

        buttons.add(backButton);
        buttons.add(forwardButton);
        addComponent(buttons, "South"); // Add buttons
    }

    /**
     * Get the back button element
     *
     * @return the back button
     */
    public JButton getBackButton() {
        return backButton;
    }

    /**
     * Get the forward button element
     *
     * @return the forward button
     */
    public JButton getForwardButton() {
        return forwardButton;
    }

    /**
     * Add a JComponent to BrowserView
     *
     * @param jComponent the JComponent
     */
    public void addComponent(JComponent jComponent, String location) {
        panel.add(jComponent, location);
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
}
