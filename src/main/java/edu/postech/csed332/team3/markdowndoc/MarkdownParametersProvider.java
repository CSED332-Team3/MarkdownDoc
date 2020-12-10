package edu.postech.csed332.team3.markdowndoc;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.ui.JBColor;
import com.intellij.util.PlatformIcons;
import com.intellij.util.ProcessingContext;
import org.bouncycastle.asn1.pkcs.MacData;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * This class give result of Completion to MarkdownCompletionContributor
 * this class doesn't implement perfect yet
 */
public class MarkdownParametersProvider extends CompletionProvider<CompletionParameters> {
    private static final String BOLD = "** **";
    private static final String ITALIC = "HELLO";
    private static final String URL = "< >";

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {

        LookupElement e = LookupElementBuilder.create("Bold")
                .withPresentableText("MarkDown Bold")
                .withItemTextForeground(JBColor.RED)
                .bold()
                .withIcon(PlatformIcons.VARIABLE_ICON)
                .withTailText("using like **STRING**");
        result.addElement(e);

        LookupElement f = LookupElementBuilder.create("HELLO")
                .withPresentableText("HELLO")
                .withItemTextForeground(JBColor.RED)
                .bold()
                .withIcon(PlatformIcons.VARIABLE_ICON)
                .withTailText(" test completion");
        result.addElement(f);

    }

}
