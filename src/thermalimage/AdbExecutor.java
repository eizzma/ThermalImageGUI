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
    private SystemCommandExecutor commandExecutor = new SystemCommandExecutor();

    public AdbExecutor(String ipAddress) {
        this.ipAddress = ipAddress;
        this.portNumber = "5555"; // default adb port

    }

    public AdbExecutor(String ipAddress, String portNumber) {
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
    }

    public void connect(){
        List<String> commands = new ArrayList<String>();
        commands.add("adb");
        commands.add("connect");
        commands.add(ipAddress);
        commandExecutor.executeCommand(commands);
        System.out.println("ADB says: " + commandExecutor.getStandardOutputFromCommand());
    }

    public void devices(){
        List<String> commands = new ArrayList<String>();
        commands.add("adb");
        commands.add("devices");
        commandExecutor.executeCommand(commands);
        System.out.println("ADB devices: " + commandExecutor.getStandardOutputFromCommand());
    }

    public void keyEvent(int intervall, int times, Keycode keycode) {

        List<String> command = new ArrayList<String>();
        command.add("adb");
        command.add("shell");
        command.add("input");
        command.add("keyevent");
        command.add(keycode.getNumber());

        commandExecutor.executeCommand(command);

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

        List<String> commands = new ArrayList<String>();
        commands.add("adb");
        commands.add("shell");
        commands.add("ls");

        commandExecutor.executeCommand(commands);
        StringBuilder pictures = commandExecutor.getStandardOutputFromCommand();

        System.out.println(pictures.toString());
        return pictures.toString();
    }


}
