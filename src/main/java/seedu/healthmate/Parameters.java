package seedu.healthmate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum Parameter {
    CALORIE_SIGNALLER("/c"),
    PORTIONS_SIGNALLER("/p");

    private String prefix;

    // Enum constructor
    Parameter(String prefix) {
        this.prefix = prefix;
    }

    // Getter for the prefix
    public String getPrefix() {
        return prefix;
    }

    // Method to parse the value associated with a parameter
    public static int parseParameter(String input, Parameter param) {
        // Create a regex pattern for the parameter prefix (like /c or /p)
        String regex = param.getPrefix() + "(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            // Extract and return the numeric value after the prefix
            return Integer.parseInt(matcher.group(1));
        } else {
            // Default value if the parameter is not found
            return param == PORTIONS_SIGNALLER ? 1 : 0; // Default portions is 1, calories 0
        }
    }
}