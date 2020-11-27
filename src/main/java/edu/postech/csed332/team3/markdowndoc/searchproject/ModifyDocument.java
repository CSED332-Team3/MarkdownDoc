package edu.postech.csed332.team3.markdowndoc.searchproject;

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

    /**
     * Given Java file and Markdown file, we extract JavaDoc comments from Java file,
     * and add that to Markdown file
     *
     * @param pth path of javafile that contains JavaDoc comments
     * @param file Markdown file to be modified
     * @return if it works correctly then return true, else return false
     * @throws IOException
     */
    public boolean ModifyDocument(Path pth, File file) throws IOException {
        File fileWithComment = new File(String.valueOf(pth));

        //if path of file is not JavaFile than return false
        if (!manageComment.isJavaFile(fileWithComment.getName()))
            return false;

        //extract all JavaDoc comments from java file
        JsonArray commentAndElementInfo = manageComment.AllJavadocExtractor(fileWithComment);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            //iterate json array
            for (JsonElement jsonElement : commentAndElementInfo) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                Set<String> keys = jsonObject.keySet();
                Iterator<String> itr = keys.iterator();
                while (itr.hasNext()) {
                    String str_name = itr.next();

                    // change javaDoc grammar to markdown grammar
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
