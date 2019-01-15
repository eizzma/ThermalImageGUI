package thermalimage;


import java.io.*;
import java.util.List;

public class SystemCommandExecutor {

    private StringBuilder inputBuffer = null;
    private StringBuilder errorBuffer = null;


    public int executeCommand(final List<String> commandInformation) {
        inputBuffer = new StringBuilder();
        errorBuffer = new StringBuilder();

        int exitValue = -99;

        try {
            ProcessBuilder pb = new ProcessBuilder(commandInformation);
            Process process = pb.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            output(stdInput, inputBuffer);
            if (stdError.readLine() != null) {
                output(stdError, errorBuffer);
            }

            exitValue = process.waitFor();

        } catch (IOException e) {
            throw e;
        } finally {
            return exitValue;
        }
    }

    private void output(BufferedReader bufferedReader, StringBuilder buffer) {
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
            }
        }
    }

    public StringBuilder getStandardOutputFromCommand() {
        return inputBuffer;

    }

    public StringBuilder getStandardErrorFromCommand() {
        return errorBuffer;
    }

}







