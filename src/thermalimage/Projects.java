package thermalimage;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;

public class Projects {

    public static HashMap<String, List<Integer>> projectMap = new HashMap<>();

    public Projects(){
        projectMap = scan();
    }

    public HashMap<String, List<Integer>> scan(){
        Path dir = Paths.get(Settings.projectPath);
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream){
                System.out.println(file.getFileName());
            }
        }catch (IOException | DirectoryIteratorException x){
            System.err.println(x);
        }
        return null;
    }
}
