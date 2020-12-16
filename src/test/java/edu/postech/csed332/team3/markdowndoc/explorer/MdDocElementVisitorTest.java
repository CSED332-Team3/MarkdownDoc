package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.*;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.util.*;

public class MdDocElementVisitorTest extends BasePlatformTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("root/Subject.java");
    }

    @Override
    protected String getTestDataPath() {
        return "testData";
    }

    @NotNull
    private Set<PsiPackage> getPsiPackages() {
        Project project = getProject();
        final Set<PsiPackage> rootPackages = new HashSet<>();
        PsiElementVisitor visitor = new PsiElementVisitor() {
            @Override
            public void visitDirectory(@NotNull PsiDirectory dir) {
                final PsiPackage psiPackage = JavaDirectoryService.getInstance().getPackage(dir);
                if (psiPackage != null && !PackageUtil.isPackageDefault(psiPackage))
                    rootPackages.add(psiPackage);
                else
                    Arrays.stream(dir.getSubdirectories()).forEach(sd -> sd.accept(this));
            }
        };

        ProjectRootManager rootManager = ProjectRootManager.getInstance(project);
        PsiManager psiManager = getPsiManager();
        Arrays.stream(rootManager.getContentSourceRoots())
                .map(psiManager::findDirectory)
                .filter(Objects::nonNull)
                .forEach(dir -> dir.accept(visitor));
        return rootPackages;
    }

    public void testVisitor() {
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode(getProject());
        List<Boolean> reached = new ArrayList<>(List.of(false, false, false, false));
        MdDocElementVisitor visitor = new MdDocElementVisitor(root) {
            @Override
            public void visitPackage(PsiPackage aPackage) {
                reached.set(0, true);
                super.visitPackage(aPackage);
            }

            @Override
            public void visitClass(PsiClass aClass) {
                reached.set(1, true);
                super.visitClass(aClass);
            }

            @Override
            public void visitMethod(PsiMethod method) {
                reached.set(2, true);
                super.visitMethod(method);
            }

            @Override
            public void visitField(PsiField field) {
                reached.set(3, true);
                super.visitField(field);
            }
        };

        getPsiPackages().forEach(psiPackage -> psiPackage.accept(visitor));
        assertEquals(Set.of(root), Set.copyOf(visitor.getStack()));
        reached.forEach(TestCase::assertTrue);

        File test = new File("html/root/Subject.html");
        assertTrue(test.delete());

        test = new File("html/root");
        assertTrue(test.delete());
    }
}
