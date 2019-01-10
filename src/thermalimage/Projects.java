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

    public int activeProject;

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
}
