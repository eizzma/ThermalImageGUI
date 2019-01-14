package thermalimage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class SceneManager {

    private static Stage window;

    SceneManager(Stage window) {
        this.window = window;
    }

    public static void showMainScene() {
        ControllerMain controllerMain = new ControllerMain();
        window.setScene(new Scene(controllerMain, 600, 600));
        if (!window.isShowing()) {
            window.show();
        }
    }

    public static void showProjectScene() {
        ControllerProject controllerProject = new ControllerProject();
        window.setScene(new Scene(controllerProject, 600, 600));
    }

    static void displayNewProject() {

        Stage newProject = new Stage();
        newProject.initModality(Modality.APPLICATION_MODAL);

        double width = window.getWidth();
        double height = window.getHeight() - 22; // mac os 22 pixel window title

        newProject.setMinWidth(width / 2);
        newProject.setMinHeight(height / 2);

        Label messageLabel = new Label();
        messageLabel.setText("Geben Sie dem neuen Projekt einen Namen:");
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);
        textField.setMaxWidth(200);

        Button okButton = new Button();
        okButton.setText("Bestätigen");
        okButton.setOnAction(e -> {
            if (textField.getText().length() < 3) {
                displayError("Der Projektname muss mindestens drei Zeichen betragen!");
            } else {
                newProject.close();
                Projects.addNewProject(textField.getText());
                showProjectScene();
            }
        });

        Button denyButton = new Button();
        denyButton.setText("Abbrechen");
        denyButton.setOnAction(e -> {
            newProject.close();
        });

        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(okButton, denyButton);

        VBox layout = new VBox(30);
        layout.getChildren().addAll(messageLabel, textField, buttons);
        layout.setAlignment(Pos.CENTER);

        newProject.setScene(new Scene(layout));
        newProject.showAndWait();

    }

    static void displaySettings() {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Einstellungen");
        window.setMinWidth(500);
        window.setMinHeight(500);

        Label durationLabel = new Label();
        durationLabel.setText("Versuchsdauer:");
        TextField durationTextField = numeric(new TextField());
        appearanceTextField(durationTextField, "" + Settings.duration);
        durationTextField.setMaxWidth(50);

        Label timerLabel = new Label();
        timerLabel.setText("Auslöseintervall: ");
        TextField timerTextField = numeric(new TextField());
        appearanceTextField(timerTextField, "" + Settings.timer);
        timerTextField.setMaxWidth(50);

        Label pythonLabel = new Label();
        pythonLabel.setText("Pfad zu den Pythonprogrammen:");
        TextField pythonTextField = new TextField();
        appearanceTextField(pythonTextField, Settings.pythonPath);
        HBox pythonBox = browse("Pfad zu den Pythonprogrammen:", Settings.pythonPath, pythonTextField);

        Label projectLabel = new Label();
        projectLabel.setText("Pfad zu den Projekten:");
        TextField projectTextField = new TextField();
        appearanceTextField(projectTextField, Settings.projectPath);
        HBox projectBox = browse("Pfad zu den Projekten:", Settings.projectPath, projectTextField);

        Label ipLabel = new Label();
        ipLabel.setText("Ip Addresse");
        TextField ipTextField = new TextField();
        appearanceTextField(ipTextField, Settings.ipAddress);
        ipTextField.setMaxWidth(200);

        Button okButton = new Button("Bestätigen");
        okButton.setOnAction(e -> {
            Settings.duration = Integer.parseInt(durationTextField.getText());
            Settings.timer = Integer.parseInt(timerTextField.getText());
            Settings.pythonPath = pythonTextField.getText();
            if (!Settings.pythonPath.endsWith("/")) {
                Settings.pythonPath = Settings.pythonPath + "/";
            }
            Settings.projectPath = projectTextField.getText();
            Settings.ipAddress = ipTextField.getText();
            System.out.println("duration: " + Settings.duration + ", timer: " + Settings.timer + ", pythonPath: "
                    + Settings.pythonPath + ", projectPath: " + Settings.projectPath + ", Ip Addresse: "
                    + Settings.ipAddress);
            Projects.scan();
            window.close();
        });

        Button denyButton = new Button();
        denyButton.setText("Abbrechen");
        denyButton.setOnAction(e -> window.close());

        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(okButton, denyButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(durationLabel, durationTextField, timerLabel, timerTextField,
                pythonBox, pythonTextField, projectBox, projectTextField, ipLabel, ipTextField, buttonBox);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private static TextField numeric(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        return textField;
    }

    private static void appearanceTextField(TextField textField, String text) {
        textField.setAlignment(Pos.CENTER);
        textField.setText(text);
    }

    private static HBox browse(String labelText, String path, TextField textField) {
        Label label = new Label();
        label.setText(labelText);
        HBox hBox = new HBox(15);
        Button browse = new Button();
        browse.setText("browse");
        browse.setOnAction(e -> {
            textField.setText(directoryChooser(new File(path)));
        });
        hBox.getChildren().addAll(label, browse);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private static String directoryChooser(File defaultDirectory) {
        DirectoryChooser chooser = new DirectoryChooser();
        if (defaultDirectory.exists()){
            chooser.setInitialDirectory(defaultDirectory);
        }
        File selectedDirectory = chooser.showDialog(new Stage());
        if (selectedDirectory.exists()){
            return selectedDirectory.toString();
        }else {
            return Settings.projectPath;
        }
    }

    static void displayLoadProject() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        ListView<String> list = new ListView<>();
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list.setPrefHeight(200);
        list.setPrefWidth(240);

        HBox buttonBox = new HBox(10);
        Button okButton = new Button("Bestätigen");
        okButton.setOnAction(event -> {
            stage.close();
            Projects.activeProject = list.getSelectionModel().getSelectedItem();
            showProjectScene();
        });
        Button denyButton = new Button("Abbrechen");
        denyButton.setOnAction(event -> stage.close());
        buttonBox.getChildren().addAll(okButton, denyButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15);
        layout.getChildren().addAll(list, buttonBox);
        layout.setAlignment(Pos.CENTER);

        for (String project : Projects.projectMap.keySet()) {
            list.getItems().add(project);
        }

        stage.setScene(new Scene(layout, 300, 300));
        stage.showAndWait();

    }

    static void displayError(String error) {
        Stage errorStage = new Stage();
        Label message = new Label(error);
        Button okButton = new Button("Verstanden");
        okButton.setOnAction(event -> errorStage.close());
        constructAndShowStage(errorStage, okButton, message);
    }

    static void takeBackgroundImage() {
        AdbExecutor adbExecutor = new AdbExecutor();
        adbExecutor.connect();
        Stage stepStage = new Stage();
        Label message = new Label("Zunächst muss ein Bild des Hintergrunds erstellt werden!");
        Button okButton = new Button("Auslöser");
        okButton.setOnAction(event -> {
            adbExecutor.backgroundImg();
            takeBeforeImage();
            stepStage.close();
        });
        constructAndShowStage(stepStage, okButton, message);
    }

    private static void takeBeforeImage() {
        AdbExecutor adbExecutor = new AdbExecutor();
        Stage stepStage = new Stage();
        Label message = new Label("Als nächstes machen Sie bitte ein Bild des initialen Zustandes, des Projektgegenstands");
        Button okButton = new Button("Auslöser");
        okButton.setOnAction(event -> {
            adbExecutor.takeAndTransferImg();
            takePicturesExperiment();
            stepStage.close();
        });
        constructAndShowStage(stepStage, okButton, message);

    }

    private static void takePicturesExperiment() {
        AdbExecutor adbExecutor = new AdbExecutor();
        Stage stepStage = new Stage();
        Label message = new Label("Berühren Sie nun den Gegenstand in dem gewünschten Zeitfenster und legen Sie ihn dann ab.");
        Button okButton = new Button("Auslöser");
        okButton.setOnAction(event -> {
            adbExecutor.executeExperiment();
            stepStage.close();
        });
        constructAndShowStage(stepStage, okButton, message);
    }

    static void constructAndShowStage(Stage stage, Button button, Label label) {
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(layout, 500, 120));
        stage.showAndWait();
    }
}
