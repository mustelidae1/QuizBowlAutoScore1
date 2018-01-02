package Base;

import javafx.scene.image.Image;

public class TossUpQuestion extends Question
{
    private TossUpAnswerAttempt firstAnswerAttempt;
    private TossUpAnswerAttempt secondAnswerAttempt;

    public TossUpQuestion(String body, Image answerImage)
    {
        super(body, answerImage);
    }

    public TossUpAnswerAttempt getTeamAnswerAttempt(int teamIndex)
    {
        TossUpAnswerAttempt result = null;
        if(firstAnswerAttempt != null && firstAnswerAttempt.teamIndex == teamIndex)
        {
            result = getFirstAnswerAttempt();
        }
        else if(secondAnswerAttempt != null && secondAnswerAttempt.teamIndex == teamIndex)
        {
            result = getSecondAnswerAttempt();
        }
        return  result;
    }

    public TossUpAnswerAttempt getFirstAnswerAttempt()
    {
        return firstAnswerAttempt;
    }

    public void setFirstAnswerAttempt(TossUpAnswerAttempt firstAnswerAttempt)
    {
        if(firstAnswerAttempt.correct && isCorrectlyAnswered())
        {
            throw new IllegalStateException("Attempting to add a correct answer to a question already correctly answered.");
        }
        setAttempted(true);
        this.firstAnswerAttempt = firstAnswerAttempt;
        setCorrectlyAnswered(firstAnswerAttempt.correct);
        GameController.updateTeamScores();
    }

    public TossUpAnswerAttempt getSecondAnswerAttempt()
    {
        return secondAnswerAttempt;
    }

    public void setSecondAnswerAttempt(TossUpAnswerAttempt secondAnswerAttempt)
    {
        if(firstAnswerAttempt == null)
        {
            throw new IllegalStateException("Attempting to set second answer without setting first");
        }
        if(secondAnswerAttempt.correct && isCorrectlyAnswered())
        {
            throw new IllegalStateException("Attempting to add a correct answer to a question already correctly answered.");
        }
        setAttempted(true);
        this.secondAnswerAttempt = secondAnswerAttempt;
        setCorrectlyAnswered(firstAnswerAttempt.correct);
        GameController.updateTeamScores();
    }

    public static class TossUpAnswerAttempt
    {
        int teamIndex;
        int playerIndex;
        boolean correct;
        Enums.ATTEMPT_RESULT attemptResult;

        TossUpAnswerAttempt(int teamIndex, int playerIndex, Enums.ATTEMPT_RESULT attemptResult)
        {
            setTeamIndex(teamIndex);
            setPlayerIndex(playerIndex);
            setAttemptResult(attemptResult);
            setCorrect(Utility.answerResultToBoolean(attemptResult));
        }

        public int getTeamIndex()
        {
            return teamIndex;
        }

        public void setTeamIndex(int teamIndex)
        {
            this.teamIndex = teamIndex;
        }

        public int getPlayerIndex()
        {
            return playerIndex;
        }

        public void setPlayerIndex(int playerIndex)
        {
            this.playerIndex = playerIndex;
        }

        public boolean isCorrect()
        {
            return correct;
        }

        public void setCorrect(boolean correct)
        {
            this.correct = correct;
        }

        public Enums.ATTEMPT_RESULT getAttemptResult()
        {
            return attemptResult;
        }

        public void setAttemptResult(Enums.ATTEMPT_RESULT attemptResult)
        {
            this.attemptResult = attemptResult;
        }
    }
}
