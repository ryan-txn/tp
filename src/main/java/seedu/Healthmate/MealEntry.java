package seedu.Healthmate;

import java.time.LocalDateTime;
import java.util.Optional;

public class MealEntry extends Meal{
    private final LocalDateTime timestamp;

    public MealEntry(Optional<String> name, int calories) {
        super(name, calories);
        this.timestamp = LocalDateTime.now();
    }

    public static MealEntry extractMealEntryFromString(String input,
                                             String command,
                                             String calorieSignaller) throws EmptyCalorieException {
        Optional<String> mealDescription = extractMealDescription(input, command, calorieSignaller);
        String caloriesString = extractCalories(input, calorieSignaller);
        int calories = Integer.parseInt(caloriesString);
        MealEntry mealEntry = new MealEntry(mealDescription, calories);
        return mealEntry;
    }


    @Override
    public String toString() {
        return super.toString() + " (at: " + this.timestamp + ")";
    }
}
