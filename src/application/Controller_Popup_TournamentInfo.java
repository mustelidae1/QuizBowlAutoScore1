package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_Popup_TournamentInfo {

    @FXML
    private TextField textField_tourneyName;

    @FXML
    private TextField textField_numRooms;

    @FXML
    private DatePicker textField_tourneyDate;

    @FXML
    private Button button_next;

    private void changeScene(String scenePath, Stage curStage) {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource(scenePath));
    		Scene newScene = new Scene(root); 
    		
    		curStage.setScene(newScene);
    		curStage.show(); 
    	} catch (Exception e) {
    		e.printStackTrace(); 
    	}
    }
    
    @FXML
    void next(ActionEvent event) {
    	Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	changeScene("/GUI/Popup_TeamList.fxml", curStage); 
    }

}
