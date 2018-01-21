package application;

import java.io.IOException;

import Base.*;
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
    
    protected Tournament_Info stats; 
    
    protected void setStats(Tournament_Info newStats) {
    	this.stats = newStats; 
    }
    
    // ***************** SCENE CHANGE METHODS **********************//
    //                Used by child controllers.                    //
    // *************************************************************//
    protected void popup(String scenePath, Stage curStage) throws IOException {
    	// Get FXML file 
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath)); 
    	Parent root = loader.load();
    	
    	// Pass on stats object 
    	Controller controller = loader.getController(); 
    	controller.setStats(stats);
    	controller.loadData(); 
    	
    	// Popup 
   		Scene newScene = new Scene(root); 
   		Stage newStage = new Stage(); 
   		newStage.setScene(newScene);
   		newStage.setResizable(false);
   		newStage.initModality(Modality.APPLICATION_MODAL);
   		newStage.initOwner(curStage);
    		
    	newStage.show(); 
    	
    }
    
    protected Controller changeScene(String scenePath, Stage curStage) throws IOException {
    	// Get FXML file 
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath)); 
    	Parent root = loader.load();
    	
    	// Pass on the stats object 
    	Controller controller = loader.getController(); 
    	controller.setStats(stats);
    	controller.loadData(); 
    	
    	// Change the scene 
    	Scene newScene = new Scene(root); 
    	curStage.setScene(newScene);
    	newScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    	curStage.show();
    	return controller;
    	
    }
    
    protected void closePopup(String scenePath, Stage curStage) throws IOException {
    	// Get FXML file 
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath)); 
    	Parent root = loader.load();
    	
    	// Pass on the stats object 
    	Controller controller = loader.getController(); 
    	controller.setStats(stats); 
    	controller.loadData(); 
    	
    	// Change the scene 
    	Scene newScene = new Scene(root, 900, 600); 
    	Stage ownerStage = (Stage)curStage.getOwner(); 	
    	ownerStage.setScene(newScene);
    	ownerStage.show(); 
    	curStage.hide(); 
    	
    }
    
    /*
     * Loads any necessary data on to the page. 
     * Called after stats have been loaded. 
     */
    public void loadData() {}
    
    /*
     * For updating data once the page is already
     * displayed. 
     */ 
    public void update() {}
    
    // ******* MAIN MENU BUTTON EVENT HANDLERS ***********// 
    
    @FXML
    void newRound(ActionEvent event) throws IOException {
    	Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	popup("/GUI/Popup_Packets.fxml", curStage); 
    }

    @FXML
    void newTournament(ActionEvent event) throws IOException {
  
    	Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	popup("/GUI/Popup_TournamentInfo.fxml", curStage); 
    	
    }

    @FXML
    void savedTournaments(ActionEvent event) {

    }

}

