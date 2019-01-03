package thermalimage;


import java.io.*;
import java.util.List;

/**
 * This class can be used to execute a system command from a Java application.
 * See the documentation for the public methods of this class for more
 * information.
 * <p>
 * Documentation for this class is available at this URL:
 * <p>
 * http://devdaily.com/java/java-processbuilder-process-system-exec
 * <p>
 * <p>
 * Copyright 2010 alvin j. alexander, devdaily.com.
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Please ee the following page for the LGPL license:
 * http://www.gnu.org/licenses/lgpl.txt
 */
class SystemCommandExecutor {

    private List<String> commandInformation;
    private StringBuilder inputBuffer = new StringBuilder();
    private StringBuilder errorBuffer = null;


    /**
     * Pass in the system command you want to run as a List of Strings, as shown here:
     * <p>
     * List<String> commands = new ArrayList<String>();
     * commands.add("/sbin/ping");
     * commands.add("-c");
     * commands.add("5");
     * commands.add("www.google.com");
     * SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
     * commandExecutor.executeCommand();
     * <p>
     * Note: I've removed the other constructor that was here to support executing
     * the sudo command. I'll add that back in when I get the sudo command
     * working to the point where it won't hang when the given password is
     * wrong.
     *
     * @param commandInformation The command you want to run.
     */
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







