package seedu.healthmate.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import seedu.healthmate.services.UI;
import seedu.healthmate.exceptions.BadCalorieException;
import seedu.healthmate.exceptions.BadTimestampException;
import seedu.healthmate.exceptions.EmptyCalorieException;
import seedu.healthmate.exceptions.EmptyTimestampException;
import seedu.healthmate.exceptions.MealNotFoundException;
import seedu.healthmate.utils.DateTimeUtils;
import seedu.healthmate.utils.Parameter;

/**
 * Represents a meal entry in the HealthMate application.
 * A meal entry extends the Meal class and includes timestamp information.
 */
public class MealEntry extends Meal{
    private final LocalDateTime timestamp;

    /**
     * Constructs a MealEntry with the current timestamp.
     *
     * @param name The name/description of the meal
     * @param calories The caloric content of the meal
     */
    public MealEntry(Optional<String> name, int calories) {
        super(name, calories);
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructs a MealEntry with a specified timestamp.
     *
     * @param name The name/description of the meal
     * @param calories The caloric content of the meal
     * @param timestamp The timestamp of when the meal was consumed
     */
    public MealEntry(Optional<String> name, int calories, LocalDateTime timestamp) {
        super(name, calories);
        this.timestamp = timestamp;
    }

    /**
     * Extracts a MealEntry object from a string input.
     *
     * @param input The input string to parse
     * @param command The command associated with the input
     * @param mealOptions List of predefined meal options
     * @return A new MealEntry object based on the input
     * @throws EmptyCalorieException If calories information is missing
     * @throws BadCalorieException If calories value is invalid
     * @throws MealNotFoundException If referenced meal is not found in options
     * @throws BadTimestampException If timestamp format is invalid
     */
    public static MealEntry extractMealEntryFromString(String input, String command, MealList mealOptions)
            throws EmptyCalorieException, BadCalorieException, MealNotFoundException, BadTimestampException {

        int calories;
        Optional<String> mealDescription = extractMealDescription(input, command);

        try {
            calories = Parameter.getCalories(input);
        } catch (EmptyCalorieException e) {
            UI.printSeparator();
            UI.printString("Getting info from meal options...");
            Optional<Integer> optionalCalories = mealOptions.getCaloriesByMealName(mealDescription.orElse(""));
            if (!optionalCalories.isPresent() && !(mealDescription.orElse("").equals(""))) {
                UI.printMealNotFound();
                throw new MealNotFoundException();
            }
            calories = optionalCalories.orElseThrow(() -> new EmptyCalorieException());
        }

        try {
            LocalDate timestamp = Parameter.getTimestamp(input);
            if (timestamp.isAfter(DateTimeUtils.currentDate())) {
                UI.printString("DATE ERROR: NO FUTURE DATES");
                throw new BadTimestampException();
            }
            return new MealEntry(mealDescription, calories, timestamp.atStartOfDay());
        } catch (EmptyTimestampException e) {
            return new MealEntry(mealDescription, calories);
        } catch (BadTimestampException e) {
            throw new BadTimestampException();
        }
    }

    /**
     * Gets the timestamp of the meal entry.
     *
     * @return The timestamp when the meal was consumed
     */
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Converts the meal entry to a string format for saving.
     *
     * @return String representation of the meal entry for storage
     */
    @Override
    public String toSaveString() {
        return super.toSaveString() + ", " + this.timestamp;
    }

    /**
     * Checks if the meal entry's timestamp is before or equal to the given timestamp.
     *
     * @param timestamp The timestamp to compare against
     * @return true if this entry's timestamp is before or equal to the given timestamp
     */
    @Override
    public boolean isBeforeEqualDate(LocalDateTime timestamp) {
        return this.timestamp.isBefore(timestamp) || this.timestamp.isEqual(timestamp);
    }

    /**
     * Checks if the meal entry's timestamp is after or equal to the given timestamp.
     *
     * @param timestamp The timestamp to compare against
     * @return true if this entry's timestamp is after or equal to the given timestamp
     */
    @Override
    public boolean isAfterEqualDate(LocalDateTime timestamp) {
        return this.timestamp.isAfter(timestamp) || this.timestamp.isEqual(timestamp);
    }

    /**
     * Returns a string representation of the meal entry.
     *
     * @return String representation of the meal entry including timestamp
     */
    @Override
    public String toString() {
        return super.toString() + " (at: " + this.timestamp.toLocalDate() + ")";
    }
}
