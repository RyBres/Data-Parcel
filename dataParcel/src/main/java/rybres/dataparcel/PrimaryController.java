package rybres.dataparcel;

import rybres.dataparcel.model.PartitionMethods;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrimaryController {

    // On-initialization (immediate) loading
    @FXML
    private ComboBox<String> methodComboBox;
    @FXML
    private ComboBox<String> sizeParamComboBox;

    @FXML
    private void initialize() {
        // Method combo box
        methodComboBox.getItems().addAll("Row number", "Partition number"); // , "Partition size" is removed for now
        methodComboBox.setValue("Row number");

        // File size combo box
        sizeParamComboBox.getItems().addAll("Gb", "Mb", "Kb");
        sizeParamComboBox.setValue("Gb");
        sizeParamComboBox.setVisible(false); // keep hidden unless file size is selected
    }

    // Path textField
    @FXML
    private TextField inputPathTextField;

    @FXML
    private void handleBrowseButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            inputPathTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private TextField outputPathTextField;

    @FXML
    private void handleBrowseButton1Action(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        File selectedDirectory = directoryChooser.showDialog(new Stage());
        if (selectedDirectory != null) {
            outputPathTextField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    // Exit button
    @FXML
    private Button exitButton;

    @FXML
    private void handleExitButtonAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    // Method menu selection
    @FXML
    private TextField methodParamTextField;

    @FXML
    private void handleMethodSelection(ActionEvent event) {
        String selectedMethod = methodComboBox.getValue();

        // Selection logic for TextField prompt, with display logic for size param ComboBox
        switch (selectedMethod) {
            case "Row number":
                methodParamTextField.setPromptText("# Rows");
                sizeParamComboBox.setVisible(false);
                break;
            case "Partition number":
                methodParamTextField.setPromptText("# Partitions");
                sizeParamComboBox.setVisible(false);
                break;
            /*
            case "Partition size":
                methodParamTextField.setPromptText("Size");
                sizeParamComboBox.setVisible(true);
                break;
             */
            default:
                methodParamTextField.setPromptText("# Rows");
                sizeParamComboBox.setVisible(false);
                break;
        }

    }
    
    // Columns button
    private static Scene columnsScene;
    
    @FXML
    private void handleColumnsButton(ActionEvent event) throws IOException {
       columnsScene = new Scene(new FXMLLoader(App.class.getResource("columnsMenu.fxml")).load(), 400, 400);
       columnsScene.getStylesheets().add(getClass().getResource("/rybres/dataparcel/confirmButton.css").toExternalForm());
       Stage stage = new Stage();
       stage.setTitle("DataParcel: Select Columns");
       stage.initModality(Modality.APPLICATION_MODAL);
       stage.setScene(columnsScene);
       stage.show();
       stage.setResizable(false);
    }
    

    // Start button
    @FXML
    private Button startButton; // this should probably be called startStopButton or something since that's what it is
    private final PartitionMethods partitionMethods = new PartitionMethods();
    private boolean processStarted = false;
    private Task<Void> processingTask;

    @FXML
    private void handleStartButtonAction(ActionEvent event) {
        processStarted = !processStarted;

        if (processStarted) {
            // Update button state to 'Stop'

            setToStopButton();

            processingTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception, IOException {
                    // Define input variables
                    String methodType = methodComboBox.getValue();
                    String inputFile = inputPathTextField.getText();
                    String outputFile = outputPathTextField.getText();
                    int rowNumber = Integer.parseInt(methodParamTextField.getText());

                    // Process
                    partitionMethods.startParsingMethod(methodType, inputFile, outputFile, rowNumber);

                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    updateMessage("Success!");
                    // Update button state to 'Start' when processing ends
                    setToStartButton();
                    processStarted = false;
                }

                @Override
                protected void failed() {
                    super.failed();
                    updateMessage("Failed!");
                    // Handle errors and update button state
                    setToStartButton();
                    processStarted = false;
                }
                
                @Override
                protected void cancelled() {
                    super.cancelled();
                    updateMessage("Cancelled!");
                    // Handle task cancellation
                    setToStartButton();
                    processStarted = false;
                }
            };

            new Thread(processingTask).start();
        } else {
            // Kill process
            if (processingTask != null && processingTask.isRunning()) {
                System.out.println(processingTask.isDone());
                processingTask.cancel(true);
            }
            System.out.println(processingTask.isDone());
            processingTask.cancel(true);

            // Update button state to 'Start'
            setToStartButton();
            processStarted = false;

            System.out.println("Stop button clicked");
        }
    }

    private void setToStopButton() {
        startButton.setText("Stop");
        startButton.getStyleClass().remove("button-start");
        startButton.getStyleClass().add("button-stop");
    }

    private void setToStartButton() {
        startButton.setText("Start");
        startButton.getStyleClass().remove("button-stop");
        startButton.getStyleClass().add("button-start");
    }

}
