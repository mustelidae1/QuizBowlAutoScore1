package application;

import Base.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class Tournament_Info {

	private String tournamentName; 
	private int numRooms; 
	private int numTeams; 
	private int numRounds;  
	private LocalDate tournamentDate;
	private ArrayList<Team> teams = new ArrayList<Team>(); 
	private Schedule_Generator schedule; 
	private ArrayList<File> packets = new ArrayList<File>(); 
	private Team currentTeam1; 
	private Team currentTeam2; 
	private int currentRound; 
	
	public Tournament_Info() {
		setTournamentName(""); 
		setNumRooms(0); 
		setNumRounds(8); 
		setNumTeams(0); 
		setCurrentRound(0); 
	}
	
	public Tournament_Info(String tourneyName, int numRooms, int numRounds) {
		setTournamentName(tourneyName); 
		setNumRooms(numRooms); 
		setNumRounds(numRounds); 
		setNumTeams(0); 
		setCurrentRound(0); 
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
	
	public ArrayList<Team> getTeams() {
		return teams;
	}
	
	public void addTeam(Team team) {
		teams.add(team); 
	}
	
	public boolean teamExists(String team) {
		boolean retVal = false; 
		for (Team searchTeam : teams) {
			if (searchTeam.getTeamName().equals(team)) {
				retVal = true; 
			}
		}
		return retVal; 
	}
	
	public void removeTeam(String team) {
		teams.remove(team); 
	}
	
	public Team getTeam(int index) {
		return teams.get(index); 
	}
	
	public Team getTeam(String name) {
		Team retVal = null; 
		for (Team team : teams) {
			if (team.getTeamName().equals(name)) {
				retVal = team; 
			}
		}
		return retVal; 
	}
	
	public void addPacket(File file) {
		packets.add(file); 
	} 
	
	public void removePacket(File file) {
		packets.remove(file); 
	}
	
	public File getPacket(int index) {
		return packets.get(index); 
	}

	public Schedule_Generator getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule_Generator schedule) {
		this.schedule = schedule;
	}

	public Team getCurrentTeam1() {
		return currentTeam1;
	}

	public void setCurrentTeam1(Team currentTeam1) {
		this.currentTeam1 = currentTeam1;
	}

	public Team getCurrentTeam2() {
		return currentTeam2;
	}

	public void setCurrentTeam2(Team currentTeam2) {
		this.currentTeam2 = currentTeam2;
	}

	public int getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}
	
}
