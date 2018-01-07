package Base;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

public class Game
{
    private ArrayList<TossUpQuestion> tossUps;
    private ArrayList<BonusQuestion> bonuses;
    private Team teamOne;
    private Team teamTwo;
    private IntegerProperty[] teamScoreProperties;
    private boolean completed;

    public Game(Team teamOne, Team teamTwo)
    {
        tossUps = new ArrayList<>();
        bonuses = new ArrayList<>();
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.completed = false;
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

    public ArrayList<BonusQuestion> getBonuses()
    {
        return  bonuses;
    }

    public void setBonuses(ArrayList<BonusQuestion> bonuses)
    {
        this.bonuses = bonuses;
    }

    public Team getTeamOne()
    {
        return teamOne;
    }

    public Team getTeamTwo()
    {
        return teamTwo;
    }

    public boolean isCompleted()
    {
        return completed;
    }

    public void setCompleted(boolean completed)
    {
        this.completed = completed;
    }
}
