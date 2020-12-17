package edu.postech.csed332.team3.markdowndoc;

import com.intellij.notification.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.externalSystem.service.execution.NotSupportedException;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiType;
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import edu.postech.csed332.team3.markdowndoc.explorer.ProjectModel;
import edu.postech.csed332.team3.markdowndoc.util.LoggerUtil;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefDisplayHandler;
import org.cef.handler.CefLoadHandler;
import org.cef.network.CefRequest;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.TreeModel;
import java.io.File;
import java.io.FileNotFoundException;

import static edu.postech.csed332.team3.markdowndoc.explorer.ActiveProjectModel.getActiveProject;

public class BrowserController implements BrowserControllerInterface {

    private static final String HTML = "html";
    private final JBCefBrowser browser;
    private final BrowserView view;
    private final CefBrowser cefBrowser;
    private final ProjectNavigator navigator;
    private final String projectPath;
    private final String baseURL;
    private TreeModel model;
    private RearrangeMembers rearrangeMembers;

    public static final NotificationGroup GROUP_DISPLAY_ID_INFO =
            new NotificationGroup("MarkdownDoc",
                    NotificationDisplayType.BALLOON, true);

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

        baseURL = "file://" + projectPath + "/html/index.html";
        browser = new JBCefBrowser(baseURL);
        this.view = view;
        cefBrowser = browser.getCefBrowser();

        // Finalize the view
        view.addComponent(browser.getComponent(), "Center"); // Add browser

        setListeners();
        setHandlers();

        // Make html directory
        File folder = new File(projectPath, HTML);
        folder.mkdirs();

        //Create RearrangeMembers that is invoked by ShortCut (Ctrl + ;)
        rearrangeMembers = new RearrangeMembers(this);

        // Initialize SearchProject
        model = ProjectModel.createProjectTreeModel(getActiveProject());
        navigator = new ProjectNavigator();
    }

    private void setListeners() {
        // Set action listeners
        view.getBackButton().addActionListener(e -> {
            goBack();
        });

        view.getForwardButton().addActionListener(e -> {
            goForward();
        });

        view.getExportButton().addActionListener(e -> {
            ApplicationManager.getApplication().invokeLater(() -> {
                try {
                    // Refresh all pages and make sure it's ip to date
                    model = ProjectModel.createProjectTreeModel(getActiveProject());
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
            });
        });
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
                }

                return false;
            }
        });

        cefBrowser.getClient().addLoadHandler(new CefLoadHandler() {
            @Override
            public void onLoadingStateChange(CefBrowser cefBrowser, boolean b, boolean b1, boolean b2) {

            }

            @Override
            public void onLoadStart(CefBrowser cefBrowser, CefFrame cefFrame, CefRequest.TransitionType transitionType) {

            }

            @Override
            public void onLoadEnd(CefBrowser cefBrowser, CefFrame cefFrame, int i) {

            }

            @Override
            public void onLoadError(CefBrowser cefBrowser, CefFrame cefFrame, ErrorCode errorCode, String s, String s1) {
                LoggerUtil.warning(s);
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
