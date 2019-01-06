package thermalimage;

import javafx.fxml.FXML;

import java.awt.*;
import java.net.URL;

public class Controller {

    //TODO call methods corresponding to button event

    @FXML
    private TextArea outputText;

    @FXML
    private URL location;

    public Controller(){

    }

    @FXML
    private void initialize(){

    }

    @FXML
    private void printOutput(){
        System.out.println("cwd: "+ System.getProperty("user.dir"));
    }

    @FXML
    private void python(){
        PythonExecutor pythonExecutor = new PythonExecutor();
        System.out.println("result: " + pythonExecutor.run("test.py", null));
    }

    @FXML
    private void adb(){
        AdbExecutor adbExecutor = new AdbExecutor("192.168.178.22");
        adbExecutor.connect();
    }

}