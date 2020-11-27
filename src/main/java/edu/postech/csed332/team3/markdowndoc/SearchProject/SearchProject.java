package edu.postech.csed332.team3.markdowndoc.SearchProject;

import edu.postech.csed332.team3.markdowndoc.BrowserController;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class SearchProject extends Thread {
    private FileSystem fs = FileSystems.getDefault();
    private Path projPath;
    private WatchKey key;
    private WatchService watchService;
    private final ModifyDocument modifyDocument = new ModifyDocument();
    private final BrowserController controller;

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
     * @param pth path of project root directory
     * @throws IOException
     */
    public void init(String pth) throws IOException {
        //set projPath as input
        projPath = Path.of(pth);

        //make mdsaved directory
        File Folder = new File(pth + "/mdsaved");
        if (!Folder.exists()) {
            try{
                Folder.mkdirs();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }

        //initialize mdsaved directory using recursion
        initDirectory(pth + "/src");
    }

    private void initDirectory(String pth) throws IOException {
        File dir = new File(pth);
        File files[] = dir.listFiles();

        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()){
                initDirectory(files[i].getCanonicalPath());
            }
            else{
                File file_ = new File(projPath.toString());
                String p = files[i].getCanonicalPath().replace(file_.getCanonicalPath() + "/src", "");
                if(ManageComment.isJavaFile(p)) {
                    File file = new File(projPath + "/mdsaved" + p.replace(".java", "") + ".md");
                    file.getParentFile().mkdirs();
                    boolean result = file.createNewFile();
                    modifyDocument.ModifyDocument(Path.of(files[i].getCanonicalPath()), file);
                }
            }
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
            RegisterAllDir(Path.of(projPath.toString() + "/src"));

            while (true) {
                key = watchService.take();

                List<WatchEvent<?>> eventList = key.pollEvents();
                for (WatchEvent<?> event : eventList) {
                    Kind<?> kind = event.kind();
                    Path pth = (Path) (event.context());
                    pth = ((Path) key.watchable()).resolve(pth);
                    if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE) && ManageComment.isJavaFile(pth.getFileName().toString())) {
                        File file = new File(projPath + "/mdsaved" + pth.toString().replace(projPath.toString() + "/src", "").replace(".java", "") + ".md");
                        file.getParentFile().mkdirs();
                        boolean result = file.createNewFile();
                        modifyDocument.ModifyDocument(pth, file);
                    } else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE) && ManageComment.isJavaFile(pth.getFileName().toString())) {
                        File file = new File(projPath + "/mdsaved" + pth.toString().replace(projPath.toString()+ "/src", "").replace(".java", "") + ".md");
                        boolean result = file.delete();
                    } else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY) && ManageComment.isJavaFile(pth.getFileName().toString())) {
                        File file = new File(projPath + "/mdsaved" + pth.toString().replace(projPath.toString()+ "/src", "").replace(".java", "") + ".md");
                        PrintWriter writer = new PrintWriter(file);
                        writer.print("");
                        writer.close();
                        modifyDocument.ModifyDocument(pth, file);
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
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}