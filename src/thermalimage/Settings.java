package thermalimage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;

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
        window.setMinWidth(600);

        Label durationLabel = new Label();
        durationLabel.setText("Versuchsdauer:");
        TextField durationTextField = numeric(new TextField());
        durationTextField.setAlignment(Pos.CENTER);
        durationTextField.setText("" + duration);

        Label timerLabel = new Label();
        timerLabel.setText("Auslöseintervall: ");
        TextField timerTextField = numeric(new TextField());
        timerTextField.setAlignment(Pos.CENTER);
        timerTextField.setText("" + timer);

        Label pythonLabel = new Label();
        pythonLabel.setText("Pfad zu den Pythonprogrammen:");
        TextField pythonTextField = new TextField();
        pythonTextField.setText(pythonPath);

        Label projectLabel = new Label();
        projectLabel.setText("Pfad zu den Projekten:");
        TextField projectTextField = new TextField();
        projectTextField.setText(projectPath);

        Label ipLabel = new Label();
        ipLabel.setText("Ip Addresse");
        TextField ipTextField = new TextField();
        ipTextField.setText(ipAddress);
        ipTextField.setAlignment(Pos.CENTER);

        Button button = new Button("ok");
        button.setOnAction(e -> {
            duration = Integer.parseInt(durationTextField.getText());
            timer = Integer.parseInt(timerTextField.getText());
            pythonPath = pythonTextField.getText();
            if (!pythonPath.endsWith("/")) {
                pythonPath = pythonPath + "/";
            }
            projectPath = projectTextField.getText();
            ipAddress = ipTextField.getText();
            System.out.println("duration: " + duration + ", timer: " + timer + ", pythonPath: " + pythonPath +
                    ", Ip Addresse: " + ipAddress);
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(durationLabel, durationTextField, timerLabel, timerTextField,
                pythonLabel, pythonTextField, projectLabel, projectTextField, ipLabel, ipTextField, button);
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
}
