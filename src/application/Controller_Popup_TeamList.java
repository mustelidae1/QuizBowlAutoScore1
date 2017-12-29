package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Controller_Popup_TeamList {

    @FXML
    private TextArea textField_teams;

    @FXML
    private Button button_next;

    private void changeScene(String scenePath, Stage curStage) throws IOException {
    	
    	Parent root = FXMLLoader.load(getClass().getResource(scenePath));
    	Scene newScene = new Scene(root, 900, 600); 
    	Stage ownerStage = (Stage)curStage.getOwner(); 	
    	ownerStage.setScene(newScene);
    	ownerStage.show(); 
    	curStage.hide(); 
    	
    }
    
    @FXML
    void next(ActionEvent event) throws IOException {
    	Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	changeScene("/GUI/Home_Tournament.fxml", curStage); 
    }

}

