package seedu.healthmate;
import static seedu.healthmate.ChatParser.CALORIE_SIGNALLER;
import static seedu.healthmate.Meal.extractMealFromString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MealList {

    protected ArrayList<Meal> mealList;

    public MealList() {
        this.mealList = new ArrayList<Meal>();
    }

    public void addMealWithoutCLIMessage(Meal meal) {
        this.mealList.add(meal);
    }

    public void addMeal(Meal meal) {
        this.mealList.add(meal);
        UI.printReply(meal.toString(), "Added to options: ");
    }

    public void deleteMeal(int mealNumber) {
        Meal mealToDelete = this.mealList.get(mealNumber - 1);
        this.mealList.remove(mealNumber - 1);
        UI.printReply(mealToDelete.toString(), "Deleted option: ");
    }

    public int size() {
        return this.mealList.size();
    }

    public void appendMealFromString(String userInput, String command, MealList mealOptions) {
        try {
            assert userInput.split(" ").length == 1: "meals can only have one word as a name";
            Meal meal = extractMealFromString(userInput, command, CALORIE_SIGNALLER);
            if (!meal.descriptionIsEmpty()) {
                this.addMeal(meal);
            } else {
                UI.printReply("Meal options require a name", "Retry: ");
            }
        } catch (EmptyCalorieException e) {
            UI.printReply("Every meal needs a calorie integer. (e.g. 120)", "");
        } catch (StringIndexOutOfBoundsException s) {
            UI.printReply("Do not forget to use /c mark the following integer as calories",
                    "Retry: ");
        } catch (NumberFormatException n) {
            UI.printReply("A calorie entry needs to be an integer", "Error: ");
        }
    }

    public void removeMealFromString(String userInput, String command) {
        try {
            assert userInput.split(" ").length == 1;
            int mealNumber = Integer.parseInt(userInput.replaceAll(command, "").strip());
            deleteMeal(mealNumber);
        } catch (NumberFormatException n) {
            UI.printReply("Meal index needs to be an integer", "Error: ");
        } catch (IndexOutOfBoundsException s) {
            UI.printReply("Meal index needs to be within range", "Error: ");
        }
    }

    public String toMealString(int mealIndex) {
        return this.mealList.get(mealIndex).toString();
    }

    public List<Meal> getMealList() {
        return new ArrayList<>(mealList);
    }

    public Optional<Integer> getCaloriesByMealName(String mealName) {
        for (Meal meal : mealList) {
            if (meal.getName().isPresent() && meal.getName().get().equalsIgnoreCase(mealName)) {
                return Optional.of(meal.getCalories());
            }
        }
        return Optional.empty();
    }
}
