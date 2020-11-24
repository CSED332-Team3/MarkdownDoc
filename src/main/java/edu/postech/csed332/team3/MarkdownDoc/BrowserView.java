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
    private final JLabel responseLabel;

    /**
     * Creates a browser component
     */
    public BrowserView() {
        panel = new JPanel(new BorderLayout());

        // TODO: Remove response label for production

        // Button layout
        JPanel buttons = new JPanel(new GridLayout(1, 3));
        backButton = new JButton();
        forwardButton = new JButton();

        // Response label
        responseLabel = new JLabel();
        responseLabel.setText("No responses");

        // Set text
        backButton.setText("←");
        forwardButton.setText("→");

        buttons.add(backButton);
        buttons.add(responseLabel);
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
     * Get the response label
     *
     * @return the response label
     */
    public JLabel getResponseLabel() {
        // TODO: Remove
        return responseLabel;
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
