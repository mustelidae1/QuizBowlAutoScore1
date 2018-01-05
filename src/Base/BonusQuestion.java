package Base;

import javafx.scene.image.Image;

public class BonusQuestion
{
    String partBodies[];
    Image partAnswers[];

    public BonusQuestion(String bodies[], Image[] answers)
    {
        this.partBodies = bodies;
        this.partAnswers = answers;
    }

    public String getPartBody(int index)
    {
        return partBodies[index];
    }

    public Image getPartAnswer(int index)
    {
        return partAnswers[index];
    }
}
