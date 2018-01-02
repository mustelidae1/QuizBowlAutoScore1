package QuestionParsing;

public class AnswerPosition
{
    private float startX;
    private float bottomToTopY;
    private float pageWidth;
    private float answerHeight;

    public AnswerPosition(float startX, float startY, float pageWidth, float answerHeight, float pageHeight)
    {
        this.startX = startX;
        this.bottomToTopY = pageHeight - startY;
        this.pageWidth = pageWidth;
        this.answerHeight = answerHeight;
    }

    public float getStartX()
    {
        return startX;
    }

    public void setStartX(float startX)
    {
        this.startX = startX;
    }

    public float getBottomToTopY()
    {
        return bottomToTopY;
    }

    public void setBottomToTopY(float bottomToTopY)
    {
        this.bottomToTopY = bottomToTopY;
    }

    public float getPageWidth()
    {
        return pageWidth;
    }

    public void setPageWidth(float pageWidth)
    {
        this.pageWidth = pageWidth;
    }

    public float getAnswerHeight()
    {
        return answerHeight;
    }

    public void setAnswerHeight(float answerHeight)
    {
        this.answerHeight = answerHeight;
    }
}
