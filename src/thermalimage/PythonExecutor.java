package thermalimage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PythonExecutor {

    public int run(String programmName, String args) {

        List<String> commands = new ArrayList<String>();
        commands.add("python3");
        if (!Settings.pythonPath.endsWith("/")) {
            Settings.pythonPath = Settings.pythonPath + "/";
        }
        if (new File(Settings.pythonPath + programmName).exists()) {
            commands.add(Settings.pythonPath + programmName);
        } else {
            System.err.println("Python Programm: \"" + Settings.pythonPath + programmName + "\" not found");
            System.exit(-1);
        }
        if (args != null) {
            commands.add(args);
        }

        SystemCommandExecutor systemCommandExecutor = new SystemCommandExecutor();
        int result = systemCommandExecutor.executeCommand(commands);

        // get the stdout and stderr from the command that was run
        StringBuilder stdout = systemCommandExecutor.getStandardOutputFromCommand();
        StringBuilder stderr = systemCommandExecutor.getStandardErrorFromCommand();

        // print the stdout and stderr
        System.out.println("STDOUT:");
        System.out.println(stdout);

        System.out.println("STDERR:");
        System.out.println(stderr);

        return result;
    }


    public void evaluatePictures(List<String> pictures) {
        // takes a list of all the pictures and evaluates only the thermal images
        for (String picture : pictures) {
            // only the thermal images end with this suffix
            if (picture.endsWith("0.png")) {
                run("images.py", picture);
            }
        }
    }

    public void plotGraph(String pathToCSV) {
        run("plot.py", pathToCSV);
    }

}
