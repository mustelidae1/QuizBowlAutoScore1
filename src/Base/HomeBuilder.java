package Base;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.File;

public class HomeBuilder
{
    private final float PLAYER_BUTTON_SPACING = 5;
    private final int NUM_ANSWER_BUTTONS = 4;

    private GameController gameController;

    private Label team1ScoreLabel;
    private Label team2ScoreLabel;
    private PlayerContainer[] t1PlayerContainers;
    private PlayerContainer[] t2PlayerContainers;
    private BorderPane baseBorderPane;

    public HomeBuilder(Game game, GameController gameController) //TODO Probably should find a way to not have to pass in the controller
    {
        this.gameController = gameController;

        t1PlayerContainers = new PlayerContainer[game.getTeamOne().getNumPlayers()];
        t2PlayerContainers = new PlayerContainer[game.getTeamTwo().getNumPlayers()];
        populatePlayerContainers(game.getTeamOne(), t1PlayerContainers);
        populatePlayerContainers(game.getTeamTwo(), t2PlayerContainers);
    }

    public void initHome(BorderPane baseBorderPane, AnchorPane scorePane1, AnchorPane scorePane2)
    {
        this.baseBorderPane = baseBorderPane;

        GridPane topGrid = (GridPane) baseBorderPane.getTop();
        team1ScoreLabel = new Label();
        team2ScoreLabel = new Label();
        team1ScoreLabel.setFont(new Font("Arial", 20));
        team2ScoreLabel.setFont(new Font("Arial", 20));
        
        //AnchorPane scorePane1 = (AnchorPane)getFromGridPane(topGrid, 0,0); 
        //AnchorPane scorePane2 = (AnchorPane)getFromGridPane(topGrid,0,2); 
        
        
        scorePane1.getChildren().add(team1ScoreLabel); 
        scorePane1.setRightAnchor(team1ScoreLabel, 5.0);
        scorePane2.getChildren().add(team2ScoreLabel); 
        scorePane2.setRightAnchor(team2ScoreLabel, 5.0);

        VBox leftVBox = (VBox) baseBorderPane.getLeft();
        VBox rightVBox = (VBox) baseBorderPane.getRight();
        for(PlayerContainer playerContainer : t1PlayerContainers)
        {
            leftVBox.getChildren().add(playerContainer);
        }
        for(PlayerContainer playerContainer : t2PlayerContainers)
        {
            rightVBox.getChildren().add(playerContainer);
        }
    }
    
    private Node getFromGridPane(GridPane grid, int row, int col) {
    	Node result = null;
        ObservableList<Node> children = grid.getChildren();

        for (Node node : children) {
            if(GridPane.getRowIndex(grid) == row && GridPane.getRowIndex(grid) == col) {
                result = node;
                break;
            }
        }

        return result;
    }

    private void populatePlayerContainers(Team team, PlayerContainer[] containers)
    {
        for(int i = 0; i < containers.length; i++)
        {
            Player player = team.getPlayers().get(i);
            containers[i] = new PlayerContainer(player.getName());
        }

    }

    public Label getTeam1ScoreLabel()
    {
        return team1ScoreLabel;
    }

    public Label getTeam2ScoreLabel()
    {
        return team2ScoreLabel;
    }

    public PlayerContainer[] getT1PlayerContainers()
    {
        return t1PlayerContainers;
    }

    public PlayerContainer[] getT2PlayerContainers()
    {
        return t2PlayerContainers;
    }

    public BorderPane getBaseBorderPane()
    {
        return baseBorderPane;
    }

    public class PlayerContainer extends VBox //Game controller needs access to PlayerContainer
    {
        private PlayerAnswerButton[] answerButtons;

        PlayerContainer(String name)
        {
            super();
            answerButtons = new PlayerAnswerButton[NUM_ANSWER_BUTTONS];
            TextField text = new TextField(name); 
            text.setEditable(false);
            getChildren().add(text);
            getChildren().add(createButtonContainer());
        }

        private HBox createButtonContainer()
        {
            HBox output = new HBox();
            output.setSpacing(50);
            PlayerAnswerButton powerButton = new PlayerAnswerButton("++", "008800");  // Was Power
            PlayerAnswerButton correctButton = new PlayerAnswerButton("+", "008800");  // Was Correct
            PlayerAnswerButton incorrectButton = new PlayerAnswerButton("o", "CF2D1F");  // Was Incorrect
            PlayerAnswerButton negativeButton = new PlayerAnswerButton("--", "CF2D1F"); // Was Negative

            answerButtons[0] = powerButton;
            answerButtons[1] = correctButton;
            answerButtons[2] = incorrectButton;
            answerButtons[3] = negativeButton;

            output.getChildren().add(powerButton);
            output.getChildren().add(correctButton);
            output.getChildren().add(incorrectButton);
            output.getChildren().add(negativeButton);
            output.setSpacing(PLAYER_BUTTON_SPACING);
            return output;
        }

        public PlayerAnswerButton[] getAnswerButtons()
        {
            return answerButtons;
        }

    }

    public class PlayerAnswerButton extends Button  //Game controller needs access to PlayerAnswerButton
    {
        private String activatedColorString;

        PlayerAnswerButton(String text)
        {
            super(text);
            setOnAction((ActionEvent event) -> {
                gameController.handleContainerButtonClicked(event);
            });
        }

        PlayerAnswerButton(String text, String activatedColorString)
        {
            this(text);
            setActivatedColorString(activatedColorString);
        }

        public void setActivated(boolean activated)
        {
            if(activated)
            {
                setStyle("-fx-background-color: #" + activatedColorString + ";");
            }
            else
            {
                setStyle("");
            }
        }

        public void setActivatedColorString(String activatedColorString)
        {
            this.activatedColorString = activatedColorString;
        }
    }


}
