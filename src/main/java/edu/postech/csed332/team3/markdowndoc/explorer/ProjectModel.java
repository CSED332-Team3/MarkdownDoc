package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocToken;
import edu.postech.csed332.team3.markdowndoc.MarkdownParser;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static edu.postech.csed332.team3.markdowndoc.explorer.ActiveProjectModel.getActiveProject;
import static edu.postech.csed332.team3.markdowndoc.explorer.ModifyComment.modifyComment;

public class ProjectModel {
    private static final String HTML = "html";
    private static final String JAVA_EXT = ".java";
    private static final String SRC_DIR = "src";
    private static final String HTML_EXT = ".html";

    private ProjectModel() {
    }

    public static TreeModel createProjectTreeModel(String path) {
        // Make mdsaved directory
        File folder = new File(path, HTML);
        if (!folder.mkdirs())
            return null;

        Project activeProject = getActiveProject();
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode(activeProject);

        final JavaElementVisitor visitor = new JavaElementVisitor() {
            final Deque<DefaultMutableTreeNode> stack = new ArrayDeque<>(Collections.singleton(root));
            final StringBuilder stringBuilder = new StringBuilder();
            FileWriter fileWriter = null;

            @Override
            public void visitPackage(PsiPackage pack) {
                final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(pack);
                stack.getFirst().add(newChild);
                stack.push(newChild);
                Arrays.stream(pack.getSubPackages()).forEach(psiPackage -> psiPackage.accept(this));
                Arrays.stream(pack.getClasses()).forEach(psiClass -> {
                    VirtualFile virtualFile = psiClass.getContainingFile().getVirtualFile();
                    String path = Objects.requireNonNull(virtualFile.getCanonicalPath())
                            .replace(SRC_DIR, HTML).replace(JAVA_EXT, "") + HTML_EXT;
                    File file = new File(path);
                    try {
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                        fileWriter = new FileWriter(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    psiClass.accept(this);
                    try {
                        write(stringBuilder.toString());
                        stringBuilder.setLength(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                stack.pop();
            }

            @Override
            public void visitClass(PsiClass aClass) {
                final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(aClass);
                stack.getFirst().add(newChild);
                stack.push(newChild);
                if (aClass.getName() != null) {
                    stringBuilder.append("Class: ").append(aClass.getName()).append("\n\n");
                    appendComment(aClass.getDocComment());
                }

                Arrays.stream(aClass.getInnerClasses()).forEach(psiClass -> psiClass.accept(this));
                Arrays.stream(aClass.getFields()).forEach(psiField -> psiField.accept(this));
                Arrays.stream(aClass.getMethods()).forEach(psiMethod -> psiMethod.accept(this));
                stack.pop();
            }

            @Override
            public void visitMethod(PsiMethod method) {
                final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(method);
                stack.getFirst().add(newChild);
                stringBuilder.append("Method: ").append(method.getName()).append("\n\n");
                appendComment(method.getDocComment());
            }

            @Override
            public void visitField(PsiField field) {
                final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(field);
                stack.getFirst().add(newChild);
                stringBuilder.append("Field: ").append(field.getName()).append("\n\n");
                appendComment(field.getDocComment());
            }

            private void appendComment(PsiDocComment docComment) {
                if (docComment != null) {
                    stringBuilder.append("```\n");
                    for (PsiElement descriptionElement : docComment.getDescriptionElements())
                        if (descriptionElement instanceof PsiDocToken)
                            stringBuilder.append(modifyComment(descriptionElement.getText())).append('\n');
                    stringBuilder.append("```\n\n");
                }
            }

            private void write(String str) throws IOException {
                fileWriter.write(MarkdownParser.parse(str));
                fileWriter.close();
            }
        };

        getRootPackages(activeProject).forEach(aPackage -> aPackage.accept(visitor));
        return new DefaultTreeModel(root);
    }

    private static Set<PsiPackage> getRootPackages(Project project) {
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
        PsiManager psiManager = PsiManager.getInstance(project);
        Arrays.stream(rootManager.getContentSourceRoots())
                .map(psiManager::findDirectory)
                .filter(Objects::nonNull)
                .forEach(dir -> dir.accept(visitor));

        return rootPackages;
    }
}
