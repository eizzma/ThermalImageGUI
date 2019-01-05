package thermalimage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
        primaryStage.setTitle("Thermal Image");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {

        //launch(args);


        // CWD: System.out.println("cwd: "+ System.getProperty("user.dir"));

        PythonExecutor pythonProgramm = new PythonExecutor();
        pythonProgramm.run("test.py", null);

        String ipAddress = "192.168.178.22";

        AdbExecutor adbExecutor = new AdbExecutor(ipAddress);
        adbExecutor.connect();
        //adbExecutor.devices();

        //adbExecutor.keyEvent(1,1,Keycode.VOLUMEUP);
        //adbExecutor.keyEvent(2,1,Keycode.MENU);
        System.out.println("list pictures");
        List<String> pictures = adbExecutor.listPictures("/sdcard/DCIM/Camera");
        adbExecutor.transferPictures("/Volumes/DiePlatte/test", "/sdcard/DCIM/Camera", pictures, false);

    }
}
