package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BrowserWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        BrowserView view = new BrowserView();
        try {
            BrowserController controller = new BrowserController(view);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(view.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
