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

}
