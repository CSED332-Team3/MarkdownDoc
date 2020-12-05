package edu.postech.csed332.team3.markdowndoc.searchproject;

import edu.postech.csed332.team3.markdowndoc.BrowserController;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class SearchProject extends Thread {
    private static final String MD_SAVED = "mdsaved";
    private static final String JAVA_EXT = ".java";
    private static final String SRC_DIR = "src";
    private static final String HTML_EXT = ".html";
    private final FileSystem fs = FileSystems.getDefault();
    private final ModifyDocument modifyDocument = new ModifyDocument();
    private final BrowserController controller;
    private Path projPath;
    private WatchService watchService;

    /**
     * Default constructor
     */
    public SearchProject() {
        controller = null;
    }

    /**
     * Initialize with BrowserController
     *
     * @param controller the BrowserController instance
     */
    public SearchProject(BrowserController controller) {
        this.controller = controller;
    }

    /**
     * searching existing file and make .md file which contain javadoc comments and classes, methods info.
     *
     * @param path path of project root directory
     * @throws IOException For IO errors.
     */
    public void init(String path) throws IOException {
        // Set projPath as input
        projPath = Path.of(path);

        // Make mdsaved directory
        File folder = new File(path, MD_SAVED);
        if (!folder.exists()) {
            try {
                folder.mkdirs();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        //initialize mdsaved directory using recursion
        initDirectory(Path.of(path, SRC_DIR).toString());
    }

    private void initDirectory(String pth) throws IOException {
        File dir = new File(pth);
        File[] files = dir.listFiles();

        for (File value : files) {
            if (value.isDirectory()) {
                initDirectory(value.getCanonicalPath());
            } else {
                File projectFile = projPath.toFile();
                String p = value.getCanonicalPath().replace(Path.of(projectFile.getCanonicalPath(), SRC_DIR).toString(), "");
                if (ManageComment.isJavaFile(p)) {
                    File file = Path.of(projPath.toString(), MD_SAVED, p.replace(JAVA_EXT, ""), HTML_EXT).toFile();
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    modifyDocument.modifyDocument(Path.of(value.getCanonicalPath()), file);
                }
            }
        }
    }

    private void registerAllDir(final Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
                return FileVisitResult.CONTINUE;
            }
        });

    }

    /**
     * Watching all created, deleted, modified files and update .md file which save all comments and classes, methods info.
     * You have to use start() instead of run().
     */
    @Override
    public void run() {
        try {
            watchService = fs.newWatchService();
            projPath.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.OVERFLOW);
            registerAllDir(Path.of(projPath.toString(), SRC_DIR));

            while (true) {
                WatchKey key = watchService.take();

                List<WatchEvent<?>> eventList = key.pollEvents();
                for (WatchEvent<?> event : eventList) {
                    Kind<?> kind = event.kind();
                    Path pth = (Path) (event.context());
                    pth = ((Path) key.watchable()).resolve(pth);
                    if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE) && ManageComment.isJavaFile(pth.getFileName().toString())) {
                        File file = getFile(pth);
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                        modifyDocument.modifyDocument(pth, file);
                    } else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE) && ManageComment.isJavaFile(pth.getFileName().toString())) {
                        File file = getFile(pth);
                        file.delete();
                    } else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY) && ManageComment.isJavaFile(pth.getFileName().toString())) {
                        File file = getFile(pth);
                        PrintWriter writer = new PrintWriter(file);
                        writer.print("");
                        writer.close();
                        modifyDocument.modifyDocument(pth, file);
                    } else if (kind.equals(StandardWatchEventKinds.OVERFLOW))
                        System.out.print("Overflow is occurred");

                    // Reload the browser
                    if (controller != null) controller.reload();
                }
                boolean valid = key.reset();
                if (!valid) {
                    watchService.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            interrupt();
        }
    }

    @NotNull
    private File getFile(Path path) {
        final String replacedPath = path.toString().replace(Path.of(projPath.toString(), SRC_DIR).toString(), "").replace(JAVA_EXT, "");
        return Path.of(projPath.toString(), MD_SAVED, replacedPath + HTML_EXT).toFile();
    }
}