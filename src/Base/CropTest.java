package Base;

import QuestionParsing.AnswerPosition;
import QuestionParsing.TextStripperUnderline;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CropTest
{
    public static void main(String args[]) throws IOException
    {
        File file = new File("Packets/PDFQuestionsForCropping.pdf");
        PDDocument doc = PDDocument.load(file);
        PDPage page = doc.getPage(0);
        TextStripperUnderline underlinedText = new TextStripperUnderline();
        underlinedText.getText(doc);

        AnswerPosition pos = underlinedText.getAnswerPositions().get(3);

        float captureWidth = pos.getPageWidth() - pos.getStartX();
        float calcY = pos.getBottomToTopY() - pos.getAnswerHeight();
        page.setCropBox(new PDRectangle(pos.getStartX(), pos.getBottomToTopY() - 3, pos.getPageWidth(), (int) (pos.getAnswerHeight() * 2)));

        PDDocument doc2 = new PDDocument();
        doc2.addPage(page);
        doc2.save("CalcCrop.pdf");
        PDFRenderer renderer = new PDFRenderer(doc2);
        BufferedImage bim = renderer.renderImageWithDPI(0, 100, ImageType.RGB);
        ImageIOUtil.writeImage(bim, "TestImageOutput.png", 100);

        doc.close();
        doc2.close();
    }
}
