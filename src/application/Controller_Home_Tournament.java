package application;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

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
    private Button button_print; 
    
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
    
    @FXML
    void printSchedule(ActionEvent event) {
    	File file = new File("schedule.txt");
    	try {
			file.createNewFile();
			FileOutputStream is = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(is);    
            Writer w = new BufferedWriter(osw);
            for (int i = 0; i < stats.getNumRooms(); i++) {
            	w.write("Room " + (i+1) + ":\r\n");
            	w.write(stats.getSchedule().getScheduleRoom(i) + "\r\n");
            }
            w.close();
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) desktop.open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
