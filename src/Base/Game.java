package Base;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

public class Game
{
    private ArrayList<TossUpQuestion> tossUps;
    private Team teamOne;
    private Team teamTwo;
    private IntegerProperty[] teamScoreProperties;

    public Game(Team teamOne, Team teamTwo)
    {
        tossUps = new ArrayList<>(); //Arbitrarily limiting to 50 questions.
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        teamScoreProperties = new IntegerProperty[2];
        teamScoreProperties[0] = new SimpleIntegerProperty(0);
        teamScoreProperties[1] = new SimpleIntegerProperty(0);
    }

    public void setTeamScoreProperty(int teamIndex, int teamScore)
    {
        teamScoreProperties[teamIndex].set(teamScore);
    }

    public IntegerProperty getTeamScoreProperty(int teamIndex)
    {
        return teamScoreProperties[teamIndex];
    }

    public ArrayList<TossUpQuestion> getTossups()
    {
        return  tossUps;
    }

    public void setTossUps(ArrayList<TossUpQuestion> tossUps)
    {
        this.tossUps = tossUps;
    }

    public Team getTeamOne()
    {
        return teamOne;
    }

    public Team getTeamTwo()
    {
        return teamTwo;
    }
}
