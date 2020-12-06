package edu.postech.csed332.team3.markdowndoc;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.psi.PsiManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import edu.postech.csed332.team3.markdowndoc.explorer.UpdateListener;
import org.jetbrains.annotations.NotNull;

public class BrowserWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        BrowserView view = new BrowserView();
        BrowserController controller = new BrowserController(view);

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(view.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
        PsiManager.getInstance(project).addPsiTreeChangeListener(new UpdateListener(controller), () -> {
        });
    }
}
