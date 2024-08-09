package rybres.dataparcel;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class PrimaryController {
    
    // On-initialization (immediate) loading
    @FXML
    private ComboBox<String> methodComboBox;
    @FXML
    private ComboBox<String> sizeParamComboBox;
    
    @FXML
    private void initialize()
    {
        // Method combo box
        methodComboBox.getItems().addAll("Row number", "Partition number", "Partition size");
        methodComboBox.setValue("Row number");
        
        // File size combo box
        sizeParamComboBox.getItems().addAll("Gb", "Mb", "Kb");
        sizeParamComboBox.setValue("Gb");
        sizeParamComboBox.setVisible(false); // keep hidden unless file size is selected
    }
    
    // Path textField classes
    @FXML
    private TextField pathTextField;
    
    @FXML
    private void handleBrowseButtonAction(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null)
        {
            pathTextField.setText(selectedFile.getAbsolutePath());
        }
    }
    
    // Exit button classes
    @FXML
    private Button exitButton;
    
    @FXML
    private void handleExitButtonAction(ActionEvent event)
    {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    // Method menu selection classes
    @FXML
    private TextField methodParamTextField;
    
    @FXML
    private void handleMethodSelection(ActionEvent event)
    {
        String selectedMethod = methodComboBox.getValue();
        
        // Selection logic for TextField prompt, with display logic for size param ComboBox
        switch(selectedMethod)
        {
            case "Row number":
                methodParamTextField.setPromptText("# Rows");
                sizeParamComboBox.setVisible(false);
                break;
            case "Partition number":
                methodParamTextField.setPromptText("# Partitions");
                sizeParamComboBox.setVisible(false);
                break;
            case "Partition size":
                methodParamTextField.setPromptText("Size");
                sizeParamComboBox.setVisible(true);
                break;
            default:
                methodParamTextField.setPromptText("# Rows");
                sizeParamComboBox.setVisible(false);
                break;
        }
        
    }
    
}
