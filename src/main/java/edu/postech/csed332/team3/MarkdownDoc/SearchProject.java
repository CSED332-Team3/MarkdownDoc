package edu.postech.csed332.team3.MarkdownDoc;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;
import java.util.List;

public class ProjectSearcher {
    //Project path
    private static final String projPath = System.getProperty("user.dir");
    private WatchKey watchKey;

    public void SearchProject() throws IOException, InterruptedException {
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
                if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)){
                    File file = new File("./" + pth.getFileName() + ".md");
                    boolean result = file.createNewFile();
                    ModifyDocument(pth, file);
                }
                else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)){
                    File file = new File("./" + pth.getFileName() + ".md");
                    file.delete();
                }
                else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)){
                    File file = new File("./" + pth.getFileName() + ".md");
                    ModifyDocument(pth, file);
                }
                else if (kind.equals(StandardWatchEventKinds.OVERFLOW))
                    System.out.printf("Overflow is occurred");
            }
        }

    }

    public boolean ModifyDocument(Path pth, File file) {

        pth.getFileName();
        return true;
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