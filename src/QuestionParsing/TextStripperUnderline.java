package QuestionParsing;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;

public class TextStripperUnderline extends PDFTextStripper
{
    private final static String TARGET_ANSWER_TEXT = "answer:";

    private int answerMatchIndex;
    private int numCharsPrinted;
    //private boolean answerStartFound;
    private boolean answerEndFound;
    private int pageIndex = 0;
    private float startX;
    private float startY;
    private float pageWidth;
    private float answerHeight;
    private float pageHeight;
    private float previousY;
    private int temPos = 0;
    private char lastNonSpace;


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
        //answerStartFound = false;
        answerEndFound = false;
        answerPositions = new ArrayList<>();
    }

    protected void processTextPosition( TextPosition text )
    {
//        String tempMatch = "feet/inches";
//        if(tempMatch.charAt(temPos) == text.getUnicode().charAt(0))
//        {
//            temPos++;
//            if(temPos == tempMatch.length())
//            {
//                System.out.println("Stuff");
//                temPos = 0;
//            }
//        }
//        else
//        {
//            temPos = 0;
//        }

        if(answerEndFound)
        {
            answerPositions.add(new AnswerPosition(startX, startY, pageWidth, answerHeight, pageHeight, pageIndex));
            //answerStartFound = false;
            answerEndFound = false;
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
                answerEndFound = true;
                answerMatchIndex = 0;
            }
        }
        else
        {
            answerMatchIndex = 0;
        }

//        if(text.getUnicode().charAt(0) != 160 && text.getUnicode().charAt(0) != 32)
//        {
//            if(text.getUnicode().charAt(0) != '<')
//            {
//                lastNonSpace = text.getUnicode().charAt(0);
//            }
//            else if(answerStartFound)
//            {
//                System.out.println("Last char: " + lastNonSpace);
//            }
//        }
//        else
//        {
//
//        }
        super.processTextPosition(text);
    }

    public ArrayList<AnswerPosition> getAnswerPositions()
    {
        return this.answerPositions;
    }
}
