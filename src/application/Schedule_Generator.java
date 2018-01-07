package application;

import java.util.ArrayList;

/*
 * Object representing a tournament schedule. 
 */
public class Schedule_Generator {

	String[][] schedule; 
	int numRounds; 
	
	public Schedule_Generator(int numRooms, int numRounds, ArrayList<Team> teams) {
		schedule = new String[numRooms][numRounds];
		this.numRounds = numRounds; 
		
		for (int i = 0; i < numRooms; i++) {
			for (int j = 0; j < numRounds; j++) {
				// TODO: Actually generate a schedule 
				schedule[i][j] = "Room " + (i + 1) + " Round " + (j + 1); 
			}
		}
	}
	
	public String[][] getSchedule() {
		return schedule; 
	}
	
	public String getScheduleRoom(int room) {
		String retVal = ""; 
		for (int i = 0; i < numRounds; i++) {
			retVal += ((i + 1) + ". " + schedule[room][i] + "\r\n"); 
		}
		return retVal; 
	}
	
}
