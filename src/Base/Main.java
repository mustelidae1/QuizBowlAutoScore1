package Base;

import QuestionParsing.QuestionParser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Team team1 = new Team("Team 1 Name");
        team1.addPlayer(new Player("First T1 Player"));
        team1.addPlayer(new Player("Second T1 Player"));
        team1.addPlayer(new Player("Third T1 Player"));
        team1.addPlayer(new Player("Fourth T1 Player"));

        Team team2 = new Team("Team 2 Name");
        team2.addPlayer(new Player("First T2 Player"));
        team2.addPlayer(new Player("Second T2 Player"));
        team2.addPlayer(new Player("Third T2 Player"));
        team2.addPlayer(new Player("Fourth T2 Player"));
        team2.addPlayer(new Player("Fifth T2 Player"));

        Game game = new Game(team1, team2);


        FXMLLoader loader =  new FXMLLoader(getClass().getResource("game.fxml"));
        Parent root = loader.load();
        GameController gController = loader.getController();
        QuestionParser test = new QuestionParser("Packets/PDFQuestions.pdf");
        game.setTossUps(test.tossUps);
        game.setBonuses(test.bonuses);

        gController.loadGame(game);
        gController.initGuiElements();
        //gController.setActiveTossup(0);

        primaryStage.setTitle("Quiz Bowl Auto Score");
        primaryStage.setScene(new Scene(root, 1200, 600));
        //primaryStage.getScene().getStylesheets().add(getClass().getResource("Game.css").toExternalForm());
        primaryStage.show();
        XMLBuilder xBuilder = new XMLBuilder(game);
        xBuilder.createGameXml("TestGame.xml");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
