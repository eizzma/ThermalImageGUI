package thermalimage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

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
        AdbExecutor adbExecutor = new AdbExecutor();
        adbExecutor.keyEvent(Keycode.M);
    }

    @FXML
    private void python() {
        PythonExecutor pythonExecutor = new PythonExecutor();
        System.out.println("result: " + pythonExecutor.run("test.py", null));
    }

    @FXML
    private void adb() {
        AdbExecutor adbExecutor = new AdbExecutor();
        //adbExecutor.connect();


        List<String> list = new ArrayList<>();
        list = adbExecutor.listPictures();

        //adbExecutor.testTransfer();
        adbExecutor.transferPictures(false);

    }

    @FXML
    private void newProject(ActionEvent event){
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
        NewProject.display(stageTheEventSourceNodeBelongs,null);
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