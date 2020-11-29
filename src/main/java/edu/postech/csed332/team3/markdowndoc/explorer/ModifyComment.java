package edu.postech.csed332.team3.markdowndoc.explorer;

public class ModifyComment {
    private ModifyComment() {
    }

    /**
     * Modify a comment string in .html to remove javaDoc grammar.
     *
     * @param comment comment to modify
     * @return modified comment
     */
    public static String modifyComment(String comment) {
        return comment.replace("/**\n", "")
                .replace(" */\n", "")
                .replace(" * ", "");
    }
}
