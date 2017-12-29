package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private Button button_newRound;

    @FXML
    private Button button_newTournament;

    @FXML
    private Button button_savedTournaments;

    private void popup(String scenePath, Stage curStage) {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource(scenePath));
    		Scene newScene = new Scene(root); 
    		Stage newStage = new Stage(); 
    		newStage.setScene(newScene);
    		newStage.initModality(Modality.APPLICATION_MODAL);
    		newStage.initOwner(curStage);
    		
    		newStage.show(); 
    	} catch (Exception e) {
    		e.printStackTrace(); 
    	}
    }
    
    @FXML
    void newRound(ActionEvent event) {
    	Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	popup("/GUI/Popup_Packets.fxml", curStage); 
    }

    @FXML
    void newTournament(ActionEvent event) {
  
    	Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	popup("/GUI/Popup_TournamentInfo.fxml", curStage); 
    	
    }

    @FXML
    void savedTournaments(ActionEvent event) {

    }

}

