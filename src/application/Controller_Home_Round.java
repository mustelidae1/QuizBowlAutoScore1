package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class Controller_Home_Round {

    @FXML
    private MenuItem menuItem_close;

    @FXML
    private Button button_Packet1;

    private void changeScene(String scenePath, Stage curStage) throws IOException {
    	
    	Parent root = FXMLLoader.load(getClass().getResource(scenePath));
    	Scene newScene = new Scene(root,900,600); 
    	
    	curStage.setScene(newScene);
    	curStage.show(); 
    	
    }
    
    @FXML
    void close(ActionEvent event) throws IOException {
    	Stage curStage = (Stage)button_Packet1.getScene().getWindow();
    	changeScene("/GUI/RootMenu.fxml", curStage); 
    }

    @FXML
    void startRound(ActionEvent event) throws IOException {
    	Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	changeScene("/GUI/Tossup.fxml", curStage); 
    }

}
