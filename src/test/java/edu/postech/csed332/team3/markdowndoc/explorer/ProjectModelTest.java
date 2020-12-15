package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.openapi.project.Project;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

public class ProjectModelTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testData/projectModelTest";
    }

    public void testCreateProjectTreeModel() {
        Project project = getProject();
        TreeModel model = ProjectModel.createProjectTreeModel(project);

        Object root = model.getRoot();
        assertTrue(root instanceof DefaultMutableTreeNode);
        assertEquals(project, ((DefaultMutableTreeNode) root).getUserObject());
    }
}
