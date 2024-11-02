package seedu.healthmate.core;
import static seedu.healthmate.core.Meal.extractMealFromString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.healthmate.services.UI;
import seedu.healthmate.exceptions.BadCalorieException;
import seedu.healthmate.exceptions.EmptyCalorieException;

public class MealList {

    protected ArrayList<Meal> mealList;

    public MealList() {
        this.mealList = new ArrayList<Meal>();
    }

    public MealList(ArrayList<Meal> mealList) {
        this.mealList = mealList;
    }

    public void extractAndAppendMeal(String userInput, String command, MealList mealOptions, User user) {
        try {
            Meal meal = extractMealFromString(userInput, command);
            if (!meal.descriptionIsEmpty()) {
                this.addMeal(meal);
            } else {
                UI.printReply("Meal options require a name", "Retry: ");
            }
        } catch (EmptyCalorieException | BadCalorieException e) {
            UI.printReply("Every meal needs a calorie integer. (e.g. 120)", "");
        } catch (StringIndexOutOfBoundsException s) {
            UI.printReply("Do not forget to use /c mark the following integer as calories",
                    "Retry: ");
        }
    }

    public void extractAndRemoveMeal(String userInput, String command) {
        try {
            int mealNumber = Integer.parseInt(userInput.replaceAll(command, "").strip());
            deleteMeal(mealNumber);
        } catch (NumberFormatException n) {
            UI.printReply("Meal index needs to be an integer", "Error: ");
        } catch (IndexOutOfBoundsException s) {
            UI.printReply("Meal index needs to be within range", "Error: ");
        }
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

    public String toMealStringByIndex(int mealIndex) {
        return this.mealList.get(mealIndex).toString();
    }

    public int size() {
        return this.mealList.size();
    }

    public void updateMeal(Meal newMeal) {
        for (int i = 0; i < mealList.size(); i++) {
            if (mealList.get(i).getName().equals(newMeal.getName())) {
                mealList.remove(i);
                mealList.add(i, newMeal);
                break;
            }
        }
    }

}
