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
<<<<<<< HEAD
    private boolean answerStartFound;
    private boolean answerEndFound;
=======
    private int numCharsPrinted;
    private boolean termStringFound;
    private boolean answerStartFound;
    private boolean answerEndFound;
    private boolean bonusAnswerFound;
>>>>>>> 0ed9f12691430bddd23a43bf0aefb89fd8cf7215
    private int numAnswersFound;
    private int pageIndex = 0;
    private int answerTermMatchIndex;
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
        answerTermMatchIndex = 0;
<<<<<<< HEAD
=======
        numCharsPrinted = 0;
>>>>>>> 0ed9f12691430bddd23a43bf0aefb89fd8cf7215
        startX = 0;
        startY = 0;
        pageWidth = 0;
        answerHeight = 0;
        pageHeight = 0;
        answerStartFound = false;
        answerEndFound = false;
<<<<<<< HEAD
=======
        termStringFound = false;
>>>>>>> 0ed9f12691430bddd23a43bf0aefb89fd8cf7215
        answerPositions = new ArrayList<>();
    }

    protected void processTextPosition( TextPosition text )
    {
        if(numAnswersFound < NUM_TOSSUPS)
        {
            processTextPosition(text, "<");
        }
        else if(numAnswersFound % 3 == 0)
        {
            processTextPosition(text, "B.");
        }
        else if(numAnswersFound % 3 == 1)
        {
            processTextPosition(text, "C.");
        }
        else
        {
            processTextPosition(text, "<");
        }
    }

    private void processTextPosition(TextPosition text, String answerTermString)
    {
        if(answerEndFound)
        {
            answerPositions.add(new AnswerPosition(startX, startY, pageWidth, answerHeight, pageHeight, pageIndex));
            answerStartFound = false;
            answerEndFound = false;
            answerTermMatchIndex = 0;
            numAnswersFound++;
        }

        if(!answerStartFound && (text.getUnicode().charAt(0) == TARGET_ANSWER_TEXT.charAt(answerMatchIndex)) )
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
                answerStartFound = true;
                answerMatchIndex = 0;
            }
        }
        else
        {
            answerMatchIndex = 0;
        }

        if(text.getUnicode().charAt(0) == answerTermString.charAt(answerTermMatchIndex))
        {
            answerTermMatchIndex++;
        }
        else
        {
            answerTermMatchIndex = 0;
        }
        if(answerTermMatchIndex == answerTermString.length())
        {
            answerEndFound = true;
        }


        if(text.getUnicode().charAt(0) != 160 && text.getUnicode().charAt(0) != 32) //Dont' worry about spaces
        {
            if(!answerEndFound && answerTermMatchIndex == 0) //For multi character terminators, don't include parts of terminator
            {
                lastNonSpace = text;
            }
            else if(answerStartFound)
            {
                System.out.println("Last char: " + lastNonSpace.getUnicode());
                double yDifference =  Math.abs(startY - lastNonSpace.getY());
                if(yDifference > MAX_SINGLE_LINE_Y_DIFF)
                {
                    answerHeight += yDifference;
                    startY = lastNonSpace.getY();
                }
            }
        }
        super.processTextPosition(text);
    }

    public ArrayList<AnswerPosition> getAnswerPositions()
    {
        return this.answerPositions;
    }
}
