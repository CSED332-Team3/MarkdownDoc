package edu.postech.csed332.team3.MarkdownDoc;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class BrowserWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        BrowserWindow browser = new BrowserWindow();
        browser.getBrowser().loadURL("https://google.com");
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(browser.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
