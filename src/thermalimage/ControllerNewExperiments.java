package thermalimage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ControllerNewExperiments extends VBox {

    private PythonExecutor pythonExecutor;

    private double progressbarlevel;

    private Timeline progressbarTimeLine;

    private AdbExecutor adbExecutor;

    @FXML
    private Label labelName;

    @FXML
    private Label labelInstruction;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button backgroundButton;

    @FXML
    private Button initialImageinitialImageButton;

    @FXML
    private Button startExperimentstartExperimentButton;

    @FXML
    private Button abbortButton;


    public ControllerNewExperiments() {

        // labelName.setText(Projects.activeProject + " " + Projects.activeExperiment);

        adbExecutor = new AdbExecutor();
        adbExecutor.connect();
        //   if (connectOutput.contains("failed")){
        //       SceneManager.displayError(connectOutput);
        //       SceneManager.showProjectScene();
        //   }

        progressbarlevel = 0.0;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newExperiment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        startExperimentstartExperimentButton.setDisable(true);

        File background = new File(Projects.getActiveProjectDirectory() + "/background.png");

        if (!background.exists()) {
            initialImageinitialImageButton.setDisable(true);
            labelInstruction.setText("Bitte machen Sie zuerst ein Hintergrundbild!");
        } else {
            updateProgressBar(0.1);
            labelInstruction.setText("Bitte machen Sie ein Bild vom unberührten Objekt");
        }

    }

    @FXML
    private void backgroundImg() {

        adbExecutor.backgroundImg();

        initialImageinitialImageButton.setDisable(false);
        updateProgressBar(0.1);

        labelInstruction.setText("Bitte machen Sie ein Bild vom unberührten Objekt");

    }

    @FXML
    private void initialImage() {

        adbExecutor.takeAndTransferImg();

        startExperimentstartExperimentButton.setDisable(false);
        updateProgressBar(0.1);

        labelInstruction.setText("Berühren Sie nun das Objekt die gewünschte Zeit lang und starten sie direkt im Anschluss die Messung");

    }

    @FXML
    private void startExperiment() {

        initialImageinitialImageButton.setDisable(true);
        startExperimentstartExperimentButton.setDisable(true);
        backgroundButton.setDisable(true);

        double progressLevel = 0.8 / (double) (Settings.duration / Settings.timer);
        System.out.println("progressLevel " + progressLevel);
        // adbExecutor.executeExperiment();

        progressbarTimeLine = new Timeline(new KeyFrame(Duration.seconds(Settings.timer), event -> {
            System.out.println("generated by timeline at: " + ZonedDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy_HH:mm")));
            updateProgressBar(progressLevel);
            adbExecutor.takeAndTransferImg();
        }));
        progressbarTimeLine.setCycleCount(Settings.duration / Settings.timer);
        progressbarTimeLine.play();
        progressbarTimeLine.setOnFinished(event -> {
            SceneManager.showProjectScene();
            // TODO run python
        });

    }

    @FXML
    private void abbort() {

        // TODO abbrechen wenn timeline noch nicht gestartet wurde

        progressbarTimeLine.stop();

        SceneManager.showMainScene();

    }

    private void updateProgressBar(double newProgress) {

        progressbarlevel = progressbarlevel + newProgress;

        progressBar.setProgress(progressbarlevel);

    }
}