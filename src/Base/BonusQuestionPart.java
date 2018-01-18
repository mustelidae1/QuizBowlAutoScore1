package Base;

import javafx.scene.image.Image;

public class BonusQuestionPart extends Question
{
    public BonusQuestionPart(String body, Image answer)
    {
        super(body, answer);
    }

    public void setCorrectlyAnswered(boolean answered)
    {
        super.setCorrectlyAnswered(answered);
        GameController.updateTeamScores();
    }
}
