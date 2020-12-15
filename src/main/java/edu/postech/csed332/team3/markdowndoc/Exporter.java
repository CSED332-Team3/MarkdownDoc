package edu.postech.csed332.team3.markdowndoc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Exporter {

    private Exporter() {
    }

    /**
     * Export html directories to zip file.
     *
     * @param projectName name of zip file
     */
    public static void export(String projectName) {
        File file = new File("html");
        try {
            FileOutputStream fileOut = new FileOutputStream(projectName + ".zip");
            ZipOutputStream zipOut = new ZipOutputStream(fileOut);

            zipFile(file, file.getName(), zipOut);

            zipOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zipFile(File fileToZip, String path, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) return;

        if (fileToZip.isDirectory()) {
            zipOut.putNextEntry(new ZipEntry(path + "/"));
            zipOut.closeEntry();

            File[] children = fileToZip.listFiles();
            Arrays.stream(children).forEach(childFile -> {
                try {
                    zipFile(childFile, path + "/" + childFile.getName(), zipOut);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return;
        }

        try (FileInputStream fileIn = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(path);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fileIn.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }
    }
}
