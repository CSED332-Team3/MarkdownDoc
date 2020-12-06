package edu.postech.csed332.team3.markdowndoc;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.editorActions.wordSelection.DocTagSelectioner;
import com.intellij.codeInsight.lookup.Lookup;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiJavaPatterns;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.Constants;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.psi.javadoc.PsiDocToken;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static com.intellij.patterns.PsiJavaPatterns.psiElement;

/**
 *
 */
public class MarkdownCompletionContributor extends CompletionContributor implements DumbAware {
    public MarkdownCompletionContributor() {
        extend(CompletionType.BASIC, PsiJavaPatterns.psiElement(JavaDocTokenType.DOC_COMMENT_DATA), new MarkdownParametersProvider());
    }
}
