package thermalimage;

import java.io.File;
import java.util.*;

public class AdbExecutor extends SystemCommandExecutor {

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
        System.out.println("ADB connection: " + commandExecutor.getStandardOutputFromCommand());
        System.out.println("\treturnvalue: " + returnvalue);

        // the return value seems to be still zero even if the connection failed

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

            public void run() {
                System.out.println("picture number: " + timesexecuted);
                takeAndTransferImg();

                timesexecuted++;
                if (timesexecuted > duration) {
                    timer.cancel();
                }
            }
        };
        timer.schedule(repeatedTask, 0, Settings.timer * 1000);

    }

    private void inputKeyevent(Keycode keycode) {

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

    void transferPictures() {

        String destinationDirectory = Projects.getActiveExperimentDirectory();

        List<String> pictures = listPictures();

        for (String picture : pictures) {

            File img = new File(imgPath + "/" + picture);
            File destination = new File(destinationDirectory + "/" + picture);

            adbPull(destination.getAbsolutePath(), img.getAbsolutePath());

        }

    }

    void backgroundImg() {
        String pathForBackgroundImg;
        if (!Settings.projectPath.endsWith("/")) {
            pathForBackgroundImg = Settings.projectPath + "/" + Projects.activeProject + "/";
        } else {
            pathForBackgroundImg = Settings.projectPath + Projects.activeProject + "/";
        }

        inputKeyevent(Keycode.VOLUMEDOWN);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> list = listPictures();
        for (String potentialBackground : list) {
            if (potentialBackground.endsWith("orig.png")) { // only orig picture is interesting for background
                adbPull(pathForBackgroundImg + "/background.png"
                        , imgPath + "/" + potentialBackground);
            } else {
                deletePicture(potentialBackground);
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

    private void deletePicture(String picture) {

        String filePath = imgPath.replace(" ", "\\ ") + "/" + picture;

        List<String> command = new ArrayList<String>();
        command.add("adb");
        command.add("shell");
        command.add("rm");
        command.add(filePath);

        int value = commandExecutor.executeCommand(command);
        // System.out.println("delete:");
        // System.out.println("output: " + commandExecutor.getStandardOutputFromCommand().toString());
        // System.out.println("error: " + commandExecutor.getStandardErrorFromCommand().toString());
        // System.out.println("return: " + value);
    }

    public void deletePictures() {

        List<String> pictureList = listPictures();
        List<String> command = new ArrayList<String>();
        command.add("adb");
        command.add("shell");
        command.add("rm");
        command.add("placeholder");

        for (String picture : pictureList) {
            String filePath = imgPath.replace(" ", "\\ ") + "/" + picture;
            command.set(3, filePath);
            int value = commandExecutor.executeCommand(command);
        }
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

        // first of all lets delete all the old Pictures in the Directory
     //   List<String> oldPictures = listPictures();
     //   for (String oldPicture : oldPictures){
     //       deletePicture(oldPicture);
     //   }

        // then take the new picture
        inputKeyevent(Keycode.VOLUMEDOWN);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        transferPictures();

    }
}