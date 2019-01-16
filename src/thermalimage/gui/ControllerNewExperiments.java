package thermalimage.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import thermalimage.codeExecution.AdbExecutor;
import thermalimage.Projects;
import thermalimage.codeExecution.Keycode;
import thermalimage.codeExecution.PythonExecutor;
import thermalimage.Settings;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ControllerNewExperiments extends VBox {

    private double progressbarlevel;

    private Timeline timeLine;

    private PythonExecutor pythonExecutor;

    private AdbExecutor adbExecutor;

    private List<String> newPictures;

    private List<String> oldPictures;

    private List<String> pulledPictures;

    @FXML
    private Label labelName;

    @FXML
    private Label labelInstruction;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button backgroundButton;

    @FXML
    private Button initialImageinitialImageButton; //gh

    @FXML
    private Button startExperimentstartExperimentButton;

    @FXML
    private Button abbortButton;

    @FXML
    private Button backToProject;


    public ControllerNewExperiments() {

        oldPictures = new ArrayList<>();
        newPictures = new ArrayList<>();
        pulledPictures = new ArrayList<>();

        labelName.setText(Projects.activeProject + " " + Projects.activeExperiment);

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

        abbortButton.setDisable(true);
        startExperimentstartExperimentButton.setDisable(true);

        File background = new File(Projects.getActiveProjectDirectory() + "/background.png");

        if (!background.exists()) {
            initialImageinitialImageButton.setDisable(true);
            labelInstruction.setText("Bitte machen Sie zuerst ein Hintergrundbild!");
        } else {
            updateProgressBar(0.1);
            labelInstruction.setText("Bitte machen Sie ein Bild vom unberührten Objekt");
        }

        pythonExecutor = new PythonExecutor();

    }

    @FXML
    private void backgroundImg() {

        oldPictures = adbExecutor.listPictures();

        adbExecutor.delete(oldPictures);

        oldPictures = new ArrayList<>();

        adbExecutor.backgroundImg();

        initialImageinitialImageButton.setDisable(false);
        updateProgressBar(0.1);

        labelInstruction.setText("Bitte machen Sie ein Bild vom unberührten Objekt");

    }

    @FXML
    private void initialImage() throws InterruptedException {

        oldPictures = adbExecutor.listPictures();

        adbExecutor.inputKeyevent(Keycode.VOLUMEDOWN);

        Thread.sleep(500);

        newPictures = adbExecutor.listPictures();

        pulledPictures = adbExecutor.pullPictures(newPictures);

        startExperimentstartExperimentButton.setDisable(false);
        updateProgressBar(0.1);

        labelInstruction.setText("Berühren Sie nun das Objekt die gewünschte Zeit lang und starten sie direkt im Anschluss die Messung");

    }

    @FXML
    private void startExperiment() {

        initialImageinitialImageButton.setDisable(true);
        startExperimentstartExperimentButton.setDisable(true);
        backgroundButton.setDisable(true);
        backToProject.setDisable(true);
        abbortButton.setDisable(false);

        double progressLevel = 0.8 / (double) (Settings.duration / Settings.timer);

        timeLine = new Timeline(new KeyFrame(Duration.seconds(Settings.timer), event -> {

            adbExecutor.inputKeyevent(Keycode.VOLUMEDOWN);              //   FOTO                   FOTO2
            adbExecutor.delete(oldPictures);                            //   lösche BKG             lösche initial
            pythonExecutor.evaluatePictures(pulledPictures);            //   evaluiere initial      evaluiere FOTO
            updateProgressBar(progressLevel);                           //   upgrade                --
            oldPictures = newPictures;                                  //   alte Bilder = initial  alteBilder = FOTO
            newPictures = adbExecutor.listPictures();                   //   neubild =FOTO+initial  neu Bilder = FOTO2+1
            newPictures.remove(oldPictures);                            //   neubild =FOTO          neu Bilder = FOTO2
            pulledPictures = adbExecutor.pullPictures(newPictures);     //   pull FOTO              pull FOTO2


            // if (oldPictures.isEmpty()) {
            //     oldPictures = adbExecutor.listPictures();   // scan for old pictures
            //     adbExecutor.inputKeyevent(Keycode.VOLUMEDOWN);
            // } else {
            //     adbExecutor.inputKeyevent(Keycode.VOLUMEDOWN);
            //     adbExecutor.delete(oldPictures);
            //     newPictures.remove(oldPictures);
            //     oldPictures = newPictures;
            //     pythonExecutor.evaluatePictures(pulledPictures);
            // }
            // newPictures = adbExecutor.listPictures();
            // pulledPictures = adbExecutor.pullPictures(oldPictures);

            // adbExecutor.takeAndTransferImg();
        }));
        timeLine.setCycleCount(Settings.duration / Settings.timer);
        timeLine.play();
        timeLine.setOnFinished(event -> {

            adbExecutor.delete(newPictures);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pythonExecutor.evaluatePictures(pulledPictures);


            SceneManager.showProjectScene();

            //   File experimentDirectory = new File(Projects.getActiveExperimentDirectory());
            //   File[] pictureList = experimentDirectory.listFiles();
            //   for (File picture : pictureList) {
            //       pythonExecutor.run("images.py", picture.getAbsolutePath());
            //   }

        });

    }

    @FXML
    private void abbort() {

        timeLine.stop();

        // back to projects Scene and delete the active Project
        Projects.deleteExperiment(Projects.activeExperiment);
        Projects.activeExperiment = null;

        SceneManager.showProjectScene();

    }

    private void updateProgressBar(double newProgress) {

        progressbarlevel = progressbarlevel + newProgress;

        progressBar.setProgress(progressbarlevel);

    }

    @FXML
    private void backToProject() {

        Projects.deleteExperiment(Projects.activeExperiment);
        Projects.activeExperiment = null;

        SceneManager.showProjectScene();

    }
}
