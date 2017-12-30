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

public class Controller_Home_Round extends Controller {

    @FXML
    private MenuItem menuItem_close;

    @FXML
    private Button button_Packet1;
    
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
