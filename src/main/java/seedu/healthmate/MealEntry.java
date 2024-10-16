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

    public static MealEntry extractMealEntryFromString(String input,
                                             String command,
                                             String calorieSignaller,
                                             MealList mealOptions) throws EmptyCalorieException {
        Optional<String> mealDescription = extractMealDescription(input, command, calorieSignaller);
        int calories;
        try {
            String caloriesString = extractCalories(input, calorieSignaller);
            assert caloriesString.length() > 0: "caloriesString cannot be empty";
            calories = Integer.parseInt(caloriesString);
        } catch (Exception e) {
            System.out.println("Getting info from meal options...");
            Optional<Integer> optionalCalories = mealOptions.getCaloriesByMealName(mealDescription.orElse(""));
            calories = optionalCalories.orElseThrow(() -> new EmptyCalorieException());
        }
        MealEntry mealEntry = new MealEntry(mealDescription, calories);
        return mealEntry;
    }

    @Override
    public String toSaveString() {
        return super.toSaveString() + ", " + this.timestamp;
    }


    @Override
    public String toString() {
        return super.toString() + " (at: " + this.timestamp.truncatedTo(ChronoUnit.HOURS) + ")";
    }
}
