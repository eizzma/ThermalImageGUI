package thermalimage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Processbuilder {

    /**
     * Class that will handle the execution of commandline calls
     */

    //TODO
    public void run(String command) {

        String s = null;

        try {

            Process p = Runtime.getRuntime().exec(command);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            System.out.println("Here is the standard output of the command:");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            System.out.println("\n");

            if (stdError.readLine() != null) {
                System.out.println("Here is the standard error of the command:");
                while ((s = stdError.readLine()) != null) {
                    System.out.println(s);
                }
            }

            System.exit(0);
        } catch (IOException e) {
            System.out.println("exeception happend - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }


}
