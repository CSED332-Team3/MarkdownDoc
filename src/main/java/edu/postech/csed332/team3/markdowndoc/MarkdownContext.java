package edu.postech.csed332.team3.markdowndoc;

import com.intellij.codeInsight.template.TemplateActionContext;
import com.intellij.codeInsight.template.TemplateContextType;
import org.jetbrains.annotations.NotNull;

/**
 * This class provide Markdown macro using Intellj live template
 * For example, when we type Bold and press TAB inside JavaDoc comment, it converted to ** **
 * Information of live template that we implement is in MarkdownCompletion.xml
 */
public class MarkdownContext extends TemplateContextType {

    protected MarkdownContext() {
        super("MARKDOWN", "MarkdownCompletion");
    }

    @Override
    public boolean isInContext(@NotNull TemplateActionContext templateActionContext) {
        return templateActionContext.getFile().getName().endsWith(".java");
    }

}
