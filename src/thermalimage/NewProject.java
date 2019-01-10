package thermalimage;

import com.sun.tools.internal.xjc.model.CEnumConstant;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class NewProject {

    static void display(Stage mainWindow, Scene nextScene) {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        Scene oldScene = mainWindow.getScene();
        double width = mainWindow.getWidth();
        double height = mainWindow.getHeight() - 22; // mac os 22 pixel window title

        window.setMinWidth(width / 2);
        window.setMinHeight(height / 2);

        Label messageLabel = new Label();
        messageLabel.setText("Geben Sie dem neuen Projekt einen Namen:");
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);
        textField.setMaxWidth(200);

        Button okButton = new Button();
        okButton.setText("Bestätigen");
        okButton.setOnAction(e -> {
            window.close();
            mainWindow.setScene(oldScene);
            System.out.println(textField.getText());
        });

        Button denyButton = new Button();
        denyButton.setText("Abbrechen");
        denyButton.setOnAction(e -> {
            window.close();
        });

        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(okButton, denyButton);

        VBox layout = new VBox(30);
        layout.getChildren().addAll(messageLabel, textField, buttons);
        layout.setAlignment(Pos.CENTER);

        window.setScene(new Scene(layout));
        window.showAndWait();

    }
}
