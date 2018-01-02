package Base;

import javafx.scene.image.Image;

public abstract class Question
{
    private String body;
    private Image answerImage;
    private boolean attempted;
    private boolean correctlyAnswered;

    public Question(String body, Image answerImage)
    {
        this.setBody(body);
        this.setAnswer(answerImage);
        this.attempted = false;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public Image getAnswer()
    {
        return answerImage;
    }

    public void setAnswer(Image answerImage)
    {
        this.answerImage = answerImage;
    }

    public boolean isAttempted()
    {
        return attempted;
    }

    protected void setAttempted(boolean attempted)
    {
        this.attempted = attempted;
    }



    public void setAnswered(boolean answered)
    {
        this.attempted = answered;
    }

    public boolean isCorrectlyAnswered()
    {
        if(!isAttempted())
        {
            return false;
        }
        return correctlyAnswered;
    }

    protected void setCorrectlyAnswered(boolean correctlyAnswered)
    {
        if(isAttempted() == false)
        {
            throw new IllegalStateException("ERROR! Question has not yet been answered.");
        }
        this.correctlyAnswered = correctlyAnswered;
    }


}
