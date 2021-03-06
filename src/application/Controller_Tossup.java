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

public class Controller_Tossup extends Controller {

    @FXML
    private MenuItem menuItem_close;
    
    @FXML
    private Button button_noAnswer; 
    
    @FXML
    void close(ActionEvent event) throws IOException {
    	Stage curStage = (Stage)button_noAnswer.getScene().getWindow();
    	changeScene("/GUI/Home_Round.fxml", curStage); 
    }

}
