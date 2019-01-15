package thermalimage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new SceneManager(primaryStage);

        SceneManager.showMainScene();

    }



    public static void main(String[] args) {

        new Projects();
        launch(args);



        // CWD: System.out.println("cwd: "+ System.getProperty("user.dir"));
        // PythonExecutor pythonProgramm = new PythonExecutor();
        // pythonProgramm.run("test.py", null);
        // String ipAddress = "192.168.178.22";
        // AdbExecutor adbExecutor = new AdbExecutor(ipAddress);
        // adbExecutor.connect();
        // adbExecutor.devices();
        // adbExecutor.executeExperiment(1,1,Keycode.VOLUMEUP);
        // adbExecutor.executeExperiment(2,1,Keycode.MENU);
        // System.out.println("list pictures");
        // List<String> pictures = adbExecutor.listPictures("/sdcard/DCIM/Camera");
        // adbExecutor.transferPictures("/Volumes/DiePlatte/test", "/sdcard/DCIM/Camera", pictures, false);

    }
}