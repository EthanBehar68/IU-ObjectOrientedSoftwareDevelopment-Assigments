package p532.gamemaker.utility;

public class ParseNumberUtility
{
    //Prevent instantiation
    private ParseNumberUtility() {}

    /**
     * Converts the input String to a double
     * or returns null if the String cannot be parsed.
     */
    public static Double parseDoubleOrReturnNull(String input)
    {
        try
        {
            double output = Double.parseDouble(input);
            return output;
        }
        catch (NumberFormatException ex) {
            return null;
        }
    }
}
