package thermalimage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {

    public Controller() {

    }

    @FXML
    private void initialize() {
        //Projects projects = new Projects();
    }

    @FXML
    private void loadProject() {

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
    private void newProject(ActionEvent event) throws IOException {
        Parent rootLayout = FXMLLoader.load(getClass().getResource("projectPage.fxml"));
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        NewProject.display(stageTheEventSourceNodeBelongs, rootLayout.getScene());

    }

    @FXML
    private void backToMainScreen(ActionEvent event) throws IOException {
        Parent rootLayout = FXMLLoader.load(getClass().getResource("thermalImageStart.fxml"));
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.setScene(new Scene(rootLayout));
    }

    @FXML
    private void settings() {
        Settings.display();
    }

    @FXML
    private void startexperiment() {

    }


    private void renameBackground(File file) {
        file.renameTo(new File("background.png"));
    }

}