package seedu.healthmate.core;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.healthmate.exceptions.BadCalorieException;
import seedu.healthmate.exceptions.EmptyCalorieException;
import seedu.healthmate.utils.Parameter;

public class Meal {
    
    public static final int MAX_DESCRIPTION_LENGTH = 50;

    private final Optional<String> name;
    private final int calories;

    public Meal(Optional<String> name, int calories) {
        this.name = name;
        this.calories = calories;
    }

    /**
     * Extracts a Meal object from a string input.
     * @param input The input string containing meal information
     * @param command The command string to parse from
     * @return A new Meal object with the extracted description and calories
     * @throws EmptyCalorieException if no calorie value is specified
     * @throws BadCalorieException if the calorie format is invalid
     */
    public static Meal extractMealFromString(String input,
                                             String command) throws EmptyCalorieException, BadCalorieException {
        Optional<String> mealDescription = extractMealDescription(input, command);
        int calories = Parameter.getCalories(input);
        Meal meal = new Meal(mealDescription, calories);
        return meal;
    }

    /**
     * Extracts the meal description from the input string.
     * @param input The input string containing the meal description
     * @param command The command string to parse from
     * @return An Optional containing the extracted meal description, or empty if none exists
     */
    public static Optional<String> extractMealDescription(String input, String command) {
        int mealDescriptionIndex = input.indexOf(command) + command.length();
        int signallerIndex = input.indexOf(Parameter.EMPTY_SIGNALLER.getPrefix());
        if (signallerIndex == -1) {
            signallerIndex = input.length();
        }
        String mealDescrition = input.substring(mealDescriptionIndex, signallerIndex).trim().toLowerCase();
        if (mealDescrition.strip().length() > 0) {
            return Optional.of(mealDescrition);
        } else {
            return Optional.empty();
        }
    }

    public boolean descriptionIsEmpty() {
        return this.name.orElse("").length() == 0;
    }

    public boolean descriptionWithinMaxLength() {
        return this.name.orElse("").length() <= MAX_DESCRIPTION_LENGTH;
    }

    public boolean isBeforeEqualDate(LocalDateTime timestamp) {
        return false;
    }

    public boolean isAfterEqualDate(LocalDateTime timestamp) {
        return false;
    }

    public String toSaveString() {
        return this.name.orElse("Meal") + "," + this.getCalories();
    }

    public Optional<String> getName() {
        return this.name;
    }

    public int getCalories() {
        return this.calories;
    }

    @Override
    public String toString() {
        return this.name.orElse("Meal") + " with " + this.calories + " calories";
    }
}

