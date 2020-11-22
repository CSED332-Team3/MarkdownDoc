package edu.postech.csed332.team3.MarkdownDoc.SearchProject;

import com.google.gson.JsonObject;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;


public class ManageComment {
    public JsonObject ParsingComment(ArrayList<String> comment) {
        return null;

    }

    public ArrayList<String> GetComment(File file) throws IOException {
        ArrayList<String> comments = new ArrayList<>();
        FileReader fr = null;
        try {
            fr = new FileReader(file.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);

        boolean isComment = false;
        String l = null;

        for (int i = 1; (l = br.readLine()) != null; i++) {
            if (l.trim().startsWith("/**")) {
                isComment = true;
            }

            if(isComment)
                comments.add(l);

            if (l.trim().endsWith("**/")) {
                isComment = false;
            }
        }
        return comments;
    }

    public static boolean isDocument(String file) {
        return file.endsWith(".md");
    }

    public static boolean isJavaFile(String file) {
        return file.endsWith(".java");
    }

    public static boolean isJavaDoc(String file) {
        return file.endsWith(".html");
    }

}
