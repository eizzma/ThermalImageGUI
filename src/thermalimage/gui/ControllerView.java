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
    private Button plotButton;

    @FXML
    private ImageView imageView;

    @FXML
    private TableView<ThermalData> tableView;

    @FXML
    private Label projectLabel;

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

        projectLabel.setText("Projekt: "+ Projects.activeProject + "\nVersuch: " + Projects.activeExperiment);

        // Temp Column
        TableColumn<ThermalData, Double> tempColumn = new TableColumn<>("Temperatur");
        tempColumn.setMinWidth(95);
        tempColumn.setCellValueFactory(new PropertyValueFactory<>("temp"));

        // Seconds Column
        TableColumn<ThermalData, Double> secondsColumn = new TableColumn<>("Sekunden");
        secondsColumn.setMinWidth(95);
        secondsColumn.setCellValueFactory(new PropertyValueFactory<>("seconds"));

        // disable multiple selection
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // set tableview content
        tableView.setItems(thermalData);
        tableView.getColumns().addAll(tempColumn, secondsColumn); // show only temp and time in seconds
        // click on item in tableview to display images
        tableView.setOnMouseClicked(event -> {
            setImageView(tableView.getSelectionModel().getSelectedItem().getName());
        });
        // navigate threw tableview to display images
        tableView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    setImageView(tableView.getSelectionModel().getSelectedItem().getName());
                }
                event.consume();
            }
        });

        // display the plot at first
        setImageView("plot");
    }

    @FXML
    private void backToProject() {

        // back to the old scene and reset the activeExperiment
        SceneManager.showProjectScene();
        Projects.activeExperiment = null;

    }

    @FXML
    private void showPlot() {

        // clear selection and show plot
        tableView.getSelectionModel().clearSelection();
        setImageView("plot");

    }

    @FXML
    private void deleteExperiment(){

        Projects.deleteExperiment(Projects.activeExperiment);
        SceneManager.showProjectScene();

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

    private void setImageView(String imgName) {
        File img = new File(Projects.getActiveExperimentDirectory() + "/" + imgName + ".png");
        imageView.setImage(new Image(img.toURI().toString()));
    }

}

