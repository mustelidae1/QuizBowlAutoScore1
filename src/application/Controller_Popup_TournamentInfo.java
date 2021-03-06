package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_Popup_TournamentInfo extends Controller {

    @FXML
    private TextField textField_tourneyName;

    @FXML
    private TextField textField_numRooms;

    @FXML
    private DatePicker textField_tourneyDate;

    @FXML
    private Button button_next;
    
    @FXML
    void next(ActionEvent event) throws IOException {
    	stats.setTournamentName(textField_tourneyName.getText()); 
    	stats.setTournamentDate(textField_tourneyDate.getValue());
    	
    	try {
    		int numRooms = Integer.parseInt(textField_numRooms.getText());
    	    
        	stats.setNumRooms(Integer.parseInt(textField_numRooms.getText()));
        	
        	Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        	changeScene("/GUI/Popup_TeamList.fxml", curStage); 
    	} catch (NumberFormatException e) {
    		textField_numRooms.setText("Please enter a valid number");
    	}
    	
    }

}
