package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Controller_Popup_TeamList extends Controller {

    @FXML
    private TextArea textField_teams;

    @FXML
    private Button button_next;
    
    @FXML
    void next(ActionEvent event) throws IOException {
    	// Get teams 
    	for (String line : textField_teams.getText().split("\\n")) {
    		if (line != null) {
            	stats.addTeam(line);
            	stats.setNumTeams(stats.getNumTeams()+1);
    		}
    	}
    	
    	// Generate schedule 
    	Schedule_Generator schedule = new Schedule_Generator(stats.getNumRooms(), stats.getNumRounds(), stats.getTeams()); 
    	stats.setSchedule(schedule);
    	
    	// Change scene 
    	Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	closePopup("/GUI/Home_Tournament.fxml", curStage); 
    }

}
