package thermalimage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.swing.text.html.ImageView;
import javax.swing.text.html.ListView;
import java.io.IOException;

public class ControllerView extends VBox {



    @FXML
    private Button okButton;

    @FXML
    private Button abbruch;

    @FXML
    private ImageView imageView;

    @FXML
    private ListView listCelsius;

    @FXML
    private ListView listPictures;

    @FXML
    private TextField textField;

    @FXML
    private SplitPane splitPane;


    public ControllerView(){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewExperiment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }



    }


}

