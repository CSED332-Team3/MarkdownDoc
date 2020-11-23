package edu.postech.csed332.team3.MarkdownDoc.SearchProject;

import com.github.javaparser.ast.comments.Comment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ModifyDocument {
    ManageComment manageComment = new ManageComment();
    public boolean ModifyDocument(Path pth, File file) throws IOException {
        File fileWithComment = new File(String.valueOf(pth));

        if(!manageComment.isJavaFile(fileWithComment.getName()))
            return false;

        JsonArray commentAndElementInfo = manageComment.AllJavadocExtractor(fileWithComment);

        return true;
    }
}
