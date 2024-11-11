package seedu.healthmate.core;
import static seedu.healthmate.core.Meal.extractMealFromString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.healthmate.services.UI;
import seedu.healthmate.exceptions.BadCalorieException;
import seedu.healthmate.exceptions.EmptyCalorieException;

/**
 * Represents a list of meals that can be manipulated.
 * Provides methods to add, remove, and manage meals in the list.
 */
public class MealList {

    protected ArrayList<Meal> mealList;

    /**
     * Constructs an empty MealList.
     */
    public MealList() {
        this.mealList = new ArrayList<Meal>();
    }

    /**
     * Constructs a MealList with an existing list of meals.
     * @param mealList The ArrayList of meals to initialize with
     */
    public MealList(ArrayList<Meal> mealList) {
        this.mealList = mealList;
    }

    /**
     * Extracts meal information from user input and adds it to the list.
     * @param userInput The raw input string from user
     * @param command The command that triggered this action
     * @param mealOptions List of available meal options
     * @param user The current user
     */
    public void extractAndAppendMeal(String userInput, String command, MealList mealOptions, User user) {
        try {
            if (userInput.contains(",")) {
                UI.printReply("Meal entry should not include commas", "Retry: ");
                return;
            }
            Meal meal = extractMealFromString(userInput, command);

            if (meal.descriptionIsEmpty()) {
                UI.printReply("Meal options require a name", "Retry: ");
            } else if (!meal.descriptionWithinMaxLength()) {
                UI.printReply(
                        "Keep description to less than " + Meal.MAX_DESCRIPTION_LENGTH + " characters",
                        "Retry: ");
            } else {
                this.addMeal(meal);
            }
        } catch (EmptyCalorieException | BadCalorieException e) {
            UI.printReply("Every meal needs a calorie integer. (e.g. 120)", "");
        } catch (StringIndexOutOfBoundsException s) {
            UI.printReply("Do not forget to use /c mark the following integer as calories",
                    "Retry: ");
        }
    }

    /**
     * Removes a meal from the list based on user input.
     * @param userInput The raw input string from user
     * @param command The command that triggered this action
     */
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

    /**
     * Adds a meal to the list without displaying CLI messages.
     * @param meal The meal to be added
     */
    public void addMealWithoutCLIMessage(Meal meal) {
        this.mealList.add(meal);
    }

    /**
     * Adds a meal to the list and displays a confirmation message.
     * @param meal The meal to be added
     */
    public void addMeal(Meal meal) {
        this.mealList.add(meal);
        UI.printReply(meal.toString(), "Added to options: ");
    }


    /**
     * Deletes a meal from the list by its index.
     * @param mealNumber The 1-based index of the meal to delete
     */
    //@@author DarkDragoon2002
    public void deleteMeal(int mealNumber) {
        Meal mealToDelete = this.mealList.get(mealNumber - 1);
        this.mealList.remove(mealNumber - 1);
        UI.printReply(mealToDelete.toString(), "Deleted option: ");
    }
    //@@author

    public List<Meal> getMealList() {
        return new ArrayList<>(mealList);
    }

    /**
     * Retrieves the calories for a meal by its name.
     * @param mealName The name of the meal to look up
     * @return Optional containing the calories if found, empty otherwise
     */
    public Optional<Integer> getCaloriesByMealName(String mealName) {
        for (Meal meal : mealList) {
            if (meal.getName().isPresent() && meal.getName().get().equalsIgnoreCase(mealName)) {
                return Optional.of(meal.getCalories());
            }
        }
        return Optional.empty();
    }

    /**
     * Returns the string representation of a meal at the given index.
     * @param mealIndex The index of the meal
     * @return String representation of the meal
     */
    public String toMealStringByIndex(int mealIndex) {
        return this.mealList.get(mealIndex).toString();
    }

    public int size() {
        return this.mealList.size();
    }

    /**
     * Updates an existing meal in the list with new information.
     * @param newMeal The meal containing updated information
     */
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
