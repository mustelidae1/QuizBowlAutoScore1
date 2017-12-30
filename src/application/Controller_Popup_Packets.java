package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class Controller_Popup_Packets extends Controller {

    @FXML
    private Button button_next;

    @FXML
    private ListView<?> listView_packets;
    
    @FXML
    void next(ActionEvent event) throws IOException {
    	Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	closePopup("/GUI/Home_Round.fxml", curStage); 
    }

}
