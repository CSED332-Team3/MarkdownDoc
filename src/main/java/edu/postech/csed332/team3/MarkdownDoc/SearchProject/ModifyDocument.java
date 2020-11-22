package edu.postech.csed332.team3.MarkdownDoc.SearchProject;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class ModifyDocument {
    ManageComment manageComment = new ManageComment();
    public boolean ModifyDocument(Path pth, File file) throws IOException {
        File fileWithComment = new File(String.valueOf(pth));

        if(!manageComment.isJavaFile(fileWithComment.getName()))
            return false;

        ArrayList<String> comment = manageComment.GetComment(fileWithComment);
        JsonObject commentData = manageComment.ParsingComment(comment);


        return true;
    }
}
