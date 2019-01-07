package thermalimage;

import javafx.fxml.FXML;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {

    @FXML
    private TextArea outputText;

    @FXML
    private URL location;

    public Controller() {

    }

    @FXML
    private void initialize() {
        Projects projects = new Projects();

    }

    @FXML
    private void loadProject() {
        AdbExecutor adbExecutor = new AdbExecutor("test");
        adbExecutor.keyEvent(Keycode.M);
    }

    @FXML
    private void python() {
        PythonExecutor pythonExecutor = new PythonExecutor();
        System.out.println("result: " + pythonExecutor.run("test.py", null));
    }

    @FXML
    private void adb() {
        AdbExecutor adbExecutor = new AdbExecutor("192.168.178.22");
        adbExecutor.connect();
    }

    @FXML
    private void settings() {
        Settings.display();
    }

    @FXML
    private void startexperiment(){

    }


    private void renameBackground(File file) {
        file.renameTo(new File("background.png"));
    }

}