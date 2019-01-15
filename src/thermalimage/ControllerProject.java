package thermalimage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ControllerProject extends VBox {

    @FXML
    private Label label;

    @FXML
    private ListView list;

    public ControllerProject() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("projectScene.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        label.setText("Projekt: \"" + Projects.activeProject + "\"");

        for (String experiment : Projects.getExperiments()) {
            list.getItems().add(experiment);
        }

        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    @FXML
    private void backToMainScreen() {

        SceneManager.showMainScene();

    }

    @FXML
    private void evaluate() {

        System.out.println(getSelectedItem());

    }

    @FXML
    private void showExperiment() {

        // TODO new scene
        PythonExecutor pythonExecutor = new PythonExecutor();

        String selected = getSelectedItem();
        selected = Projects.getActiveProjectDirectory() + "/" + selected;

        File experimentDirectory = new File(selected);
        File[] pictureList = experimentDirectory.listFiles();
        for (File picture : pictureList){
            pythonExecutor.run("images.py",picture.getAbsolutePath());
        }


    }

    @FXML
    private void newExperiment() {


        String timeStamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy_HH:mm"));

        if (Projects.addNewExperiment(timeStamp)){ // returns true if the directory has been created
            // then the timestamp should be added to displayed list.
            list.getItems().add(timeStamp);
        }


        SceneManager.showNewExperimentScene();

  //     for (String picture : adbExecutor.listPictures()){
  //         System.out.println("/sdcard/DCIM/Google Photos" + "/" + picture);
  //     }
  //
  //     adbExecutor.backgroundImg();
  //
  //     adbExecutor.transferPictures(true);


    }

    @FXML
    private void deleteProject() throws IOException {

        Projects.deleteProject();

    }

    @FXML
    private void deleteExperiment() {

        String experimentToBeDeleted = getSelectedItem();

        if (Projects.deleteExperiment(experimentToBeDeleted)){
            int index = list.getSelectionModel().getSelectedIndex();
            list.getItems().remove(index);
        }else {
            SceneManager.displayError("Der Versuch konnte nicht gelöscht werden.");
        }

    }

    private String getSelectedItem(){

        return list.getSelectionModel().getSelectedItem().toString();

    }

}
