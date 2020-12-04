package edu.postech.csed332.team3.markdowndoc;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.impl.source.Constants;
import com.intellij.psi.javadoc.PsiDocComment;

import java.util.HashMap;

import static com.intellij.patterns.PsiJavaPatterns.psiElement;

/**
 *
 */
public class MarkdownCompletionContributor extends CompletionContributor {
    public MarkdownCompletionContributor() {
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiComment(),
                new MarkdownParametersProvider(new HashMap<String, String>() {{
                    put("MDBold", "``");
                    put("MDTitle", "#");
                }}));
    }
}
