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
    // Method to parse the value associated with a parameter
    public static int parseParameter(String input, Parameter param) throws NumberFormatException {
        // Create a regex pattern for the parameter prefix (like /c or /p)
        String regex = param.getPrefix() + "(\\d+)(\\s|$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        boolean containsParam = input.contains(param.getPrefix());

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            // Contains param but bad format response is -2
            // If param is missing return 1 for portions for default and -1 for Calories
            return containsParam? -2: param == PORTIONS_SIGNALLER?1:-1;

        }
    }
    public static int getPortions(String input) throws BadPortionException {
        int portions = parseParameter(input, Parameter.PORTIONS_SIGNALLER);
        if (portions == -2) {
            throw new BadPortionException();
        }
        return parseParameter(input, Parameter.PORTIONS_SIGNALLER);
    }
    public static int getCalories(String input) throws BadCalorieException, EmptyCalorieException {
        int calories = parseParameter(input, Parameter.CALORIE_SIGNALLER);
        if (calories == -1) {
            throw new EmptyCalorieException();
        } else if (calories == -2) {
            throw new BadCalorieException();
        }
        return calories;
    }
}