package thermalimage;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static sun.plugin.util.ProgressMonitor.get;

public class Projects {

    public static HashMap<String, HashSet<String>> projectMap = new HashMap<>();

    public Projects() {
        if (projectMap.isEmpty()) {

            scan();
            printMap();

        }
    }

    public static String activeProject = null;

    public static String activeExperiment = null;

    public static boolean createFolder(String folderName) throws IOException {

        //String fileName = "test.jpeg";

        String path;

        // make sure there is an "/" at the end of the path...
        if (!Settings.projectPath.endsWith("/")) {
            path = Settings.projectPath + "/";
        } else {
            path = Settings.projectPath;
        }

        File dir = new File(path + folderName);
        //File file = new File(path + folderName + "/" + fileName);

        boolean result = false;

        if (!dir.exists()) {
            result = dir.mkdir();
        } else {
            System.out.println(dir + " Verzeichnis existiert bereits");
        }
        return result;
    }


    private void printMap() {
        //a map with key type : String, value type : String

        /*
        3 differents ways to iterate over the map
        for (String key : projectMap.keySet()) {
            //iterate over keys
            System.out.println(key + " " + projectMap.get(key));
        }

        for (HashSet<String> values : projectMap.values()) {
            //iterate over values
            for (String value : values) {

                System.out.println(value);
            }
        }

        */
        for (Map.Entry<String, HashSet<String>> pair : projectMap.entrySet()) {
            //iterate over the pairs
            System.out.println("Projekt: " + pair.getKey());
            for (String experiment : pair.getValue()) {
                System.out.println("\tExperiment: " + experiment);
            }
        }
        System.out.println();
    }

    public static void scan() {

        Path dir = Paths.get(Settings.projectPath);

        //scans the given path for files and directories
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path projectFolder : stream) {
                //if not invisbile and Folder: Projekte!
                if (!projectFolder.getFileName().toString().startsWith(".") && new File(projectFolder.toString()).isDirectory()) {
                    Path subdir = Paths.get(projectFolder.toAbsolutePath().toString());
                    HashSet tempSet = new HashSet();
                    // scans the found projecte Folder for Subfolders (experimente)
                    try (DirectoryStream<Path> innerstream = Files.newDirectoryStream(subdir)) {
                        for (Path experimentFolder : innerstream) {
                            if (!experimentFolder.getFileName().toString().startsWith(".") && new File(experimentFolder.toString()).isDirectory()) {
                                tempSet.add(experimentFolder.getFileName().toString());
                            }
                        }
                    } catch (IOException | DirectoryIteratorException x) {
                        System.err.println(x);
                    }
                    projectMap.put(projectFolder.getFileName().toString(), tempSet);
                }
            }
        } catch (IOException | DirectoryIteratorException x) {
            System.err.println(x);
        }
    }

    public static void addNewProject(String projektName) {
        activeProject = projektName;

        if (projectMap.containsKey(projektName)) {
            SceneManager.displayError("Dieser Projektname ist bereits bekannt. Das gewünschte Projekt wird geöffnet.");
        } else {
            projectMap.put(projektName, new HashSet<>());
            try {
                createFolder(projektName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static HashSet<String> getExperiments() {
        return projectMap.get(activeProject);
    }


    public static void deleteProject() throws IOException {

        String folder = getJustActiveDirectory();
        System.out.println(folder);
        Path pathFolder = Paths.get(folder);

        System.out.println(pathFolder.toString());


        FileVisitor visitor = new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc != null) {
                    throw exc;
                }
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        };


        Files.walkFileTree(pathFolder, visitor);
        String parentPath = pathFolder.getParent().toString();
        boolean check1 = !parentPath.isEmpty(); //geschummelt

        System.out.println(pathFolder.getParent());
        System.out.println("path: " + pathFolder.toString());
        System.out.println("check: " + check1);

        /**
        // delete Directory
        StringBuilder path = new StringBuilder().append(Settings.projectPath);
        if (!Settings.projectPath.endsWith("\\")) {
            path.append("\\");
        }
        path.append(activeProject);
        File dirToBeDeleted = new File(path.toString());

        // update activeProject, change scene, update projectsMap
        boolean check = dirToBeDeleted.delete();
        System.out.println("path: " + path.toString());
        System.out.println("check: " + check);
         */

        if (check1) {
            SceneManager.showMainScene();
            projectMap.remove(activeProject);
            activeProject = null;
        }

    }

    public static boolean addNewExperiment(String dateAndTime) {
        HashSet<String> experiments = getExperiments();
        experiments.add(dateAndTime);
        activeExperiment = dateAndTime;
        boolean result = false;
        try {
            result = createFolder(activeProject + "/" + dateAndTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
        projectMap.put(activeProject, experiments);


        return result;
    }

    public static boolean deleteExperiment(String toBeDeleted) {

        // TODO handle deletion of directories that are not empty


        // delete Directory
        boolean result = false;
        StringBuilder path = new StringBuilder().append(Settings.projectPath);
        if (!Settings.projectPath.endsWith("/")) {
            path.append("/");
        }
        path.append(activeProject + "/" + toBeDeleted);


        File folder = new File(path.toString());
        File parentFolder = new File(activeProject);
        File[] allFiles = folder.listFiles();

        for(int i = 0; i < allFiles.length; i++){
            if(allFiles[i].isFile()){
                allFiles[i].delete();
                System.out.println(path.toString() + " gelöscht.");
                result = true;
                /** Kein PopUp bei gelöschten Dateien?
                 * */

            }else {
            }

        }
        if(folder.isDirectory() && folder.listFiles().length < 1){
            folder.delete();
            System.out.println("Ordner " + parentFolder.toString() + " gelöscht.");
            return true;
        }


        /**File dirToBeDeleted = new File(path.toString());
        dirToBeDeleted.delete();
        result = dirToBeDeleted.delete();

        // update Hashset
        if (result) {
            HashSet experiments = getExperiments();
            experiments.remove(toBeDeleted);
            projectMap.put(activeProject, experiments);
        }

        // return result to decide if list will be updated*/
        return result;

    }

    static String getActiveDirectory() {

        StringBuilder activeDirectory = new StringBuilder(2);
        if (!Settings.projectPath.endsWith("/")) {
            activeDirectory.append(Settings.projectPath + "/");
        } else {
            activeDirectory.append(Settings.projectPath);
        }
        activeDirectory.append(Projects.activeProject + "/" + Projects.activeExperiment);

        return activeDirectory.toString();

    }

    static String getJustActiveDirectory() {

        StringBuilder activeDirectory = new StringBuilder(2);
        if (!Settings.projectPath.endsWith("/")) {
            activeDirectory.append(Settings.projectPath + "/");
        } else {
            activeDirectory.append(Settings.projectPath);
        }
        activeDirectory.append(Projects.activeProject);

        return activeDirectory.toString();

    }
}
