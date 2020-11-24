package edu.postech.csed332.team3.MarkdownDoc;

import com.intellij.openapi.project.Project;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class ProjectNavigatorTest extends BasePlatformTestCase {

    private ProjectNavigator pn;

    public void classExistsTest() {
        Project mockProject = getProject();

        pn = new ProjectNavigator();
    }
}
