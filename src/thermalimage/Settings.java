package thermalimage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class Settings {

    public static int duration = 60;

    public static int timer = 2;

    public static String pythonPath = "/Volumes/DiePlatte/uni/WS18_19/DropBoxTeamordner/ThermalImageGUI/python/";

    public static String projectPath = "/Volumes/DiePlatte/uni/WS18_19/DropBoxTeamordner/ThermalImageGUI/thermalImageProjects";

    public static void display() {
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

        Button button = new Button("ok");
        button.setOnAction(e -> {
            duration = Integer.parseInt(durationTextField.getText());
            timer = Integer.parseInt(timerTextField.getText());
            pythonPath = pythonTextField.getText();
            if (!pythonPath.endsWith("/")){
                pythonPath = pythonPath + "/";
            }
            projectPath = projectTextField.getText();
            System.out.println("duration: " + duration + ", timer: " + timer + ", pythonPath: " + pythonPath);
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(durationLabel, durationTextField, timerLabel, timerTextField,
                pythonLabel, pythonTextField, projectLabel, projectTextField, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static TextField numeric(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        return textField;
    }
}
