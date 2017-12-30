package application;

import java.io.IOException;

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

    protected void popup(String scenePath, Stage curStage) {
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
    
    protected void changeScene(String scenePath, Stage curStage) throws IOException {
    	
    	Parent root = FXMLLoader.load(getClass().getResource(scenePath));
    	Scene newScene = new Scene(root); 
    	
    	curStage.setScene(newScene);
    	curStage.show(); 
    	
    }
    
    protected void closePopup(String scenePath, Stage curStage) throws IOException {
    	
    	Parent root = FXMLLoader.load(getClass().getResource(scenePath));
    	Scene newScene = new Scene(root, 900, 600); 
    	Stage ownerStage = (Stage)curStage.getOwner(); 	
    	ownerStage.setScene(newScene);
    	ownerStage.show(); 
    	curStage.hide(); 
    	
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

