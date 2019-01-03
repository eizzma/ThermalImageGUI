package thermalimage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main {//extends Application {
/*
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
        primaryStage.setTitle("Thermal Image");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }
*/

    public static void main(String[] args) throws Exception {

        //launch(args);


        // CWD: System.out.println("cwd: "+ System.getProperty("user.dir"));

        PythonProgramm pythonProgramm = new PythonProgramm();
        pythonProgramm.run("test.py");

    }
}
