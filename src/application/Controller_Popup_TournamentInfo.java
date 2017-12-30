package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_Popup_TournamentInfo extends Controller {

    @FXML
    private TextField textField_tourneyName;

    @FXML
    private TextField textField_numRooms;

    @FXML
    private DatePicker textField_tourneyDate;

    @FXML
    private Button button_next;
    
    @FXML
    void next(ActionEvent event) throws IOException {
    	Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	changeScene("/GUI/Popup_TeamList.fxml", curStage); 
    }

}
