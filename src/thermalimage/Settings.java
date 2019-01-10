package thermalimage;

import com.sun.tools.internal.xjc.model.CEnumConstant;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.stage.Window;

import java.io.File;

class Settings {

    static String ipAddress = "10.90.1.223";

    static int duration = 60;

    static int timer = 2;

    static String pythonPath = "/Volumes/DiePlatte/uni/WS18_19/DropBoxTeamordner/ThermalImageGUI/python/";

    static String projectPath = "/Volumes/DiePlatte/uni/WS18_19/DropBoxTeamordner/ThermalImageGUI/thermalImageProjects";

    static void display() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Einstellungen");
        window.setMinWidth(500);
        window.setMinHeight(500);

        Label durationLabel = new Label();
        durationLabel.setText("Versuchsdauer:");
        TextField durationTextField = numeric(new TextField());
        appearanceTextField(durationTextField, "" + duration);
        durationTextField.setMaxWidth(50);

        Label timerLabel = new Label();
        timerLabel.setText("Auslöseintervall: ");
        TextField timerTextField = numeric(new TextField());
        appearanceTextField(timerTextField, "" + timer);
        timerTextField.setMaxWidth(50);

        Label pythonLabel = new Label();
        pythonLabel.setText("Pfad zu den Pythonprogrammen:");
        TextField pythonTextField = new TextField();
        appearanceTextField(pythonTextField, pythonPath);
        HBox pythonBox = browse("Pfad zu den Pythonprogrammen:", pythonPath, pythonTextField);

        Label projectLabel = new Label();
        projectLabel.setText("Pfad zu den Projekten:");
        TextField projectTextField = new TextField();
        appearanceTextField(projectTextField, projectPath);
        HBox projectBox = browse("Pfad zu den Projekten:", projectPath, projectTextField);

        Label ipLabel = new Label();
        ipLabel.setText("Ip Addresse");
        TextField ipTextField = new TextField();
        appearanceTextField(ipTextField, ipAddress);
        ipTextField.setMaxWidth(200);

        Button okButton = new Button("Bestätigen");
        okButton.setOnAction(e -> {
            duration = Integer.parseInt(durationTextField.getText());
            timer = Integer.parseInt(timerTextField.getText());
            pythonPath = pythonTextField.getText();
            if (!pythonPath.endsWith("/")) {
                pythonPath = pythonPath + "/";
            }
            projectPath = projectTextField.getText();
            ipAddress = ipTextField.getText();
            System.out.println("duration: " + duration + ", timer: " + timer + ", pythonPath: " + pythonPath +
                    ", projectPath: " + projectPath + ", Ip Addresse: " + ipAddress);
            window.close();
        });

        // DirectoryChooser chooser = new DirectoryChooser();
        // chooser.setTitle("JavaFX Projects");
        // File defaultDirectory = new File("c:/dev/javafx");
        // chooser.setInitialDirectory(defaultDirectory);
        // File selectedDirectory = chooser.showDialog(new Stage());

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

    private static String directoryChooser(File defaultDirectory){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(new Stage());
        return selectedDirectory.toString();
    }
}
