package edu.postech.csed332.team3.markdowndoc.browser;

import com.intellij.notification.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.externalSystem.service.execution.NotSupportedException;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiType;
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import edu.postech.csed332.team3.markdowndoc.Exporter;
import edu.postech.csed332.team3.markdowndoc.RearrangeMembers;
import edu.postech.csed332.team3.markdowndoc.explorer.ProjectModel;
import edu.postech.csed332.team3.markdowndoc.explorer.ProjectNavigator;
import edu.postech.csed332.team3.markdowndoc.util.LoggerUtil;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefDisplayHandler;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;

import static edu.postech.csed332.team3.markdowndoc.explorer.ActiveProjectModel.getActiveProject;

/**
 * Class for controlling the browser model
 * This class provides methods to control and respond to user actions.
 */
public class BrowserController implements BrowserControllerInterface {

    public static final NotificationGroup GROUP_DISPLAY_ID_INFO =
            new NotificationGroup("MarkdownDoc",
                    NotificationDisplayType.BALLOON, true);
    private static final String HTML = "html";
    private final JBCefBrowser browser;
    private final BrowserView view;
    private final CefBrowser cefBrowser;
    private final ProjectNavigator navigator;
    private final String projectPath;
    private final String home;

    /**
     * Create an empty browser controller instance
     */
    public BrowserController(BrowserView view) {
        if (!JBCefApp.isSupported()) {
            throw new NotSupportedException("This IDE version is not supported.");
        }

        // Get project root directory and load it in the browser
        @NotNull VirtualFile projectRoot = ModuleRootManager.getInstance(
                ModuleManager.getInstance(getActiveProject()).getModules()[0]
        ).getContentRoots()[0];

        projectPath = projectRoot.getCanonicalPath();

        home = "file://" + projectPath + "/html/index.html";
        browser = new JBCefBrowser(home);
        this.view = view;
        cefBrowser = browser.getCefBrowser();

        // Finalize the view
        view.addComponent(browser.getComponent(), "Center"); // Add browser

        setListeners();
        setHandlers();

        // Make html directory
        File folder = new File(projectPath, HTML);
        if (!folder.mkdirs()) {
            LoggerUtil.warning("File creation failed");
        }

        //Create RearrangeMembers that is invoked by ShortCut (Ctrl + ;)
        RearrangeMembers.setController(this);

        // Initialize SearchProject
        // Call twice since extends/implements list is not built during the first run
        for (int i = 0; i < 2; i++)
            ProjectModel.createProjectTreeModel(getActiveProject());
        navigator = new ProjectNavigator();
    }

    private void setListeners() {
        // Set action listeners
        view.getBackButton().addActionListener(e -> goBack());

        view.getForwardButton().addActionListener(e -> goForward());

        view.getExportButton().addActionListener(e -> ApplicationManager.getApplication().invokeLater(() -> {
            try {
                // Refresh all pages and make sure it's up to date
                ProjectModel.createProjectTreeModel(getActiveProject());
                // Export to .zip
                Exporter.export("mddoc", projectPath);
                Notification notification = GROUP_DISPLAY_ID_INFO
                        .createNotification("Successfully exported to project directory.",
                                NotificationType.INFORMATION);
                Notifications.Bus.notify(notification);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
                Notification notification = GROUP_DISPLAY_ID_INFO
                        .createNotification("An error has occurred.",
                                NotificationType.ERROR);
                Notifications.Bus.notify(notification);
            }
        }));
    }

    private void setHandlers() {
        cefBrowser.getClient().addDisplayHandler(new CefDisplayHandler() {
            @Override
            public void onAddressChange(CefBrowser cefBrowser, CefFrame cefFrame, String s) {
                updateView();
            }

            @Override
            public void onTitleChange(CefBrowser cefBrowser, String s) {
                updateView();
            }

            @Override
            public boolean onTooltip(CefBrowser cefBrowser, String s) {
                return false;
            }

            @Override
            public void onStatusMessage(CefBrowser cefBrowser, String s) {

            }

            @Override
            public boolean onConsoleMessage(CefBrowser cefBrowser, CefSettings.LogSeverity logSeverity, String s, String s1, int i) {
                if (s.startsWith("m") || s.startsWith("f")) {
                    // Method or field
                    navigator.navigateToMethodField(s);
                } else if (s.startsWith("c")) {
                    navigator.navigateToClass(s);
                }

                return false;
            }
        });
    }

    /**
     * Update the view (button status, etc.)
     */
    public void updateView() {
        // Change button enabled status
        view.getBackButton().setEnabled(canGoBack());
        view.getForwardButton().setEnabled(canGoForward());
    }

    /**
     * Loads URL in the browser
     *
     * @param url the URL
     */
    @Override
    public void loadURL(String url) {
        browser.loadURL(url);
    }

    /**
     * Loads HTML in the browser
     *
     * @param html HTML content
     */
    @Override
    public void loadHTML(String html) {
        browser.loadHTML(html);
    }

    /**
     * Returns the current URL
     *
     * @return the URL
     */
    @Override
    public String getURL() {
        return cefBrowser.getURL();
    }

    /**
     * Go back in history if possible
     */
    @Override
    public void goBack() {
        cefBrowser.goBack();
        updateView();
    }

    /**
     * Go forward in history if possible
     */
    @Override
    public void goForward() {
        cefBrowser.goForward();
        updateView();
    }

    @Override
    public void goHome() {
        cefBrowser.loadURL(home);
    }

    /**
     * Return whether the browser can go back in history
     *
     * @return true if browser can go back
     */
    @Override
    public boolean canGoBack() {
        return cefBrowser.canGoBack();
    }

    /**
     * Return whether the browser can go forward in history
     *
     * @return true if browser can go forward
     */
    @Override
    public boolean canGoForward() {
        return cefBrowser.canGoForward();
    }

    /**
     * Reload the current page
     */
    @Override
    public void reload() {
        cefBrowser.reload();
    }

    /**
     * Execute a string of JavaScript code in this frame. The url
     * parameter is the URL where the script in question can be found, if any.
     * The renderer may request this URL to show the developer the source of the
     * error. The line parameter is the base line number to use for error
     * reporting.
     *
     * @param code The code to be executed.
     */
    public void executeJavaScript(String code) {
        cefBrowser.executeJavaScript(code, getURL(), 0);
    }

    /**
     * Sort documentation elements according to type
     *
     * @param type the type (in PsiType)
     */
    public void sort(PsiType type) {
        sort(type.getPresentableText());
    }

    /**
     * Sort documentation elements according to type
     *
     * @param type the type (in String)
     */
    public void sort(String type) {
        executeJavaScript("sortTable('" + type + "'); void(0)");
    }
}
