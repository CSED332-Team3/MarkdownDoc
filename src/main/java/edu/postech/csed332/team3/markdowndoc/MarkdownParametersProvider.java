package edu.postech.csed332.team3.markdowndoc;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MarkdownParametersProvider extends CompletionProvider<CompletionParameters> {
    private Map<String, String> MarkdownGrammar;
    MarkdownParametersProvider(Map<String, String> MarkdownGrammar_){
        MarkdownGrammar = MarkdownGrammar_;
    }
    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        for(Map.Entry<String, String> entry:MarkdownGrammar.entrySet()){
            result.addElement(LookupElementBuilder.create(entry.getKey()));
        }
    }
}
