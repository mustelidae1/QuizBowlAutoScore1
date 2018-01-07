package application;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class Controller_Popup_Packets extends Controller {

	private ArrayList<File> filesList = new ArrayList<File>(); 
	
    @FXML
    private Button button_next;
    
    @FXML 
    private Button button_add; 
    
    @FXML 
    private Button button_remove; 

    @FXML
    private ListView<String> listView_packets;
    
    @FXML
    void next(ActionEvent event) throws IOException {
    	for (File file : filesList) {
    		stats.addPacket(file);
    	}
    	// TODO: check that the pdf uploaded is a valid packet 
    	if (listView_packets.getItems().size() == 0) { 
    		Alert alert = new Alert(AlertType.ERROR); 
    		alert.setTitle("No Packets"); 
    		alert.setContentText("Please upload at least one packet.");
    		alert.showAndWait(); 
    	} else {
    		Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        	changeScene("/GUI/Popup_RoundSetup.fxml", curStage); 
    	}
    	
    }
    
    @FXML 
    void uploadPacket(ActionEvent event) {
    	JFileChooser chooser = new JFileChooser();
    	chooser.setMultiSelectionEnabled(true);
    	
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
    	chooser.setFileFilter(filter);
    	chooser.setPreferredSize(new Dimension(1600,1000)); 
        setFileChooserFont(chooser.getComponents());  
    	int returnVal = chooser.showOpenDialog(null);  // TODO: Get this to parent to current window   
    	if(returnVal == JFileChooser.APPROVE_OPTION) {
    		File[] files = chooser.getSelectedFiles(); 
    		for (File file : files) {
    			filesList.add(file); 
        		listView_packets.getItems().add(file.getName()); 
    		}
    		
    	}
        
    }
    
    // From https://stackoverflow.com/questions/8997160/how-to-increase-the-size-of-a-jfilechooser
    private void setFileChooserFont(Component[] comp)
    {  
      Font font = new Font("system",Font.PLAIN,30);
      for(int x = 0; x < comp.length; x++)  
      {  
        if(comp[x] instanceof Container) setFileChooserFont(((Container)comp[x]).getComponents());  
        try{
        	comp[x].setFont(font); 
        } catch(Exception e){}//do nothing  
        try{
        	comp[x].setSize(new Dimension(comp[x].getSize().height * 2, comp[x].getSize().width * 2));
        } catch (Exception e){}
      } 
    }  
    
    @FXML 
    void removePacket(ActionEvent event) {
    	listView_packets.getItems().remove(listView_packets.getSelectionModel().getSelectedItem());
    }

}
