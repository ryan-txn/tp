package seedu.healthmate;

import static seedu.healthmate.ChatParser.CALORIE_SIGNALLER;
import static seedu.healthmate.MealEntry.extractMealEntryFromString;

import java.util.ArrayList;
import java.util.List;

public class MealEntriesList extends MealList {

    public MealEntriesList() {
        super();
    }

    @Override
    public void addMeal(Meal mealEntry) {
        super.mealList.add(mealEntry);
        UI.printReply(mealEntry.toString(), "Tracked: ");
    }

    @Override
    public void deleteMeal(int mealNumber) {
        Meal mealToDelete = this.mealList.get(mealNumber - 1);
        super.mealList.remove(mealNumber - 1);
        UI.printReply(mealToDelete.toString(), "Deleted entry: ");
    }

    @Override
    public void appendMealFromString(String userInput, String command) {
        try {
            MealEntry meal = extractMealEntryFromString(userInput, command, CALORIE_SIGNALLER);
            this.addMeal(meal);
        } catch (EmptyCalorieException e) {
            UI.printReply("Every meal needs a calorie integer. (e.g. 120)", "");
        } catch (StringIndexOutOfBoundsException s) {
            UI.printReply("Do not forget to use /c mark the following integer as calories",
                    "Retry: ");
        } catch (NumberFormatException n) {
            UI.printReply("A calorie entry needs to be an integer", "Error: ");
        }
    }

    @Override
    public void removeMealFromString(String userInput, String command) {
        try {
            int mealNumber = Integer.parseInt(userInput.replaceAll(command, "").strip());
            deleteMeal(mealNumber);
        } catch (NumberFormatException n) {
            UI.printReply("Meal Entry index needs to be an integer", "Error: ");
        } catch (IndexOutOfBoundsException s) {
            UI.printReply("Meal Entry index needs to be within range", "Error: ");
        }
    }

    public List<Meal> getMealEntries() {
        return new ArrayList<>(mealList);
    }

}
