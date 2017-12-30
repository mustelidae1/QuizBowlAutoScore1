package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller_Home_Tournament extends Controller {

    @FXML
    private MenuItem menuItem_close;
    
    @FXML 
    private Button button_moreTeam; 
    
    @FXML 
    private AnchorPane schedulePane; 
    
    public void loadData() {
    	TabPane tabs = new TabPane();
    	for (int i = 0; i < stats.getNumRooms(); i++) {
    		Tab tab = new Tab(); 
    		tab.setText("Room " + (i + 1));
    		tab.setClosable(false);
    		TextArea text = new TextArea(); 
    		text.setEditable(false);
    		text.setText(stats.getSchedule().getScheduleRoom(i));
    		tab.setContent(text);
    		tabs.getTabs().add(tab); 
    	}
    	AnchorPane.setTopAnchor(tabs, 50.0);
    	AnchorPane.setRightAnchor(tabs, 0.0);
    	AnchorPane.setLeftAnchor(tabs, 0.0);
    	AnchorPane.setBottomAnchor(tabs, 70.0);
    	schedulePane.getChildren().add(tabs); 
    }
    
    @FXML
    void close(ActionEvent event) throws IOException {
    	Stage curStage = (Stage)button_moreTeam.getScene().getWindow();
    	changeScene("/GUI/RootMenu.fxml", curStage); 
    }

}
