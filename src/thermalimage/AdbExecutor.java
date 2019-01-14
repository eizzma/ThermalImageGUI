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

    void executeExperiment() {


        double duration = (double) Settings.duration / Settings.timer;
        Timer timer = new Timer();
        TimerTask repeatedTask = new TimerTask() {
            double timesexecuted = 0;
            ProgressBarScene progressBarScene = new ProgressBarScene();

            public void run() {
                System.out.println("picture number: " + timesexecuted);
                takeAndTransferImg();

                progressBarScene.setProgressBar(timesexecuted / duration);

                timesexecuted++;
                if (timesexecuted > duration) {
                    progressBarScene.closeProgressBar();
                    timer.cancel();
                }
            }
        };
        timer.schedule(repeatedTask, 0, Settings.timer * 1000);

    }

    void inputKeyevent(Keycode keycode) {

        List<String> command = new ArrayList<>();
        command.add("adb");
        command.add("shell");
        command.add("input");
        command.add("keyevent");
        command.add(keycode.getNumber());

        commandExecutor.executeCommand(command);

    }

    void wakeUp() {
        inputKeyevent(Keycode.POWER);
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

        String destinationDirectory = Projects.getActiveDirectory();

        List<String> pictures = listPictures();

        for (String picture : pictures) {

            File img = new File(imgPath + "/" + picture);
            File destination = new File(destinationDirectory + "/" + picture);

            adbPull(destination.getAbsolutePath(), img.getAbsolutePath());

        }

        if (deleteAfterTransfer) {
            for (String picture : pictures) {
                deletePicture(picture, imgPath);
            }

        }
    }

    void backgroundImg() {

        inputKeyevent(Keycode.VOLUMEDOWN);
        List<String> list = listPictures();
        for (String potentialBackground : list) {
            if (potentialBackground.endsWith("orig.png")) { // only orig picture is interesting for background
                adbPull(Projects.getActiveDirectory() + "/background.png"
                        , imgPath + "/" + potentialBackground);


                deletePicture(potentialBackground, imgPath);

            } else {
                deletePicture(potentialBackground, imgPath);
            }
        }
    }




    private int adbPull(String destination, String source) {

        List<String> command = new ArrayList<>();
        command.add("adb");
        command.add("pull");
        command.add(source);
        command.add(destination);

        int value = commandExecutor.executeCommand(command);

        System.out.println("adb pull:");
        System.out.println("return: " + value);
        System.out.println("output: " + commandExecutor.getStandardOutputFromCommand());

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return value;

    }

    private void deletePicture(String picture, String directory) {

        String filePath = directory.replace(" ", "\\ ") + "/" + picture;

        List<String> command = new ArrayList<String>();
        command.add("adb");
        command.add("shell");
        command.add("rm");
        command.add(filePath);

        int value = commandExecutor.executeCommand(command);
        System.out.println("delete:");
        System.out.println("output: " + commandExecutor.getStandardOutputFromCommand().toString());
        System.out.println("error: " + commandExecutor.getStandardErrorFromCommand().toString());
        System.out.println("return: " + value);
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

    public void takeAndTransferImg() {

        inputKeyevent(Keycode.VOLUMEDOWN);
        transferPictures(true);

    }
}