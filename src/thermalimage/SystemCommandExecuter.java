package thermalimage;


import java.io.*;
import java.util.List;

class SystemCommandExecutor {

    private List<String> commandInformation;
    private StringBuilder inputBuffer = new StringBuilder();
    private StringBuilder errorBuffer = null;

    public SystemCommandExecutor(final List<String> commandInformation) {
        if (commandInformation == null) throw new NullPointerException("The commandInformation is required.");
        this.commandInformation = commandInformation;
    }

    public int executeCommand() {
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







