package QuestionParsing;

import Base.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class QuestionParser
{
    private final static double LINE_HEIGHT_INCREASE = 6.0;
    private final static String ANSWER_IDENTIFIER_TEXT = "answer:";

    public ArrayList<TossUpQuestion> tossUps;
    public ArrayList<BonusQuestion> bonuses;
    private static TextStripperUnderline underlineStripper;
    private static PDDocument doc;
    private static int numTossups;

    public QuestionParser(String filepath) throws FileNotFoundException, IOException
    {
        this(new File(filepath));
    }

    public QuestionParser(File file) throws IOException
    {
        tossUps = new ArrayList<>();
        bonuses = new ArrayList<>();

        doc = PDDocument.load(file);
        underlineStripper = new TextStripperUnderline();
        String text = underlineStripper.getText(doc);

        parseStringForTossUps(text, tossUps);
        parseStringForBonuses(text, bonuses);
        doc.close();
    }

    private static void parseStringForTossUps(String text, ArrayList<TossUpQuestion> questionList) throws IOException
    {
        boolean hasQuestions = true;
        int i = 1;
        while(hasQuestions)
        {
            int nextQuestionLocation = text.indexOf("\n" + i + "."); //TODO: Increment index each time to reduce search distance (Schmell the Painter problem)
            if(nextQuestionLocation == -1)
            {
                numTossups = i - 1;
                hasQuestions = false;
            }
            else
            {
                int nextQuestionEnd = text.indexOf(ANSWER_IDENTIFIER_TEXT, nextQuestionLocation); //TODO: Support capitalization
                String questionString = text.substring(nextQuestionLocation, nextQuestionEnd);
                questionString = Utility.stripExtraWhitespace(questionString);
                questionList.add(new TossUpQuestion(questionString, getAnswerImage(i - 1)));
                i++;
            }
        }
    }

    private static void parseStringForBonuses(String text, ArrayList<BonusQuestion> questionList) throws IOException
    {
        String trimmedText = text.substring(text.indexOf("Bonuses"), text.length()); //TODO: Make this more robust
        boolean hasQuestions = true;
        int i = 1;
        while(hasQuestions)
        {
            int nextBonusLocation = trimmedText.indexOf("\n" + i + ".");
            if(nextBonusLocation == -1)
            {
                hasQuestions = false;
            }
            else
            {
                int nextBonusEnd = trimmedText.indexOf("\n" + (i + 1) + ".");
                nextBonusEnd = nextBonusEnd == -1 ? trimmedText.length() : nextBonusEnd; //Set to end of text if next question not found
                String bonusString = trimmedText.substring(nextBonusLocation, nextBonusEnd);
                String[] questionParts = getBonusParts(bonusString);
                Image[] answerParts = getBonusImages(i - 1);

                BonusQuestion bonus = new BonusQuestion(questionParts, answerParts);
                questionList.add(bonus);
            }
            i++;
        }
    }

    private static String[] getBonusParts(String bonusText)
    {
        String[] output = new String[3];
        String[] identifiers = {"", "\nb.", "\nc."};
        for(int i = 0; i < output.length; i++)
        {
            String identifier = identifiers[i];
            int partStart = bonusText.toLowerCase().indexOf(identifier);
            partStart = (i == 0) ? 0 : partStart; //Part starts at 0 for first question
            int partEnd = bonusText.toLowerCase().indexOf("answer:");
            output[i] = Utility.stripExtraWhitespace(bonusText.substring(partStart, partEnd));
            bonusText = bonusText.substring(partEnd + 7, bonusText.length()); //Trim up to and including occurrence of "answer: "
        }

        return output;
    }

    private static Image[] getBonusImages(int bonusIndex)
    {
        Image[] output = new Image[3];
        for(int i = 0; i < output.length; i++)
        {
            try
            {
                output[i] = getAnswerImage(numTossups + (bonusIndex * 3) + i);
            }
            catch (IOException e)
            {
                System.err.println("Couldn't get answer image in getBonusImages.");
            }
        }

        return  output;
    }

    private static Image getAnswerImage(int index) throws IOException
    {
        AnswerPosition pos = underlineStripper.getAnswerPositions().get(index);

        PDPage page = doc.getPage(pos.getPageIndex());
        page.setCropBox(new PDRectangle(pos.getStartX(), pos.getBottomToTopY() - 3, pos.getPageWidth(), (int) (pos.getAnswerHeight() + LINE_HEIGHT_INCREASE)));

        PDDocument croppedDocument = new PDDocument();
        croppedDocument.addPage(page);
        PDFRenderer renderer = new PDFRenderer(croppedDocument);
        BufferedImage bim = renderer.renderImageWithDPI(0, 150, ImageType.RGB);
        Image image = SwingFXUtils.toFXImage(bim, null);
        croppedDocument.close();
        return image;
    }
}
