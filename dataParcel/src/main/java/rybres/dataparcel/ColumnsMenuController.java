/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package rybres.dataparcel;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author bresn
 */
public class ColumnsMenuController implements Initializable {
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    
    @FXML
    private ListView<String> includeListView; // Left hand listView
    
    @FXML
    private ListView<String> excludeListView;
    
    public void setFieldsIncludeListView(List<String> colNames) {
        includeListView.getItems().setAll(colNames);
    }
    
    
    @FXML
    private Button excludeButton; // Left hand button
    
    
    @FXML
    private void handleExcludeButtonAction() {
        ObservableList<String> selectedItems = includeListView.getSelectionModel().getSelectedItems();

        // Copy selected items to the right ListView
        excludeListView.getItems().addAll(selectedItems);

        // Remove selected items from the left ListView
        includeListView.getItems().removeAll(selectedItems);
    
    }
    
}
