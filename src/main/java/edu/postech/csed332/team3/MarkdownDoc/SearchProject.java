package edu.postech.csed332.team3.MarkdownDoc;

import java.io.File;
import java.io.IOException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class ProjectSearcher {
    //Project path
    private static final String projPath = System.getProperty("user.dir");
    private WatchKey watchKey;

    public void SearchProject() {
        WatchService watchService = FileSystems.getDefault().newWatchService();

        Path path = Paths.get(projPath);
        path.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.OVERFLOW);
        while (true) {
            WatchKey key = watchService.take();
            List<WatchEvent<?>> eventList = key.pollEvents();
            for (WatchEvent<?> event : eventList) {
                Kind<?> kind = event.kind();
                Path pth = (Path) event.context();
                if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE))
                    ModifyDocument();
                else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE))
                    ModifyDocument();
                else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY))
                    ModifyDocument();
                else if (kind.equals(StandardWatchEventKinds.OVERFLOW))
                    System.out.print
            }
        }

    }

    public boolean ModifyDocument() {
        return null;
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