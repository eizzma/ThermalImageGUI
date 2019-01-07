package thermalimage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class Settings {

    public static void display(){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Einstellungen");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("test");
        Button button = new Button("ok");
        TextField text = new TextField();
        button.setOnAction(e -> {
            String test = text.getText();
            System.out.println(test);
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, button, text);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
