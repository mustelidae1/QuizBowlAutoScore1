package QuestionParsing;

import Base.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class QuestionParser
{
    public ArrayList<TossUpQuestion> tossUps;
    public ArrayList<Question> bonuses;
    private static TextStripperUnderline underlineStripper;
    private static PDDocument doc;

    public QuestionParser(String filepath) throws FileNotFoundException, IOException
    {
        tossUps = new ArrayList<>();
        bonuses = new ArrayList<>();

        File file = new File(filepath);
        doc = PDDocument.load(file);
        underlineStripper = new TextStripperUnderline();
        String text = underlineStripper.getText(doc);

        parseStringForTossUps(text, tossUps);
        doc.close();
    }

    private static void parseStringForTossUps(String text, ArrayList<TossUpQuestion> questionList) throws IOException
    {
        boolean hasQuestions = true;
        int i = 1;
        while(hasQuestions)
        {
            int nextQuestionLocation = text.indexOf("\n" + i + ".");
            if(nextQuestionLocation == -1)
            {
                hasQuestions = false;
            }
            else
            {
                int nextQuestionEnd = text.indexOf("answer:", nextQuestionLocation);
                int nextAnswerEnd = text.indexOf("\n", nextQuestionEnd);
                String questionString = text.substring(nextQuestionLocation, nextQuestionEnd);
                questionString = Utility.stripExtraWhitespace(questionString);
                String answerString = text.substring(nextQuestionEnd + 8, nextAnswerEnd);
                answerString = Utility.stripExtraWhitespace(answerString);


                questionList.add(new TossUpQuestion(questionString, getAnswerImage(i - 1)));
                i++;
            }
        }
    }

    private static Image getAnswerImage(int index) throws IOException
    {
        AnswerPosition pos = underlineStripper.getAnswerPositions().get(index);

        PDPage page = doc.getPage(0);
        float captureWidth = pos.getPageWidth() - pos.getStartX();
        float calcY = pos.getBottomToTopY() - pos.getAnswerHeight();
        page.setCropBox(new PDRectangle(pos.getStartX(), pos.getBottomToTopY() - 3, pos.getPageWidth(), (int) (pos.getAnswerHeight() * 2)));

        PDDocument croppedDocument = new PDDocument();
        croppedDocument.addPage(page);
        PDFRenderer renderer = new PDFRenderer(croppedDocument);
        BufferedImage bim = renderer.renderImageWithDPI(0, 150, ImageType.RGB);
        Image image = SwingFXUtils.toFXImage(bim, null);
        croppedDocument.close();
        return image;
    }
}
