package thermalimage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

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

        System.out.println(list.getSelectionModel().getSelectedItem().toString());

    }

    @FXML
    private void showExperiment() {

        // TODO new scene

    }

    @FXML
    private void newExperiment() {

        String timeStamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy_HH:mm"));

        if (Projects.addNewExperiment(timeStamp)){ // returns true if the directory has been created
            // then the timestamp should be added to displayed list.
            list.getItems().add(timeStamp);
        }

        SceneManager.takeBackgroundImage();

  //     AdbExecutor adbExecutor = new AdbExecutor();

  //     adbExecutor.connect();
  //     for (String picture : adbExecutor.listPictures()){
  //         System.out.println("/sdcard/DCIM/Google Photos" + "/" + picture);
  //     }
  //     // TODO message POP-UP: taking backgound image
  //     adbExecutor.backgroundImg();
  //     // TODO message POP-UP: before image
  //     // TODO taking pictures and transferring them
  //     adbExecutor.transferPictures(true);


        // TODO run python for new files

    }

    @FXML
    private void deleteProject() {

        Projects.deleteProject();

    }

    @FXML
    private void deleteExperiment() {

        String experimentToBeDeleted = list.getSelectionModel().getSelectedItem().toString();

        if (Projects.deleteExperiment(experimentToBeDeleted)){
            int index = list.getSelectionModel().getSelectedIndex();
            list.getItems().remove(index);
        }else {
            SceneManager.displayError("Der Versuch konnte nicht gelöscht werden.");
        }

    }



}
