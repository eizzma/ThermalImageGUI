package thermalimage.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import thermalimage.Projects;
import thermalimage.ThermalData;
import thermalimage.codeExecution.PythonExecutor;

import java.io.*;

public class ControllerView extends VBox {

    private PythonExecutor pythonExecutor;

    String csvFile;

    ObservableList<ThermalData> thermalData;

    @FXML
    private Button okButton;

    @FXML
    private Button abbruch;

    @FXML
    private Button plotButton;

    @FXML
    private ImageView imageView;

    @FXML
    private TableView<ThermalData> tableView;

    @FXML
    private ListView listPictures;

    @FXML
    private TextField textField;

    @FXML
    private SplitPane splitPane;


    public ControllerView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("viewExperiment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        csvFile = Projects.getActiveExperimentDirectory() + "/data.csv";
        thermalData = readDataFromCSV();


        // DateTime Column
        TableColumn<ThermalData, String> dateTimeColumn = new TableColumn<>("DateTime");
        dateTimeColumn.setMinWidth(200);
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("date"));


        // Temp Column
        TableColumn<ThermalData, Double> tempColumn = new TableColumn<>("Temp");
        tempColumn.setMinWidth(100);
        tempColumn.setCellValueFactory(new PropertyValueFactory<>("temp"));


        // Seconds Column
        TableColumn<ThermalData, Double> secondsColumn = new TableColumn<>("Seconds");
        secondsColumn.setMinWidth(100);
        secondsColumn.setCellValueFactory(new PropertyValueFactory<>("seconds"));

        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setItems(thermalData);
        tableView.getColumns().addAll(tempColumn, secondsColumn); // show only temp and time in seconds

        tableView.setOnMouseClicked(event -> {
            SceneManager.displayError(tableView.getSelectionModel().getSelectedItem().getDate());
        });

        tableView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    SceneManager.displayError(tableView.getSelectionModel().getSelectedItem().getTemp()+"");
                }
                event.consume();
            }
        });



        //imageView = new ImageView("icons/test.jpg");

    }

    @FXML
    private void backToProject() {

        SceneManager.showProjectScene();
        Projects.activeExperiment = null;

    }

    @FXML
    private void showPlot(){

        tableView.getSelectionModel().clearSelection();
        File plot = new File(Projects.getActiveExperimentDirectory() + "/plot.png");
        System.out.println(plot.exists());
        imageView.setImage(new Image(plot.toURI().toString()));
        // imageView = new ImageView(Projects.getActiveExperimentDirectory() + "/plot.png");

    }


    private ObservableList<ThermalData> readDataFromCSV() {

        ObservableList<ThermalData> observableList = FXCollections.observableArrayList();

        String fieldDelimiter = ",";

        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(csvFile));

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(fieldDelimiter, -1);

                observableList.add(new ThermalData(fields[0], Double.parseDouble(fields[1]), Double.parseDouble(fields[2])));

            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return observableList;

    }

}

