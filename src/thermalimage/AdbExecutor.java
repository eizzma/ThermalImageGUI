package thermalimage;

import java.util.ArrayList;
import java.util.List;

public class AdbExecutor {


    /**
     * adb keycode numbers:
     * 1    menu
     * 24   volume up
     * 25   volume down
     * 26   power
     * 27   camera
     * <p>
     * command:
     * adb shell input keyevent keycode number
     */

    //needed for wireless ADB connection
    private String ipAddress;
    private String portNumber;
    private SystemCommandExecutor commandExecutor;

    public AdbExecutor(String ipAddress) {
        this.ipAddress = ipAddress;
        this.portNumber = "5555"; // default adb port
        List<String> commands = new ArrayList<String>();
        commands.add("adb");
        commands.add("connect");
        commands.add(ipAddress);
        commandExecutor = new SystemCommandExecutor();
        commandExecutor.executeCommand(commands);

        commands.clear();
        commands.add("adb");
        commands.add("devices");
        commandExecutor.executeCommand(commands);

        System.out.println(commandExecutor.getStandardOutputFromCommand());

    }

    public AdbExecutor(String ipAddress, String portNumber) {
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
    }

    public void runOnShell(String command) {
        List<String> commands = new ArrayList<String>();
        commands.add("adb shell");
        commands.add(command);

        commandExecutor.executeCommand(commands);
    }

    public void keyEvent(int intervall, int times, Keycode keycode) {

        List<String> command = new ArrayList<String>();
        command.add("adb shell input keyevent");
        command.add(keycode.getNumber());

        System.out.println(command);

        //TODO implement a Timer that calls the methods in a constant time intervall

        // SystemCommandExecutor commandExecutor = new SystemCommandExecutor(command);
    }

    public void restartApp() {
        //TODO kill process of app running and restart the app via adb keyevents.
    }

    public void transferPictures() {
        //TODO implement a functionality to get/import pictures in a folder on the computer or the current working directory
        //TODO optional delete the pictures on the phone after the import
    }

    public void deletePictures() {

    }

    public String listPictures() {
        SystemCommandExecutor commandExecutor = null;
        runOnShell("ls \\Phone\\DCIM\\Thermal\\ Camera");
        StringBuilder pictures = commandExecutor.getStandardOutputFromCommand();
        System.out.println(pictures.toString());
        return pictures.toString();
    }


}
