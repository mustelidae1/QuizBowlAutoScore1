package Base;

import java.util.ArrayList;

public class Team
{
    private ArrayList<Player> players;
    private String teamName;
    private int numPlayers;

    public Team(String teamName)
    {
        this.teamName = teamName;
        players = new ArrayList<>();
    }

    public String getTeamName()
    {
        return teamName;
    }

    public void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }

    public void addPlayer(Player player)
    {
        this.players.add(player);
    }

    public int getNumPlayers()
    {
       return players.size();
    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    // Added 1/6 -- OT
    public boolean hasPlayer(String playerName) {
        boolean retVal = false;
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                retVal = true;
            }
        }
        return retVal;
    }

    // Added 1/6 -- OT
    public Player getPlayer(int index) {
        return players.get(index);
    }
}
