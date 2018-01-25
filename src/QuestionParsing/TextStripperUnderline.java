package QuestionParsing;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;

public class TextStripperUnderline extends PDFTextStripper
{
    private final static String TARGET_ANSWER_TEXT = "answer:";
    private final static int NUM_TOSSUPS = 24;
    private final static int MAX_SINGLE_LINE_Y_DIFF = 10; //The amount of variation allowed in Y coordinates before a word is considered to be on a separate line.

    private int answerMatchIndex;
    private int numCharsPrinted;
    private boolean tossUpAnswerStartFound;
    private boolean tossUpAnswerEndFound;
    private boolean bonusAnswerFound;
    private int numAnswersFound;
    private int pageIndex = 0;
    private float startX;
    private float startY;
    private float pageWidth;
    private float answerHeight;
    private float pageHeight;
    private float previousY;
    private TextPosition lastNonSpace;


    private ArrayList<AnswerPosition> answerPositions;

    private int tempCharCount;

    public TextStripperUnderline() throws IOException
    {
        super();
        answerMatchIndex = 0;
        numCharsPrinted = 0;
        startX = 0;
        startY = 0;
        pageWidth = 0;
        answerHeight = 0;
        pageHeight = 0;
        tossUpAnswerStartFound = false;
        tossUpAnswerEndFound = false;
        answerPositions = new ArrayList<>();
    }

    protected void processTextPosition( TextPosition text )
    {
        if(numAnswersFound < NUM_TOSSUPS)
        {
            tossUpProcessTextPosition(text);
        }
        else
        {
            bonusProcessTextPosition(text);
        }
    }

    private void tossUpProcessTextPosition( TextPosition text )
    {
        if(tossUpAnswerEndFound)
        {
            answerPositions.add(new AnswerPosition(startX, startY, pageWidth, answerHeight, pageHeight, pageIndex));
            tossUpAnswerStartFound = false;
            tossUpAnswerEndFound = false;
            numAnswersFound++;
        }

        if(!tossUpAnswerStartFound && (text.getUnicode().charAt(0) == TARGET_ANSWER_TEXT.charAt(answerMatchIndex)) )
        {
            if(answerMatchIndex == 0)
            {
                startX = text.getX();
                startY = text.getY();
                pageWidth = text.getPageWidth();
                answerHeight = text.getHeight();
                pageHeight = text.getPageHeight();
            }
            answerMatchIndex++;
            if(answerMatchIndex == TARGET_ANSWER_TEXT.length()) //Finished a complete match
            {
                if(startY < previousY) //Semi-hacky way of determining if New page
                {
                    pageIndex++;
                }
                previousY = startY;
                tossUpAnswerStartFound = true;
                answerMatchIndex = 0;
            }
        }
        else
        {
            answerMatchIndex = 0;
        }

        if(text.getUnicode().charAt(0) != 160 && text.getUnicode().charAt(0) != 32)
        {
            if(text.getUnicode().charAt(0) != '<')
            {
                lastNonSpace = text;
            }
            else if(tossUpAnswerStartFound)
            {
                System.out.println("Last char: " + lastNonSpace.getUnicode());
                double yDifference =  Math.abs(startY - lastNonSpace.getY());
                if(yDifference > MAX_SINGLE_LINE_Y_DIFF)
                {
                    answerHeight += yDifference;
                    startY = lastNonSpace.getY();
                }
                tossUpAnswerEndFound = true;
            }
        }
        super.processTextPosition(text);
    }

    private void bonusProcessTextPosition(TextPosition text)
    {
        if(bonusAnswerFound)
        {
            answerPositions.add(new AnswerPosition(startX, startY, pageWidth, answerHeight, pageHeight, pageIndex));
            bonusAnswerFound = false;
            numAnswersFound++;
        }

        if(text.getUnicode().charAt(0) == TARGET_ANSWER_TEXT.charAt(answerMatchIndex))
        {
            if(answerMatchIndex == 0)
            {
                startX = text.getX();
                startY = text.getY();
                pageWidth = text.getPageWidth();
                answerHeight = text.getHeight();
                pageHeight = text.getPageHeight();
            }
            answerMatchIndex++;
            if(answerMatchIndex == TARGET_ANSWER_TEXT.length()) //Finished a complete match
            {
                if(startY < previousY) //Semi-hacky way of determining if New page
                {
                    pageIndex++;
                }
                previousY = startY;
                bonusAnswerFound = true;
                answerMatchIndex = 0;
            }
        }
        else
        {
            answerMatchIndex = 0;
        }

        super.processTextPosition(text);
    }

    public ArrayList<AnswerPosition> getAnswerPositions()
    {
        return this.answerPositions;
    }
}
