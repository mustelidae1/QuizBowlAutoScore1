package application;

import java.time.LocalDate;
import java.util.ArrayList;

public class Tournament_Info {

	private String tournamentName; 
	private int numRooms; 
	private int numTeams; 
	private int numRounds;    // TODO: Do we need this? I'm just defaulting to the usual 8 rounds for now.  -- OT
	private LocalDate tournamentDate;
	private ArrayList<String> teams = new ArrayList<String>(); 
	private Schedule_Generator schedule; 
	
	public Tournament_Info() {
		setTournamentName(""); 
		setNumRooms(0); 
		setNumRounds(8); 
		setNumTeams(0); 
	}
	
	public Tournament_Info(String tourneyName, int numRooms, int numRounds) {
		setTournamentName(tourneyName); 
		setNumRooms(numRooms); 
		setNumRounds(numRounds); 
		setNumTeams(0); 
		setNumRounds(8); 
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}

	public int getNumRooms() {
		return numRooms;
	}

	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}

	public int getNumTeams() {
		return numTeams;
	}

	public void setNumTeams(int numTeams) {
		this.numTeams = numTeams;
	}

	public int getNumRounds() {
		return numRounds;
	}

	public void setNumRounds(int numRounds) {
		this.numRounds = numRounds;
	}

	public LocalDate getTournamentDate() {
		return tournamentDate;
	}

	public void setTournamentDate(LocalDate tournamentDate) {
		this.tournamentDate = tournamentDate;
	}
	
	public ArrayList<String> getTeams() {
		return teams;
	}
	
	public void addTeam(String team) {
		teams.add(team); 
	}
	
	public void removeTeam(String team) {
		teams.remove(team); 
	}
	
	public String getTeam(int index) {
		return teams.get(index); 
	}

	public Schedule_Generator getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule_Generator schedule) {
		this.schedule = schedule;
	}
	
}
