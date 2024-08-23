/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package rybres.dataparcel;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import rybres.dataparcel.model.InputFileInfo;

/**
 * FXML Controller class
 *
 * @author bresn
 */
public class ColumnsMenuController implements Initializable {
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    // ListView values
    @FXML
    private ListView<String> includeListView; // Left hand listView
    
    @FXML
    private ListView<String> excludeListView;
    
    public void setFieldsIncludeListView(List<String> colNames) {
        includeListView.getItems().setAll(colNames);
    }
    
    private InputFileInfo inputFileInfo; // take the input info from primary controller
    
    public void setInputFileInfo(InputFileInfo inputFileInfo) {
        this.inputFileInfo = inputFileInfo;
    }
    
    public void setFieldsExcludeListView(List<String> colNames) {
        excludeListView.getItems().setAll(colNames);
    }
    
    
    @FXML
    private Button excludeButton; // Left hand button
    
    // Exclude and include buttons
    @FXML
    private void handleExcludeButtonAction() {
        ObservableList<String> selectedItems = includeListView.getSelectionModel().getSelectedItems();

        // Copy selected items to the right ListView
        excludeListView.getItems().addAll(selectedItems);

        // Remove selected items from the left ListView
        includeListView.getItems().removeAll(selectedItems);
    
    }
    
    @FXML
    private void handleIncludeButtonAction() {
        ObservableList<String> selectedItems = excludeListView.getSelectionModel().getSelectedItems();
        
        // Copy selected items to the left ListView
        includeListView.getItems().addAll(selectedItems);
        
        // Remove selected items from right listView
        excludeListView.getItems().removeAll(selectedItems);
    }
    
    @FXML
    private Button cancelButton;
    
    @FXML 
    private void handleCancelButtonAction() {
        ((Stage) (cancelButton.getScene().getWindow())).close();
    }
    
    @FXML
    private Button confirmButton;
    
    @FXML
    private void handleConfirmButtonAction() {
        inputFileInfo.setConfirmSwitchTrue();
        inputFileInfo.setIncludedColumns(includeListView.getItems());
        inputFileInfo.setExcludedColumns(excludeListView.getItems());
        
        System.out.println(inputFileInfo.getExcludedColumns());
        ((Stage) (confirmButton.getScene().getWindow())).close();
    }
    
}
