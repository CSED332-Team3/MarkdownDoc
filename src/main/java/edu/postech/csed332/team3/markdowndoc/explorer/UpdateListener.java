package edu.postech.csed332.team3.markdowndoc.explorer;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiTreeChangeAdapter;
import com.intellij.psi.PsiTreeChangeEvent;
import com.intellij.psi.util.PsiUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;

public class UpdateListener extends PsiTreeChangeAdapter {
    @Override
    public void childrenChanged(@NotNull PsiTreeChangeEvent event) {
        final PsiElement element = event.getElement();
        if (element == null) return;

        final PsiClass topLevelClass = PsiUtil.getTopLevelClass(element);
        if (topLevelClass == null) return;

        final DefaultMutableTreeNode root = new DefaultMutableTreeNode(topLevelClass.getParent());
        element.accept(new MdDocElementVisitor(root));
    }
}
