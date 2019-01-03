package thermalimage;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PythonProgramm {

    private String cwd = System.getProperty("user.dir");

    private String imageAnalysis = cwd + "Path/to/imagaAnalysis";

    private String csvAnalysis = "Path/to/csv.py";

    public int run(String programmName) {

        List<String> commands = new ArrayList<String>();
        commands.add("python");
        if (new File(cwd + "/python/" + programmName).exists()) {
            commands.add(cwd + "/python/" + programmName);
        } else {
            System.err.println("Python Programm: \"" + cwd + "/python/" + programmName + "\" not found");
            System.exit(-1);
        }
        SystemCommandExecutor systemCommandExecutor = new SystemCommandExecutor(commands);
        int result = systemCommandExecutor.executeCommand();

        // get the stdout and stderr from the command that was run
        StringBuilder stdout = systemCommandExecutor.getStandardOutputFromCommand();
        StringBuilder stderr = systemCommandExecutor.getStandardErrorFromCommand();

        // print the stdout and stderr
        System.out.println("The numeric result of the command was: " + result);
        System.out.println("STDOUT:");
        System.out.println(stdout);
        if (stderr != null) {
            System.out.println("STDERR:");
            System.out.println(stderr);
        }
        return result;
    }

}
