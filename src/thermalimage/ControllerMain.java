package thermalimage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerMain extends GridPane {

    public ControllerMain() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainScene.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }


    @FXML
    private void loadProject() {
        SceneManager.displayLoadProject();
    }


    @FXML
    private void help() {
        AdbExecutor adbExecutor = new AdbExecutor();
        //adbExecutor.connect();


        List<String> list = new ArrayList<>();
        list = adbExecutor.listPictures();

        //adbExecutor.testTransfer();
        adbExecutor.transferPictures(false);

    }



    @FXML
    private void newProject(ActionEvent event) throws IOException {

        SceneManager.displayNewProject();
    }


    @FXML
    private void settings() {
        SceneManager.displaySettings();
    }



    private void renameBackground(File file) {
        file.renameTo(new File("background.png"));
    }

}