package thermalimage;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Projects {

    public static HashMap<String, HashSet<String>> projectMap = new HashMap<>();

    public Projects() {
        if (projectMap.isEmpty()) {

            scan();
            printMap();

        }
    }

    public String activeProject = null;

    public String activeExperiment = null;

    public static void createFolder(String folderName) throws IOException {

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

        System.out.println(Settings.projectPath);

        if (dir.mkdir()) {
            System.out.println("Datei erstellt: " + dir.createNewFile());
        } else {
            System.out.println(dir + " Konnte nicht erstellt werden.");
        }
    }

    public static boolean checkDuplicate(String iD, String path) throws IOException {
        File file = new File(path + iD);
        if (!file.exists()) {
            return false;
        } else {
            return true;
        }

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

    public void scan() {

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

    public void addNewProject(String projektName) {
        projectMap.put(projektName, new HashSet<>());
        activeProject = projektName;
        try {
            createFolder(projektName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashSet<String> getExperiments() {
        return projectMap.get(activeProject);
    }

    public void addExperiment(String dateAndTime) {
        HashSet<String> experiments = getExperiments();
        experiments.add(dateAndTime);
        activeExperiment = dateAndTime;
        try {
            createFolder(activeProject + "/" + dateAndTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
