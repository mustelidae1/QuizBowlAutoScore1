package application;

import java.io.IOException;

import Base.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_addTeam extends Controller {

    @FXML
    private TextField textField_teamName;

    @FXML
    private Button button_ok;

    @FXML
    void addTeam(ActionEvent event) throws IOException {
    	if (textField_teamName.getText() != null && !stats.teamExists(textField_teamName.getText())) {
    		stats.addTeam(new Team(textField_teamName.getText())); 
    	}
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Popup_RoundSetup.fxml")); 
    	loader.load();
    	
    	Controller parentController = loader.getController();
    	parentController.setStats(stats);
    	parentController.update(); 
    	
    	Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	curStage.hide(); 
    }

}

