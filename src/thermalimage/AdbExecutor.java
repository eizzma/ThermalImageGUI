package thermalimage;

import java.io.File;
import java.util.*;

public class AdbExecutor {

    private String packageName = "georg.com.thermal_camera_plus";

    private String imgPath = "/sdcard/DCIM/Thermal Camera/";

    private SystemCommandExecutor commandExecutor = new SystemCommandExecutor();

    AdbExecutor() {

    }

    public void connect() {
        List<String> command = new ArrayList<>();
        command.add("adb");
        command.add("connect");
        command.add(Settings.ipAddress);
        int returnvalue = commandExecutor.executeCommand(command);
        System.out.println("returnvalue: " + returnvalue);
        System.out.println("ADB connection: " + commandExecutor.getStandardOutputFromCommand());
        if (commandExecutor.getStandardErrorFromCommand().length() > 1) {
            System.out.println("Error: \n" + commandExecutor.getStandardErrorFromCommand());
        }
    }

    public void devices() {
        List<String> command = new ArrayList<>();
        command.add("adb");
        command.add("devices");
        commandExecutor.executeCommand(command);
        System.out.println("ADB devices: " + commandExecutor.getStandardOutputFromCommand());
    }

    void keyEvent(Keycode keycode) {

        List<String> command = new ArrayList<>();
        command.add("adb");
        command.add("shell");
        command.add("input");
        command.add("keyevent");
        command.add(keycode.getNumber());

        commandExecutor.executeCommand(command);


        long duration = Settings.duration / Settings.timer;
        Timer timer = new Timer();
        TimerTask repeatedTask = new TimerTask() {
            int timesexecuted = 0;

            public void run() {
                System.out.println("return: " + commandExecutor.executeCommand(command) + " executed: " + timesexecuted);
                timesexecuted++;
                if (timesexecuted > duration) {
                    timer.cancel();
                }
            }
        };
        timer.schedule(repeatedTask, 0, Settings.timer * 1000);
    }

    void wakeUp() {
        keyEvent(Keycode.POWER);
    }


    void launchApp() {
        List<String> command = new ArrayList<>();
        command.add("adb");
        command.add("shell");
        command.add("monkey");
        command.add("-p");
        command.add(packageName);
        command.add("-c");
        command.add("android.intent.category.LAUNCHER");
        command.add("1");

        commandExecutor.executeCommand(command);
    }

    void killApp() {
        List<String> command = new ArrayList<>();
        command.add("adb");
        command.add("shell");
        command.add("am");
        command.add("force-stop");
        command.add(packageName);

        commandExecutor.executeCommand(command);
    }

    void listAllPackages() {
        List<String> command = new ArrayList<>();
        command.add("adb");
        command.add("shell");
        command.add("pm");
        command.add("list");
        command.add("packages");

        commandExecutor.executeCommand(command);
    }

    void transferPictures(boolean deleteAfterTransfer) {

        List<String> command = new ArrayList<>();
        command.add("adb"); //index 0
        command.add("pull"); // index 1
        command.add("placeholder"); // index 2 will be overwritten for each picture
        command.add(new File(Settings.projectPath).getAbsolutePath()); // index 3 //TODO ProjectPath + Projectname + Projectdir

        List<String> pictures = listPictures();

        for (String picture : pictures) {

            File img = new File(imgPath + picture);

            command.set(2, img.getAbsoluteFile().toString()); // index 2


            // TODO
            int result = commandExecutor.executeCommand(command);
            StringBuilder sb = commandExecutor.getStandardOutputFromCommand();
            System.out.println("Output: " + sb.toString() + ", result: " + result);

        }

        if (deleteAfterTransfer) {
            for (String picture : pictures) {
                deletePicture(picture, imgPath);
            }

        }
    }

    void testTransfer(){
        List<String> command = new ArrayList<>();
        command.add("adb");
        command.add("pull");
        command.add("/sdcard/DCIM/Screenshots/1.jpg");
        command.add(Settings.projectPath);

        commandExecutor.executeCommand(command);

    }

    private void deletePicture(String picture, String directory) {

        String filePath = directory + "/" + picture;

        List<String> command = new ArrayList<String>();
        command.add("adb");
        command.add("shell");
        command.add("rm");
        command.add(filePath);

        commandExecutor.executeCommand(command);
    }

    List<String> listPictures() {

        List<String> command = new ArrayList<>();
        command.add("adb");
        command.add("shell");
        command.add("ls");
        command.add(imgPath.replace(" ", "\\ "));

        commandExecutor.executeCommand(command);
        StringBuilder pictures = commandExecutor.getStandardOutputFromCommand();

        return Arrays.asList(pictures.toString().split("\\r?\\n"));
    }
}