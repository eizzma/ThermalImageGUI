package thermalimage;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Projects {

    public static HashMap<String, List<Integer>> projectMap = new HashMap<>();

    public Projects() {
        if (projectMap.isEmpty()){

            projectMap = scan();
        }
    }

    public HashMap<String, List<Integer>> scan() {

        Path dir = Paths.get(Settings.projectPath);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                if (!file.getFileName().toString().startsWith(".")) {
                    projectMap.put(file.getFileName().toString(), new ArrayList<>());
                }
            }
        } catch (IOException | DirectoryIteratorException x) {
            System.err.println(x);
        }

        Set set = projectMap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
            System.out.println(mentry.getValue());
        }


        return null;
    }
}
