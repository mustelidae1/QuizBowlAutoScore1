package Base;

import javafx.scene.image.Image;

public class BonusQuestion
{
    BonusQuestionPart[] questionParts;

    public BonusQuestion(String bodies[], Image[] answers)
    {
        questionParts = new BonusQuestionPart[3];

        for(int i = 0; i < questionParts.length; i++)
        {
            questionParts[i] = new BonusQuestionPart(bodies[i], answers[i]);
        }
    }

    public BonusQuestionPart getPart(int index)
    {
        return questionParts[index];
    }


}
