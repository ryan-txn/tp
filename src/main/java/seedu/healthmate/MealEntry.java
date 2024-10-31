package seedu.healthmate;

import java.time.LocalDateTime;
import java.util.Optional;
import java.time.temporal.ChronoUnit;


public class MealEntry extends Meal{
    private final LocalDateTime timestamp;

    public MealEntry(Optional<String> name, int calories) {
        super(name, calories);
        this.timestamp = LocalDateTime.now();
    }

    public MealEntry(Optional<String> name, int calories, LocalDateTime timestamp) {
        super(name, calories);
        this.timestamp = timestamp;
    }

    public static MealEntry extractMealEntryFromString(String input, String command, MealList mealOptions)
            throws EmptyCalorieException, BadCalorieException, MealNotFoundException, BadTimestampException {

        int calories;
        Optional<String> mealDescription = extractMealDescription(input, command);

        try {
            calories = Parameter.getCalories(input);
        } catch (EmptyCalorieException e) {
            System.out.println("Getting info from meal options...");
            Optional<Integer> optionalCalories = mealOptions.getCaloriesByMealName(mealDescription.orElse(""));
            if (!optionalCalories.isPresent() && !(mealDescription.orElse("").equals(""))) {
                UI.printMealNotFound();
                throw new MealNotFoundException();
            }
            calories = optionalCalories.orElseThrow(() -> new EmptyCalorieException());
        }

        try {
            LocalDateTime timestamp = Parameter.getTimestamp(input);
            return new MealEntry(mealDescription, calories, timestamp);
        } catch (EmptyTimestampException e) {
            return new MealEntry(mealDescription, calories);
        } catch (BadTimestampException e) {
            throw new BadTimestampException();
        }
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }


    @Override
    public String toSaveString() {
        return super.toSaveString() + ", " + this.timestamp;
    }

    @Override
    public boolean isBeforeEqualDate(LocalDateTime timestamp) {
        return this.timestamp.isBefore(timestamp) || this.timestamp.isEqual(timestamp);
    }

    @Override
    public boolean isAfterEqualDate(LocalDateTime timestamp) {
        return this.timestamp.isAfter(timestamp) || this.timestamp.isEqual(timestamp);
    }

    @Override
    public String toString() {
        return super.toString() + " (at: " + this.timestamp.truncatedTo(ChronoUnit.HOURS) + ")";
    }
}
