package thermalimage;

//import com.sun.tools.internal.xjc.model.CEnumConstant;
import com.sun.org.apache.bcel.internal.generic.GOTO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class NewProject {

    static void display(Stage mainWindow, Scene nextScene) {


        String path = "C:/Users/Valdrin/Desktop/";

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

            String iD = textField.getText();

            try {
                if(!checkDuplicate(iD, path)){
                    createFolder(iD, path);
                    window.close();
                    mainWindow.setScene(nextScene);
                }else{
                    System.out.println("Duplikat! Versuche es erneut.");
                    window.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Duplicate!");
                    alert.setContentText("Try again");
                    alert.showAndWait();
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }



            //System.out.println(textField.getText());
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

    public static void createFolder(String iD, String path) throws IOException {

        //String fileName = "test.jpeg";

        File dir = new File(path + iD);
        //File file = new File(path + iD + "/" + fileName);

        System.out.println(path);

        if(dir.mkdir()){
            System.out.println("Datei erstellt: " + dir.createNewFile());
        }else{
            System.out.println(dir + " Konnte nicht erstellt werden.");
        }
    }

    public static boolean checkDuplicate(String iD, String path) throws IOException{
        File file = new File(path + iD);
        if(!file.exists()){
            return false;
        }else{
            return true;
        }

    }
}
