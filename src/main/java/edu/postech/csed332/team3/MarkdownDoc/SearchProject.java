package edu.postech.csed332.team3.MarkdownDoc;

import java.io.File;
import java.io.IOException;

public class ProjectSearcher {
    public void SearchProject() {
        return null;
    }

    public boolean ModifyDocument() {
        return null;
    }

    public boolean isDocument(String file) {
        return file.endsWith(".md");
    }

    public boolean isJavaFile(String file) {
        return file.endsWith(".java");
    }

    public boolean isJavaDoc(String file) {
        return file.endsWith(".html");
    }

}