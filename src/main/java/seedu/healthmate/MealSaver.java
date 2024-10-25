package seedu.healthmate;


public class MealSaver {
    private HistoryTracker historyTracker;

    public MealSaver(HistoryTracker historyTracker) {
        this.historyTracker = historyTracker;
    }

    public Meal extractMealFromUserInput(String userInput) {
        try {
            String command = "save meal";
            Meal meal = Meal.extractMealFromString(userInput, command);
            if (meal.descriptionIsEmpty()) {
                UI.printReply("Meal options require a name", "Retry: ");
                return null;
            }
            return meal;
        } catch (EmptyCalorieException e) {
            UI.printReply("Every meal needs a calorie integer. (e.g. 120)", "");
        } catch (StringIndexOutOfBoundsException s) {
            UI.printReply("Do not forget to use /c to mark the following integer as calories", "Retry: ");
        } catch (Exception n) {
            UI.printReply("A calorie entry needs to be an integer", "Error: ");
        }
        return null;
    }

    public void saveMeal(Meal meal, MealList mealList) {
        if (DuplicateEntryChecker.isDuplicate(meal.getName(), mealList.getMealList())) {
            System.out.println("Duplicate meal found: " + meal.getName());
            if (shouldOverwrite(meal)) {
                overwriteMeal(meal, mealList);
            }
        } else {
            mealList.addMeal(meal);
        }
        historyTracker.saveMealOptions(mealList);
    }

    private boolean shouldOverwrite(Meal meal) {
        // Logic to determine if the meal should be overwritten
        return true;
    }

    private void overwriteMeal(Meal newMeal, MealList mealList) {
        mealList.updateMeal(newMeal);
    }
}
