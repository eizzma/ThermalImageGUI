package thermalimage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProgressBarScene {

    Stage stage;

    ProgressBar progressBar;


    ProgressBarScene() {

        stage = new Stage();
        progressBar = new ProgressBar(0);
        progressBar.setPrefSize(250, 75);
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(progressBar);
        stage.setScene(new Scene(vBox, 300, 100));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();


    }

    void setProgressBar(double progress) {

        progressBar.setProgress(progress);

    }

    void closeProgressBar() {

        stage.close();

    }


}
