package thermalimage.gui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import thermalimage.Projects;
import thermalimage.codeExecution.PythonExecutor;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ControllerProject extends VBox {

    @FXML
    private Label label;

    @FXML
    private ListView list;

    @FXML
    private Button showExperiment;

    @FXML
    private Button evaluateExperiment;

    @FXML
    private Button deleteExperiment;

    private SimpleListProperty<String> listProperty = new SimpleListProperty(FXCollections.<String>observableArrayList());

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

        showExperiment.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
        deleteExperiment.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
        evaluateExperiment.disableProperty().bind(Bindings.size(list.getItems()).lessThan(3));

    }

    @FXML
    private void backToMainScreen() {

        SceneManager.showMainScene();

    }

    @FXML
    private void evaluate() {

        PythonExecutor pythonExecutor = new PythonExecutor();
        pythonExecutor.run("evaluateProject.py", Projects.getActiveProjectDirectory());

    }

    @FXML
    private void showExperiment() {

        Projects.activeExperiment = list.getSelectionModel().getSelectedItem().toString();
        SceneManager.showViewExperiment();

    }

    @FXML
    private void newExperiment() {

        String timeStamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy_HH-mm"));

        if (Projects.addNewExperiment(timeStamp)){ // returns true if the directory has been created
            // then the timestamp should be added to displayed list.
            list.getItems().add(timeStamp);
            SceneManager.showNewExperimentScene();

        }else {
            SceneManager.displayError("konnte keinen neuen Ordner erstellen");
        }

    }

    @FXML
    private void deleteProject() throws IOException {

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
