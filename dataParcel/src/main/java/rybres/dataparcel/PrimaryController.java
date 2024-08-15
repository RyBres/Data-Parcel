package rybres.dataparcel;

import rybres.dataparcel.model.PartitionMethods;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
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
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
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

    // Start button
    @FXML
    private Button startButton;

    private final PartitionMethods partitionMethods = new PartitionMethods();
    
    @FXML
    private void handleStartButtonAction(ActionEvent event) {
        // Define input variables
        System.out.println("Start button clicked");
        String methodType = methodComboBox.getValue();
        String inputFile = inputPathTextField.getText();
        String outputFile = outputPathTextField.getText();
        int rowNumber;
        rowNumber = Integer.parseInt(methodParamTextField.getText());

        
        try {
            partitionMethods.startAnyMethod(methodType, inputFile, outputFile, rowNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
