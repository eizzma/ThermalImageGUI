package thermalimage;

import com.sun.tools.internal.xjc.model.CEnumConstant;
import com.sun.tools.internal.xjc.model.CEnumLeafInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    static String newProject = null;

    @FXML


    static String display(Stage mainWindow, Scene scene) {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        window.setMinWidth(300);
        window.setMinHeight(300);

        Label messageLabel = new Label();
        messageLabel.setText("Geben Sie dem neuen Projekt einen Namen:");
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);
        textField.setMaxWidth(200);

        Button okButton = new Button();
        okButton.setText("Bestätigen");
        okButton.setOnAction(e -> {
            newProject = textField.getText();
            if (newProject.length() < 3){
                errorMessage();
            }else {
                mainWindow.setScene(scene);
                window.close();
            }
        });

        Button denyButton = new Button();
        denyButton.setText("Abbrechen");
        denyButton.setOnAction(e -> {
            newProject = null;
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

        return newProject;

    }

    private static void errorMessage(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinWidth(100);
        stage.setMinHeight(100);
        Label label = new Label("Der Projektname muss mindestens 3 Zeichen betragen!");
        Button button = new Button("OK");
        button.setOnAction(event -> stage.close());
        VBox vBox = new VBox(5);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, button);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.showAndWait();
    }

}
