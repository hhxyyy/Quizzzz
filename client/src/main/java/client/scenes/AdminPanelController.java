package client.scenes;

import client.utilities.AdminPanelCommunication;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Activity;
import commons.ActivityImage;
import javafx.application.Platform;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.converter.NumberStringConverter;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AdminPanelController implements FrontEndController {

    private final MainCtrl mainCtrl;
    private final AdminPanelCommunication adminPanelCommunication;
    private FileChooser fileChooser;
    private File selectedImage;
    private File selectedJson;

    @FXML
    private TableView<Activity> activityTable;
    @FXML
    private TableColumn<Activity, String> idColumn;
    @FXML
    private TableColumn<Activity, String> imageColumn;
    @FXML
    private TableColumn<Activity, String> titleColumn;
    @FXML
    private TableColumn<Activity, Number> consumptionColumn;
    @FXML
    private TableColumn<Activity, String> sourceColumn;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;
    @FXML
    private Button saveNewActivityButton;
    @FXML
    private Button selectImage;
    @FXML
    private Button selectJson;
    @FXML
    private Group tableGroup;
    @FXML
    private Group addGroup;
    @FXML
    private HBox inputFields;

    /**
     * The constructor for the AdminPanelController
     *
     * @param mainCtrl an instance of the Main Controller
     */
    @Inject
    public AdminPanelController(MainCtrl mainCtrl, AdminPanelCommunication adminPanelCommunication) {
        this.mainCtrl = mainCtrl;
        this.adminPanelCommunication = adminPanelCommunication;
    }

    /**
     * Sets up the tableView and fills it with data
     */
    public void setup() {
        tableGroup.setVisible(true);
        activityTable.setVisible(true);
        addGroup.setVisible(false);
        List<Activity> activities = adminPanelCommunication.getActivities();
        activityTable.setItems(FXCollections.observableArrayList(activities));
        saveButton.setText("Save");

        // Set up the ID Column
        idColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getId()));
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        idColumn.setOnEditCommit(
                event -> event.getTableView().getItems().get(
                                event.getTablePosition().getRow()
                        )
                        .setId(event.getNewValue())
        );

        // Set up the Image Column
        imageColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getImage()));
        imageColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        imageColumn.setOnEditCommit(
                event -> event.getTableView().getItems().get(
                                event.getTablePosition().getRow()
                        )
                        .setImage(event.getNewValue())
        );

        // Set up the Title Column
        titleColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getTitle()));
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        titleColumn.setOnEditCommit(
                event -> event.getTableView().getItems().get(
                                event.getTablePosition().getRow()
                        )
                        .setTitle(event.getNewValue())
        );

        // Set up the Consumption Column
        consumptionColumn.setCellValueFactory(p -> new SimpleLongProperty(p.getValue().getConsumption()));
        consumptionColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        consumptionColumn.setOnEditCommit(
                event -> event.getTableView().getItems().get(
                                event.getTablePosition().getRow()
                        )
                        .setConsumption((Long) event.getNewValue())
        );

        // Set up the Source Column
        sourceColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getSource().toString()));
        sourceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        sourceColumn.setOnEditCommit(
                event -> {
                    try {
                        event.getTableView().getItems().get(
                                        event.getTablePosition().getRow()
                                )
                                .setSource(new URL(event.getNewValue()));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    /**
     * Method called when the exit button is pressed.
     */
    public void showSplashScreen() {
        mainCtrl.showSplashScene();
    }

    /**
     * The method that is run when the save button is pressed
     */
    public void saveActivities() {
        List<Activity> activities = activityTable.getItems();
        boolean res = adminPanelCommunication.saveActivities(activities);
        if (res) {
            saveButton.setText("Saved!");
        } else {
            saveButton.setText("Something went wrong!");
        }
        setTimeout(() -> saveButton.setText("Save"), 2000);
    }

    /**
     * Method that is called when the deleteButton is pressed
     */
    public void deleteActivity() {
        int row = activityTable.getSelectionModel().getFocusedIndex();
        activityTable.getItems().remove(row);
        adminPanelCommunication.saveActivities(activityTable.getItems());
        deleteButton.setText("Deleted");
        setTimeout(() -> deleteButton.setText("Delete"), 2000);
    }

    /**
     * Method that is run when the add button is pressed
     */
    public void addActivity() {
        tableGroup.setVisible(false);
        activityTable.setVisible(false);
        addGroup.setVisible(true);
    }

    /**
     * Method that is run to select an image from the file system
     */
    public void openImage() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG/JPEG", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("All Images", "*.*")
        );
        selectedImage = fileChooser.showOpenDialog(mainCtrl.getPrimaryStage());
        if (selectedImage != null) {
            selectImage.setText("Selected!");
        }
    }

    /**
     * Method that is run to select a json from the file system
     */
    public void openJSON() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON", "*.json")
        );
        selectedJson = fileChooser.showOpenDialog(mainCtrl.getPrimaryStage());
        if (selectedJson != null) {
            selectJson.setText("Selected!");
        }
    }

    /**
     * Method run when the save button is pressed. It saved a custom activity to the server
     */
    public void saveNewActivity() throws IOException {
        if (selectedJson != null) {
            InputStream input = null;
            try {
                input = new FileInputStream(selectedJson.getAbsolutePath());
            } catch (FileNotFoundException e) {
                saveNewActivityButton.setText("JSON file not found !");
            }

            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Activity>> typeReference = new TypeReference<>() {
            };
            List<Activity> activities;
            activities = mapper.readValue(input, typeReference);

            adminPanelCommunication.saveActivities(activities);
            String absolutePath = selectedJson.getParent();
            for (Activity a : activities) {
                activityTable.getItems().add(a);
                String path = absolutePath + "/" + a.getImage();
                File image = new File(path);
                adminPanelCommunication.saveImage(new ActivityImage(Files.readAllBytes(Paths.get(image.getAbsolutePath())), a.getImage()));
            }

            selectedJson = null;
            selectJson.setText("Import JSON");
        } else {
            List<String> values = new ArrayList<>();
            List<Node> nodes = inputFields.getChildren();
            for (Node n : nodes) {
                if (n instanceof TextField) {
                    TextField t = (TextField) n;
                    values.add(t.getText());
                }
            }
            if (selectedImage == null) {
                saveNewActivityButton.setText("No image selected");
                setTimeout(() -> saveNewActivityButton.setText("Save"), 2000);
                return;
            }
            int consumption = 0;
            if (values.size() == 4) {
                try {
                    consumption = Integer.parseInt(values.get(2));
                } catch (NumberFormatException e) {
                    saveNewActivityButton.setText("Consumption should be int");
                    setTimeout(() -> saveNewActivityButton.setText("Save"), 2000);
                    return;
                }
            }
            try {
                Activity a = new Activity(values.get(0), "custom/" + selectedImage.getName(), values.get(1), consumption, new URL(values.get(3)));
                activityTable.getItems().add(a);
                adminPanelCommunication.saveActivities(activityTable.getItems());
                adminPanelCommunication.saveImage(new ActivityImage(Files.readAllBytes(Paths.get(selectedImage.getAbsolutePath())), "custom/" + selectedImage.getName()));
            } catch (MalformedURLException e) {
                saveNewActivityButton.setText("Source invalid URL");
                setTimeout(() -> saveNewActivityButton.setText("Save"), 3000);
                return;
            }

            selectImage.setText("Select File");
            selectedImage = null;
            for (Node n : nodes) {
                if (n instanceof TextField) {
                    TextField t = (TextField) n;
                    t.setText("");
                }
            }
        }
        tableGroup.setVisible(true);
        activityTable.setVisible(true);
        addGroup.setVisible(false);
    }

    /**
     * Method to perform an action on a delay
     *
     * @param runnable The function to perform
     * @param delay    The delay before the runnable is run
     */
    private void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                Platform.runLater(runnable);
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }
}
