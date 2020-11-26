package edu.postech.csed332.team3.markdowndoc.SearchProject;

import com.github.javaparser.ast.comments.Comment;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Set;

public class ModifyDocument {
    ManageComment manageComment = new ManageComment();

    public boolean ModifyDocument(Path pth, File file) throws IOException {
        File fileWithComment = new File(String.valueOf(pth));

        if (!manageComment.isJavaFile(fileWithComment.getName()))
            return false;

        JsonArray commentAndElementInfo = manageComment.AllJavadocExtractor(fileWithComment);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (JsonElement jsonElement : commentAndElementInfo) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                Set<String> keys = jsonObject.keySet();
                Iterator<String> itr = keys.iterator();
                while (itr.hasNext()) {
                    String str_name = itr.next();

                    String str = jsonObject.get(str_name).getAsString();
                    str = str.replace("/**\n", "");
                    str = str.replace(" */\n", "");
                    str = str.replace(" *", "   - ");

                    writer.write("+ " + str_name + "\n");
                    writer.write(str);
                }
            }
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
