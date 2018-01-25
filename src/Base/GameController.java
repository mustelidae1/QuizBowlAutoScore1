package Base;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javafx.event.ActionEvent;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class GameController extends application.Controller
{
    private final int SCREEN_TRANSITION_MILLISECONDS = 200;

    //Some of this probably could be moved to the HomeBuilder
    @FXML private TextArea questionText;
    @FXML private ImageView answerImageView;
    @FXML private ChoiceBox<String> questionChoiceBox;
    @FXML private ChoiceBox<String> questionPartChoiceBox;
    @FXML private BorderPane baseBorderPane;
    @FXML private Button bonusCorrectButton;
    @FXML private Button bonusIncorrectButton;
    @FXML private Label team1Name;  
    @FXML private Label team2Name; 
    @FXML private AnchorPane scorePane1; 
    @FXML private AnchorPane scorePane2; 

    public NumberBinding team1Score;
    public IntegerProperty team1Prop;

    private static Game game; //Because there only ever should be one controller, there shouldn't be an issue making this static.
    private boolean questionsSet = false;
    private HomeBuilder homeBuilder;
    private ArrayList<Integer> questionsAnswered; //TODO May want to move to model, though it is used only for the GUI
    private Pair<Integer, Integer> t1ButtonActivated[]; //Container index first, button index second.
    private Pair<Integer, Integer> t2ButtonActivated[]; //Container index first, button index second.

    public GameController()
    {
        questionsAnswered = new ArrayList<>();
    }

    public void loadGame(Game game)
    {
        this.game = game;
//        if(game.getTossups().size() > 0)
//        {
//            setChoiceBoxQuestions(game.getTossups().size());
//            questionsSet = true;
//        }

        team1Score = game.getTeamScoreProperty(0).add(0);
        team1Prop = game.getTeamScoreProperty(0);
    }

    public void initGuiElements()
    {
        homeBuilder = new HomeBuilder(game, this);
        homeBuilder.initHome(baseBorderPane, scorePane1, scorePane2);
        homeBuilder.getTeam1ScoreLabel().textProperty().bind(game.getTeamScoreProperty(0).asString());
        homeBuilder.getTeam2ScoreLabel().textProperty().bind(game.getTeamScoreProperty(1).asString());
        t1ButtonActivated = new Pair[game.getTossups().size()];
        t2ButtonActivated = new Pair[game.getTossups().size()];
        if(game.getTossups().size() > 0)
        {
            setChoiceBoxQuestions(game.getTossups().size());
            questionsSet = true;
        }
        setBonusButtonsVisibility(false);
        
        // OT 1/18 
//        team1Name.setText(stats.getCurrentTeam1().getTeamName());
//        team2Name.setText(stats.getCurrentTeam2().getTeamName());
        team1Name.setText("Team 1");
        team2Name.setText("Team 2");

    }

    //Because there only ever should only be one controller, there shouldn't be an issue making this static.
    public static void updateTeamScores()
    {
        game.setTeamScoreProperty(0, getTeamTotalScore(0));
        game.setTeamScoreProperty(1, getTeamTotalScore(1));
    }

    private static int getTeamTotalScore(int teamIndex)
    {
        int numPoints = 0;
        for(int i = 0; i < game.getTossups().size(); i++)
        {
            TossUpQuestion question = game.getTossups().get(i);
            TossUpQuestion.TossUpAnswerAttempt attempt;
            if((attempt = question.getTeamAnswerAttempt(teamIndex)) != null)
            {
               numPoints += Utility.convertResultToPoints(attempt.attemptResult);
               if(question.hasBonus())
               {
                   numPoints += getTossupBonusScore(question);
               }
            }
        }
        return numPoints;
    }

    private static int getTossupBonusScore(TossUpQuestion tossUp)
    {
        int output = 0;
        BonusQuestion bonus = tossUp.getBonusQuestion();
        for(int i = 0; i < bonus.questionParts.length; i++)
        {
            BonusQuestionPart part = bonus.getPart(i);
            if(part.isAttempted() && part.isCorrectlyAnswered())
            {
                output+=10;
            }
        }
        return output;
    }

    @FXML
    private void handleChangedQuestion()
    {
        int questionIndex = questionChoiceBox.getSelectionModel().getSelectedIndex();
        int partIndex = questionPartChoiceBox.getSelectionModel().getSelectedIndex();
        partIndex = partIndex == -1 ? 0 : partIndex; //Part index defaults to 0 if not selected
        updateQuestionPartChoices(questionIndex);

        Question question;
        TossUpQuestion tossUp = game.getTossups().get(questionIndex);
        if(partIndex > 0 && tossUp.hasBonus())
        {
            question = tossUp.getBonusQuestion().questionParts[partIndex - 1];
            setBonusButtonsVisibility(true);
        }
        else
        {
            question = tossUp;
            questionPartChoiceBox.getSelectionModel().select(0);
            setBonusButtonsVisibility(false);
        }

        changeQuestion(question, questionIndex, partIndex);
    }

    private void changeQuestion(Question newQuestion, int tossupIndex, int partIndex) //All questions are either a tossup, or a bonus tied to a tossup
    {
        setQuestionText(newQuestion.getBody());
        setAnswerImage(newQuestion.getAnswer());
        questionChoiceBox.getSelectionModel().select(tossupIndex); //Needed for when question is changed by game, rather than selection
        questionPartChoiceBox.getSelectionModel().select(partIndex); //Needed for when question is changed by game, rather than selection
        updateButtons(tossupIndex);
    }

    private void advanceTossupQuestion()
    {
        int nextIndex = questionChoiceBox.getSelectionModel().getSelectedIndex() + 1;
        if(game.getTossups().size() > nextIndex)
        {
            changeQuestion(game.getTossups().get(nextIndex), nextIndex, 0);
        }
    }

    private void advanceNextPartOrQuestion() //Advances to the next part of the question if there is one, else advances to the next question
    {
        boolean couldAdvance = advanceNextQuestionPart();
        if(!couldAdvance)
        {
            advanceTossupQuestion();
        }
    }

    private boolean advanceNextQuestionPart() //Returns true if advance was successful, false otherwise.
    {
        boolean result;
        int questionIndex = questionChoiceBox.getSelectionModel().getSelectedIndex();
        int nextPartIndex = questionPartChoiceBox.getSelectionModel().getSelectedIndex() + 1;
        if(questionPartChoiceBox.getItems().size() > nextPartIndex)
        {
            BonusQuestionPart nextPart = game.getTossups().get(questionIndex).getBonusQuestion().getPart(nextPartIndex - 1);
            changeQuestion(nextPart, questionIndex, nextPartIndex);
            result = true;
        }
        else
        {
            result = false;
        }
        return result;
    }

    public void handleContainerButtonClicked(ActionEvent e)
    {
        //This method depends on the GUI Hierarchy, which is not ideal
        System.out.println("Button Clicked");
        HomeBuilder.PlayerAnswerButton buttonClicked = (HomeBuilder.PlayerAnswerButton)e.getSource();
        HomeBuilder.PlayerContainer buttonContainer = (HomeBuilder.PlayerContainer) buttonClicked.getParent().getParent();

        HomeBuilder.PlayerContainer playerContainers[];
        Pair<Integer, Integer> activatedButtonList[];
        int teamIndex;
        if(homeBuilder.getBaseBorderPane().getLeft().equals(buttonContainer.getParent()))
        {
            playerContainers = homeBuilder.getT1PlayerContainers();
            activatedButtonList = t1ButtonActivated;
            teamIndex = 0;
        }
        else
        {
            playerContainers = homeBuilder.getT2PlayerContainers();
            activatedButtonList = t2ButtonActivated;
            teamIndex = 1;
        }
        processButtonActivation(buttonClicked, playerContainers, activatedButtonList, teamIndex);
    }

    @FXML ///TEMPORARY This will need to be moved out of fxml eventually
    public void handleBonusButtonClicked(ActionEvent e)
    {
        Button buttonClicked = (Button) e.getSource();
        int questionIndex = questionChoiceBox.getSelectionModel().getSelectedIndex();
        int choiceIndex = questionPartChoiceBox.getSelectionModel().getSelectedIndex();
        if(choiceIndex == 0) //TossUp Selected
        {
            System.err.println("ERROR! Bonus answer button clicked when tossup is selected.");
        }
        BonusQuestionPart bonusPart =  game.getTossups().get(questionIndex).getBonusQuestion().getPart(choiceIndex - 1);
        if(buttonClicked == bonusCorrectButton)
        {
            bonusPart.setCorrectlyAnswered(true);
        }
        else
        {
            bonusPart.setCorrectlyAnswered(false);
        }
        advanceNextPartOrQuestion();
    }

    private void processButtonActivation(HomeBuilder.PlayerAnswerButton button,
                                         HomeBuilder.PlayerContainer playerContainers[],
                                         Pair<Integer, Integer> activatedButtonList[],
                                         int teamIndex)
    {
        int containerIndex = Arrays.asList(playerContainers).indexOf( button.getParent().getParent());
        HBox parent = (HBox)button.getParent();
        int buttonIndex = parent.getChildren().indexOf(button);
        int questionIndex = questionChoiceBox.getSelectionModel().getSelectedIndex();
        activatedButtonList[questionIndex] = new Pair<>(containerIndex, buttonIndex);

        Enums.ATTEMPT_RESULT attemptResult = convertButtonIndexToResult(buttonIndex);
        addTossupAttempt(questionIndex, teamIndex, containerIndex, attemptResult);
        boolean isCorrect = Utility.answerResultToBoolean(attemptResult);
        if(isCorrect)
        {
            questionsAnswered.add(questionIndex);
            updateButtons(questionIndex); //Need to update the buttons before the question is changed
            //TODO This seems like a bizarre use of timeline, but other methods don't work as expected (Sleeps, etc.)
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(SCREEN_TRANSITION_MILLISECONDS), event -> {
                advanceNextPartOrQuestion();
            }));
            timeline.setCycleCount(1);
            timeline.play();
        }
        else
        {
            updateButtons(questionIndex);
        }
    }

    //Needs to only take an index argument because it is used in the selectionChangeHandler
    private void updateButtons(int questionIndex)
    {
        updateTeamButtons(questionIndex, 0);
        updateTeamButtons(questionIndex, 1);
    }

    private void updateTeamButtons(int questionIndex, int teamIndex)
    {
        HomeBuilder.PlayerContainer playerContainers[];
        Pair<Integer, Integer> activatedButtonList[];
        if(teamIndex == 0)
        {
            playerContainers = homeBuilder.getT1PlayerContainers();
            activatedButtonList = t1ButtonActivated;
        }
        else
        {
            playerContainers = homeBuilder.getT2PlayerContainers();
            activatedButtonList = t2ButtonActivated;
        }

        boolean questionAnswered = questionsAnswered.contains(questionIndex);
        for(int i = 0; i < playerContainers.length; i++)
        {
            HomeBuilder.PlayerContainer container = playerContainers[i];
            for(int j = 0; j < container.getAnswerButtons().length; j++)
            {
                Pair<Integer, Integer> activatedButton = activatedButtonList[questionIndex];
                if((activatedButton != null) && (new Pair<Integer, Integer>(i, j).equals(activatedButton))) //Checks if the indexes match
                {
                    container.getAnswerButtons()[j].setActivated(true);
                    container.getAnswerButtons()[j].setDisable(false);
                }
                else
                {
                    container.getAnswerButtons()[j].setActivated(false);
                    if(questionAnswered || activatedButtonList[questionIndex] != null) //Disabled if question correctly answered or team already made an attempt.
                    {
                        container.getAnswerButtons()[j].setDisable(true);
                    }
                    else
                    {
                        container.getAnswerButtons()[j].setDisable(false);
                    }
                }
            }
        }
    }

    private Enums.ATTEMPT_RESULT convertButtonIndexToResult(int index)
    {
        Enums.ATTEMPT_RESULT attemptResult;
        if(index == 0) //Correct answers are first and end the question, this is rather hacky
        {
            attemptResult = Enums.ATTEMPT_RESULT.POWER;
        }
        else if(index == 1)
        {
            attemptResult = Enums.ATTEMPT_RESULT.CORRECT;
        }
        else if(index == 2)
        {
            attemptResult = Enums.ATTEMPT_RESULT.INCORRECT;
        }
        else
        {
            attemptResult = Enums.ATTEMPT_RESULT.NEGATIVE;
        }
        return  attemptResult;
    }

    private void addTossupAttempt(int questionIndex, int teamIndex, int playerIndex, Enums.ATTEMPT_RESULT result)
    {
        TossUpQuestion tossUp = game.getTossups().get(questionIndex);
        if(tossUp.isAttempted() && tossUp.isCorrectlyAnswered())
        {
            System.err.println("Question already correctly answered");
        }
        if(tossUp.getFirstAnswerAttempt() == null)
        {
            tossUp.setFirstAnswerAttempt(new TossUpQuestion.TossUpAnswerAttempt(teamIndex, playerIndex, result));
        }
        else if(tossUp.getSecondAnswerAttempt() == null)
        {
            tossUp.setSecondAnswerAttempt(new TossUpQuestion.TossUpAnswerAttempt(teamIndex, playerIndex, result));
        }
        else
        {
            System.err.println("Question already has two attempts. Editing questions currently not supported.");
        }

        if(tossUp.isCorrectlyAnswered())
        {
            int nextBonusIndex = game.getNextBonusIndex();
            tossUp.setBonusQuestion(game.getBonuses().get(nextBonusIndex), nextBonusIndex);
            game.incrementNextBonusIndex();
        }

        updateQuestionPartChoices(questionIndex);
    }

    private void setQuestionText(String text)
    {
        questionText.setText(text);
    }

    private void setAnswerImage(Image image)
    {
        answerImageView.setImage(image);
    }

    private void setChoiceBoxQuestions(int numQuestions)
    {
        ObservableList<String> list = FXCollections.observableArrayList();
        for(int i = 0; i < numQuestions; i++)
        {
            list.add("Question " + (i + 1));
        }
        questionChoiceBox.setItems(list);
        questionChoiceBox.getSelectionModel().select(0); //Select first question to start
        updateQuestionPartChoices(0);
    }

    private void updateQuestionPartChoices(int questionIndex) //This currently also sets the selected index to 0
    {
        ObservableList<String> partChoices = FXCollections.observableArrayList();
        partChoices.add("Toss Up");
        if(game.getTossups().get(questionIndex).hasBonus())
        {
            partChoices.add("Bonus Part A");
            partChoices.add("Bonus Part B");
            partChoices.add("Bonus Part C");
        }
        questionPartChoiceBox.setItems(partChoices);
    }

    private void setBonusButtonsVisibility(boolean visibility)
    {
        bonusCorrectButton.setVisible(visibility);
        bonusIncorrectButton.setVisible(visibility);
    }

//    public void setActiveTossup(int index)
//    {
//        if(questionsSet)
//        {
//            changeQuestion(game.getTossups().get(index), index, 0);
//        }
//        else
//        {
//            System.err.println("Cannot change active questions, no questions have been added.");
//        }
//    }
}
