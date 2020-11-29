package edu.postech.csed332.team3.markdowndoc.searchproject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.postech.csed332.team3.markdowndoc.MarkdownParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public class ModifyDocument {
    /**
     * Given Java file and Markdown file, we extract JavaDoc comments from Java file,
     * and add that to Markdown file
     *
     * @param path  path of java file that contains JavaDoc comments
     * @param file Markdown file to be modified
     * @return if it works correctly then return true, else return false
     * @throws IOException When IO error occurs.
     */
    public boolean modifyDocument(Path path, File file) throws IOException {
        File fileWithComment = new File(path.toString());

        //if path of file is not JavaFile than return false
        if (!ManageComment.isJavaFile(fileWithComment.getName()))
            return false;

        //extract all JavaDoc comments from java file
        JsonArray commentAndElementInfo = ManageComment.allJavadocExtractor(fileWithComment);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            StringBuilder builder = new StringBuilder();

            //iterate json array
            for (JsonElement jsonElement : commentAndElementInfo) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                Set<String> keys = jsonObject.keySet();
                for (String str_name : keys) {
                    // change javaDoc grammar to markdown grammar
                    String str = jsonObject.get(str_name).getAsString();
                    str = str.replace("/**\n", "");
                    str = str.replace(" */\n", "");
                    str = str.replace(" * ", "");

                    builder.append("+ ").append(str_name).append('\n');
                    builder.append(str).append('\n');
                }
            }
            String res = MarkdownParser.parse(builder.toString());
            writer.write(res);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
