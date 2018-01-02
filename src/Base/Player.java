package Base;

public class Player
{
    public Enums.ATTEMPT_RESULT[][] questionsAnswered;

    private String name;

    public Player(String name)
    {
        this.name = name;
        questionsAnswered = new Enums.ATTEMPT_RESULT[20][50]; //Arbitrarily limiting to 20 rounds and 50 questions.
    }

    public String getName()
    {
        return name;
    }
}
