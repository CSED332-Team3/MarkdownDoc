package edu.postech.csed332.team3.MarkdownDoc.SearchProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;
import java.util.List;

public class SearchProject extends Thread {
    //Project path
    private FileSystem fs = FileSystems.getDefault();
    private final Path projPath = fs.getPath("./src");
    private WatchKey key;
    private final ModifyDocument modifyDocument = new ModifyDocument();


    @Override
    public void run() {
        try {
            WatchService watchService = fs.newWatchService();
            key = projPath.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.OVERFLOW);

            while (true) {
                key = watchService.take();

                List<WatchEvent<?>> eventList = key.pollEvents();
                for (WatchEvent<?> event : eventList) {
                    Kind<?> kind = event.kind();
                    Path pth = (Path) (event.context());
                    pth = Path.of(projPath + "/" + pth.toString());
                    if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
                        File file = new File("./mdsaved/" + pth.getFileName().toString().replace(".java", "") + ".md");
                        boolean result = file.createNewFile();
                        modifyDocument.ModifyDocument(pth, file);
                    } else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
                        File file = new File("./mdsaved/" + pth.getFileName().toString().replace(".java", "") + ".md");
                        boolean result = file.delete();
                    } else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
                        File file = new File("./mdsaved/" + pth.getFileName().toString().replace(".java", "") + ".md");
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