package thermalimage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PythonExecutor {

    /*
    this will not work when build as a jar file, because the current working directory is depending on where the jar will
    be executed. When working with a jar-file for end-production the path to all python programms must be set static
    */
    //private String cwd = System.getProperty("user.dir");

    public int run(String programmName, String args) {

        List<String> commands = new ArrayList<String>();
        commands.add("python");
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
        if (stderr.length() < 2) {
            System.out.println("STDERR:");
            System.out.println(stderr);
        }
        return result;
    }


    public void evaluatePictures(List<String> pictures, String localDirectory) {
        for (String picture : pictures) {
            run("images.py", localDirectory + "/" + picture);
        }
    }

    public void plotGraph(String pathToCSV) {
        run("plot.py", pathToCSV);
    }

}
