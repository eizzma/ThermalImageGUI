package sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;

public class PythonProgramm {

    private String cwd = System.getProperty("user.dir");

    private String test = cwd + "test.py";

    private String imageAnalysis = cwd + "Path/to/imagaAnalysis";

    private String csvAnalysis = "Path/to/csv.py";

    public boolean run(String programm) {
        try {

            // String prg = "import sys\nprint int(sys.argv[1])+int(sys.argv[2])\n";
            // String prg = "import sys\nprint int";
            // BufferedWriter out = new BufferedWriter(new FileWriter("test.py"));
            // out.write(prg);
            // out.close();
            int number1 = 10;
            int number2 = 32;

            // ProcessBuilder pb = new ProcessBuilder("python", "" + programm);
            // Process p = pb.start();


            Process p = Runtime.getRuntime().exec("python " + programm);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String answer = new String(in.readLine());
            // int ret = new Integer(in.readLine()).intValue();
            // System.out.println("value is : " + ret);

            System.out.println(answer);
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

}
