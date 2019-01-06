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
        System.out.println("Hello World");
    }

    @FXML
    private void python(){
        PythonExecutor pythonExecutor = new PythonExecutor();
        pythonExecutor.run("test.py", null);
    }

}