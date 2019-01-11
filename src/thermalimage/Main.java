package thermalimage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private double width = 600;

    private double heigth = 600;

    Stage window;
    Scene scene1, scene2;

    @Override
    public void start(Stage primaryStage) throws Exception {
        new SceneManager(primaryStage);

        SceneManager.showMainScene();

        // window = primaryStage;
        // Parent rootLayout = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
        // window.setTitle("Thermal Image");
        // Scene mainScene  = new Scene(rootLayout, width, heigth);
        // window.setScene(mainScene);
        // window.show();
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
        // adbExecutor.keyEvent(1,1,Keycode.VOLUMEUP);
        // adbExecutor.keyEvent(2,1,Keycode.MENU);
        // System.out.println("list pictures");
        // List<String> pictures = adbExecutor.listPictures("/sdcard/DCIM/Camera");
        // adbExecutor.transferPictures("/Volumes/DiePlatte/test", "/sdcard/DCIM/Camera", pictures, false);

    }
}