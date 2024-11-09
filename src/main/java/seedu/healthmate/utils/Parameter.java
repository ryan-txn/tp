package seedu.healthmate.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.healthmate.exceptions.BadCalorieException;
import seedu.healthmate.exceptions.BadPortionException;
import seedu.healthmate.exceptions.BadTimestampException;
import seedu.healthmate.exceptions.EmptyCalorieException;
import seedu.healthmate.exceptions.EmptyTimestampException;

public enum Parameter {
    EMPTY_SIGNALLER("/"),
    CALORIE_SIGNALLER("/c"),
    PORTIONS_SIGNALLER("/p"),
    TIMESTAMP_SIGNALLER("/t");
    private static int maxCalories = 100000;
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
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return -2;
            }
        } else {
            // Contains param but bad format response is -2
            // If param is missing return 1 for portions for default and -1 for Calories
            return containsParam? -2: param == PORTIONS_SIGNALLER?1:-1;

        }
    }
    /**
     * Extracts the number of portions from the input string.
     * @param input The input string containing portion information
     * @return The number of portions specified, or 1 if not specified
     * @throws BadPortionException if the portion format is invalid
     */
    public static int getPortions(String input) throws BadPortionException {
        int portions = parseParameter(input, Parameter.PORTIONS_SIGNALLER);
        if (portions <= 0) {
            throw new BadPortionException();
        }
        return parseParameter(input, Parameter.PORTIONS_SIGNALLER);
    }

    /**
     * Extracts the calorie count from the input string.
     * @param input The input string containing calorie information
     * @return The number of calories specified
     * @throws BadCalorieException if the calorie format is invalid
     * @throws EmptyCalorieException if no calorie value is specified
     */
    public static int getCalories(String input) throws BadCalorieException, EmptyCalorieException {
        int calories = parseParameter(input, Parameter.CALORIE_SIGNALLER);
        if (calories == -1) {
            throw new EmptyCalorieException();
        } else if (calories == -2) {
            throw new BadCalorieException();
        }
        return calories;
    }

    /**
     * Extracts and parses the timestamp from the input string.
     * @param input The input string containing timestamp information in yyyy-MM-dd format
     * @return The parsed LocalDate object
     * @throws EmptyTimestampException if no timestamp is specified
     * @throws BadTimestampException if the timestamp format is invalid
     */
    public static LocalDate getTimestamp(String input) throws EmptyTimestampException, BadTimestampException {
        String regex = TIMESTAMP_SIGNALLER.getPrefix() + "\\d{4}-\\d{2}-\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        boolean containsTimestamp = input.contains(TIMESTAMP_SIGNALLER.getPrefix());
        
        if (!containsTimestamp) {
            throw new EmptyTimestampException();
        }
        
        if (matcher.find()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(matcher.group(0).replace(TIMESTAMP_SIGNALLER.getPrefix(), ""), formatter);
            } catch (DateTimeParseException e) {
                throw new BadTimestampException();
            }
        } else {
            throw new BadTimestampException();
        }
    }
}
