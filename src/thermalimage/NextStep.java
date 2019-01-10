package thermalimage;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class NextStep {

    static void display(String message, Stage mainWindow, Scene nextScene) {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("");
        window.setMinWidth(400);

        Label messageLabel = new Label();
        messageLabel.setText(message);
        Button okButton = new Button("weiter");
        okButton.setOnAction(event -> {
            window.close();
            mainWindow.setScene(nextScene);
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(messageLabel, okButton);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
