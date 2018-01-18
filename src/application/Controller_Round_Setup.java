package application;

import java.io.IOException;
import java.util.ArrayList;

import Base.*;
import QuestionParsing.QuestionParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_Round_Setup extends Controller {

    @FXML
    private Button button_startRound;

    @FXML
    private ComboBox<String> comboBox_team1;

    @FXML
    private ComboBox<String> comboBox_team2;

    @FXML
    private TextField textField_team1player1;

    @FXML
    private TextField textField_team1player2;

    @FXML
    private TextField textField_team1player3;

    @FXML
    private TextField textField_team1player4;

    @FXML
    private TextField textField_team1player5;

    @FXML
    private TextField textField_team1player6;

    @FXML
    private TextField textField_team2player1;

    @FXML
    private TextField textField_team2player2;

    @FXML
    private TextField textField_team2player3;

    @FXML
    private TextField textField_team2player4;

    @FXML
    private TextField textField_team2player5;

    @FXML
    private TextField textField_team2player6;
    
    private TextField[] team1Players = new TextField[6];
    
    private TextField[] team2Players = new TextField[6];
    
    private ArrayList<Team> teamsAdded = new ArrayList<Team>(); 

    public void loadData() {
    	// Add existing teams to combo boxes 
    	for (Team team : stats.getTeams()) {
    		String item = team.getTeamName(); 
    		comboBox_team1.getItems().add(item); 
    		comboBox_team2.getItems().add(item); 
    		teamsAdded.add(team); 
    	}
    }
    
    @FXML
    public void initialize() {
    	comboBox_team1.setEditable(true);
    	comboBox_team2.setEditable(true);
    	
    	team1Players[0] = textField_team1player1;  
    	team1Players[1] = textField_team1player2; 
    	team1Players[2] = textField_team1player3; 
    	team1Players[3] = textField_team1player4; 
    	team1Players[4] = textField_team1player5; 
    	team1Players[5] = textField_team1player6; 
    	
    	team2Players[0] = textField_team2player1;  
    	team2Players[1] = textField_team2player2; 
    	team2Players[2] = textField_team2player3; 
    	team2Players[3] = textField_team2player4; 
    	team2Players[4] = textField_team2player5; 
    	team2Players[5] = textField_team2player6; 
    }

    @FXML
    void changeTeam1(ActionEvent event) {
    	System.out.println("Change Team 1");
    	for (int i = 0; i < team1Players.length; i++) {
			team1Players[i].setText(null); 
		}
    	String selectedTeam = comboBox_team1.getSelectionModel().getSelectedItem(); 
    	if (stats.teamExists(selectedTeam)) {
    		Team team = stats.getTeam(selectedTeam); 
    		for (int i = 0; i < team1Players.length; i++) {
    			if (i < team.getNumPlayers()) {
    				team1Players[i].setText(team.getPlayer(i).getName());
    			} 
    		}
    	}
    }

    @FXML
    void changeTeam2(ActionEvent event) {
    	System.out.println("Change Team 2");
    	for (int i = 0; i < team2Players.length; i++) {
			team2Players[i].setText(null); 
		}
    	String selectedTeam = comboBox_team2.getSelectionModel().getSelectedItem(); 
    	if (stats.teamExists(selectedTeam)) {
    		Team team = stats.getTeam(selectedTeam);
    		for (int i = 0; i < team2Players.length; i++) {
    			if (i < team.getNumPlayers()) {
    				team2Players[i].setText(team.getPlayer(i).getName());
    			} 
    			
    		}
    	}
    }

    @FXML
    void startRound(ActionEvent event) throws IOException{
    	try {
    		// Create any nonexisting teams 
    		if (!stats.teamExists(comboBox_team1.getSelectionModel().getSelectedItem())) {
        		stats.addTeam(new Team(comboBox_team1.getSelectionModel().getSelectedItem()));
        	}
        	if(!stats.teamExists(comboBox_team2.getSelectionModel().getSelectedItem())) {
        		stats.addTeam(new Team(comboBox_team2.getSelectionModel().getSelectedItem()));
        	}
        	
        	String team1 = comboBox_team1.getSelectionModel().getSelectedItem();
        	String team2 = comboBox_team2.getSelectionModel().getSelectedItem(); 
        	
        	// Add nonexisting players to their respective team 
        	for (TextField text: team1Players) {
        		String playerName = text.getText(); 
        		if (playerName != null && playerName != "" && !stats.getTeam(team1).hasPlayer(playerName)) {
        			stats.getTeam(team1).addPlayer(new Player(playerName));
        			System.out.println("Added new player to team 1: " + playerName);
        		}
        	}
        	for (TextField text : team2Players) {
        		String playerName = text.getText(); 
        		if (playerName != null && playerName != "" && !stats.getTeam(team2).hasPlayer(playerName)) {
        			stats.getTeam(team2).addPlayer(new Player(playerName));
        			System.out.println("Added new player to team 2: " + playerName);
        		}
        	}
        	
        	if(stats.getTeam(team1).getNumPlayers() == 0 || stats.getTeam(team2).getNumPlayers() == 0) {
        		throw new NullPointerException(); 
        	}
        	
        	// Set the current teams and round in the stats object 
        	stats.setCurrentTeam1(stats.getTeam(team1));
        	stats.setCurrentTeam2(stats.getTeam(team2));
        	stats.setCurrentRound(stats.getCurrentRound() + 1);

			Game game = new Game(stats.getTeam(0), stats.getTeam(1));
			Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
			GameController gController = (Base.GameController) changeScene("/Base/game.fxml", curStage); //LOL TODO: Fix this
			QuestionParser test = new QuestionParser(stats.getPacket(stats.getCurrentRound() - 1));
			game.setTossUps(test.tossUps);
			game.setBonuses(test.bonuses);

			gController.loadGame(game);
			gController.initGuiElements();
        	
        	// TODO: change the scene 
        	
    	} catch (NullPointerException e) {
    		Alert alert = new Alert(AlertType.ERROR); 
        	alert.setTitle("Missing Team Name"); 
        	alert.setContentText("Team or player names are missing.");
        	alert.showAndWait(); 
    	}
    	
    	
    }

}
