package edu.postech.csed332.team3.MarkdownDoc.SearchProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;
import java.util.List;

public class SearchProject {
    //Project path
    private static final String projPath = System.getProperty("user.dir");
    private WatchKey watchKey;

    public void Search() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        ManageComment manageComment = new ManageComment();
        ModifyDocument modifyDocument = new ModifyDocument();

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
                    File file = new File("./mdsaved/" + pth.getFileName() + ".md");
                    boolean result = file.createNewFile();
                    modifyDocument.ModifyDocument(pth, file);
                }
                else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)){
                    File file = new File("./mdsaved/" + pth.getFileName() + ".md");
                    file.delete();
                }
                else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)){
                    File file = new File("./mdsaved/" + pth.getFileName() + ".md");
                    file.delete();
                    boolean result = file.createNewFile();
                    modifyDocument.ModifyDocument(pth, file);
                }
                else if (kind.equals(StandardWatchEventKinds.OVERFLOW))
                    System.out.printf("Overflow is occurred");
            }
        }

    }

}