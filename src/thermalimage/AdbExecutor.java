package thermalimage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdbExecutor {

    private String ipAddress;
    private String portNumber;
    private SystemCommandExecutor commandExecutor = new SystemCommandExecutor();

    public AdbExecutor(String ipAddress) {
        this.ipAddress = ipAddress;
        portNumber = "5555"; // default adb port

    }

    public AdbExecutor(String ipAddress, String portNumber) {
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
    }

    public void connect() {
        List<String> command = new ArrayList<String>();
        command.add("adb");
        command.add("connect");
        command.add(ipAddress);
        commandExecutor.executeCommand(command);
        for (String cmd : command){
            System.out.printf(cmd + " ");
        }
        System.out.println();
        System.out.println("ADB connection: " + commandExecutor.getStandardOutputFromCommand());
        System.out.printf("Error: " + commandExecutor.getStandardErrorFromCommand());
    }

    public void devices() {
        List<String> command = new ArrayList<String>();
        command.add("adb");
        command.add("devices");
        commandExecutor.executeCommand(command);
        System.out.println("ADB devices: " + commandExecutor.getStandardOutputFromCommand());
    }

    public void keyEvent(Keycode keycode) {

        List<String> command = new ArrayList<String>();
        command.add("adb");
        command.add("shell");
        command.add("input");
        command.add("keyevent");
        command.add(keycode.getNumber());

        commandExecutor.executeCommand(command);

        //TODO implement a Timer that calls the methods in a constant time intervall

    }

    public void wakeUp() {
        keyEvent(Keycode.POWER);
    }


    public void launchApp(String packagename) {
        List<String> command = new ArrayList<String>();
        command.add("adb");
        command.add("shell");
        command.add("monkey");
        command.add("-p");
        command.add(packagename);
        command.add("-c");
        command.add("android.intent.category.LAUNCHER");
        command.add("1");

        commandExecutor.executeCommand(command);
    }

    public void killApp(String packagename) {
        List<String> command = new ArrayList<String>();
        command.add("adb");
        command.add("shell");
        command.add("am");
        command.add("force-stop");
        command.add(packagename);

        commandExecutor.executeCommand(command);
    }

    public void listAllPackages() {
        List<String> command = new ArrayList<String>();
        command.add("adb");
        command.add("shell");
        command.add("pm");
        command.add("list");
        command.add("packages");

        commandExecutor.executeCommand(command);
    }

    public void transferPictures(String localDirectory, String remoteDirectory, List<String> pictures,
                                 boolean deleteAfterTransfer) {

        List<String> command = new ArrayList<String>();
        command.add("adb"); //index 0
        command.add("pull"); // index 1
        command.add("placeholder"); // index 2 will be overwritten for each picture
        command.add(localDirectory); // index 3

        //for (int i = 0; i < 5; i++) { // test with a few pictures TODO uncomment for loop with all pictures and delete this
        //    String path = remoteDirectory + "/" + pictures.get(i);

        for (String picture : pictures) {
            String path = remoteDirectory + "/" + picture;
            command.set(2, path); // index 2
            commandExecutor.executeCommand(command);
        }

        if (deleteAfterTransfer) {
            for (String picture : pictures) {
                deletePicture(picture, remoteDirectory);
            }

        }
    }

    public void deletePicture(String picture, String directory) {

        String filePath = directory + "/" + picture;

        List<String> command = new ArrayList<String>();
        command.add("adb");
        command.add("shell");
        command.add("rm");
        command.add(filePath);

        commandExecutor.executeCommand(command);
    }

    public List<String> listPictures(String dir) {

        List<String> command = new ArrayList<String>();
        command.add("adb");
        command.add("shell");
        command.add("ls");
        command.add(dir);

        commandExecutor.executeCommand(command);
        StringBuilder pictures = commandExecutor.getStandardOutputFromCommand();

        return Arrays.asList(pictures.toString().split("\\r?\\n"));
    }
}