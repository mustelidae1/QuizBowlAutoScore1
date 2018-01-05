package Base;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLBuilder
{
    private Game game;
    private Document doc;

    public XMLBuilder(Game game)
    {
        this.game = game;
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try
        {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
        }
        catch (ParserConfigurationException e)
        {
            System.err.println("Parser Config Exception in XML Builder Constructor");
        }
    }

    public void createGameXml(String filename) throws ParserConfigurationException, TransformerConfigurationException, TransformerException
    {
        // try
        {

            Element rootElement = doc.createElement("Game");

            Element teamsElement = doc.createElement("Teams");
            teamsElement.appendChild(getTeamXmlElement(game.getTeamOne()));
            rootElement.appendChild(teamsElement);
            doc.appendChild(rootElement);


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
        }

//        catch (Exception e)
//        {
//            System.out.println("Error in XML creation.");
//        }
    }

    public Element getTeamXmlElement(Team team) throws ParserConfigurationException
    {
        Element output = doc.createElement("Team");

        Attr nameAttr = doc.createAttribute("Name");
        nameAttr.setValue(team.getTeamName());
        output.setAttributeNode(nameAttr);

        Element playersElement = doc.createElement("Players");
        for(int i = 0; i < team.getPlayers().size(); i++)
        {
            Player player = team.getPlayers().get(i);
            Element playerElement = doc.createElement("Player" + (i + 1));

            Attr playerNameAttr = doc.createAttribute("Name");
            playerNameAttr.setValue(player.getName());
            playerElement.setAttributeNode(playerNameAttr);

            playersElement.appendChild(playerElement);
        }
        output.appendChild(playersElement);

        return  output;
    }
}
