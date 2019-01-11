package thermalimage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ControllerProject extends VBox {

    @FXML
    private Label label;

    @FXML
    ListView list;

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
            System.out.println(Projects.activeProject + " + " + experiment);
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

    }

    @FXML
    private void newExperiment() {

    }

    @FXML
    private void deleteProject() {

    }

    @FXML
    private void deleteExperiment() {

    }


}
