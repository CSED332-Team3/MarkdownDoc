package edu.postech.csed332.team3.MarkdownDoc.SearchProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchProject extends Thread {
    //Project path
    private FileSystem fs = FileSystems.getDefault();
    private final Path projPath = fs.getPath("./src");
    private WatchKey key;
    private WatchService watchService;
    private final ModifyDocument modifyDocument = new ModifyDocument();

    public void init() {
        String path = System.getProperty("user.dir");
        File dir = new File(path);
        File files[] = dir.listFiles();

        for (int i = 0; i < files.length; i++) {
        }
    }

    private void RegisterAllDir(final Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
                return FileVisitResult.CONTINUE;
            }

        });

    }
    @Override
    public void run() {
        try {
            watchService = fs.newWatchService();
            projPath.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.OVERFLOW);
            RegisterAllDir(projPath);

            while (true) {
                key = watchService.take();

                List<WatchEvent<?>> eventList = key.pollEvents();
                for (WatchEvent<?> event : eventList) {
                    Kind<?> kind = event.kind();
                    Path pth = (Path) (event.context());
                    pth = ((Path) key.watchable()).resolve(pth);
                    if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE) && ManageComment.isJavaFile(pth.getFileName().toString())) {
                        File file = new File("./mdsaved" + pth.toString().replace(projPath.toString(), "").replace(".java", "") + ".md");
                        file.getParentFile().mkdirs();
                        boolean result = file.createNewFile();
                        modifyDocument.ModifyDocument(pth, file);
                    } else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE) && ManageComment.isJavaFile(pth.getFileName().toString())) {
                        File file = new File("./mdsaved" + pth.toString().replace(projPath.toString(), "").replace(".java", "") + ".md");
                        boolean result = file.delete();
                    } else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY) && ManageComment.isJavaFile(pth.getFileName().toString())) {
                        File file = new File("./mdsaved" + pth.toString().replace(projPath.toString(), "").replace(".java", "") + ".md");
                        boolean result = file.delete();
                        result = file.createNewFile();
                        modifyDocument.ModifyDocument(pth, file);
                    } else if (kind.equals(StandardWatchEventKinds.OVERFLOW))
                        System.out.print("Overflow is occurred");
                }
                boolean valid = key.reset();
                if (!valid) {
                    watchService.close();
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}