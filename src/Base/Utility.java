package Base;

public class Utility
{
    public final static int POWER_POINTS = 15;
    public final static int CORRECT_POINTS = 10;
    public final static int INCORRECT_POINTS = 0;
    public final static int NEGATIVE_POINTS = -5;


    public static String stripExtraWhitespace(String s)
    {
        Character c = (char)160;
        s = s.replaceAll("\n|\r|\t", "");
        s = s.replace(c.toString(), " ");
        return s;
    }

    public static boolean answerResultToBoolean(Enums.ATTEMPT_RESULT attemptResult)
    {
        if(attemptResult == Enums.ATTEMPT_RESULT.CORRECT || attemptResult == Enums.ATTEMPT_RESULT.POWER)
        {
            return  true;
        }
        else
        {
            return false;
        }
    }

    public static int convertResultToPoints(Enums.ATTEMPT_RESULT attemptResult)
    {
        int output;
        if(attemptResult == Enums.ATTEMPT_RESULT.POWER)
        {
            output = POWER_POINTS;
        }
        else if(attemptResult == Enums.ATTEMPT_RESULT.CORRECT)
        {
            output = CORRECT_POINTS;
        }
        else if(attemptResult == Enums.ATTEMPT_RESULT.INCORRECT)
        {
            output = INCORRECT_POINTS;
        }
        else
        {
            output = NEGATIVE_POINTS;
        }
        return output;
    }
}
