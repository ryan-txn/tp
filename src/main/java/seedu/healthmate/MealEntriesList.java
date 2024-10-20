package seedu.healthmate;

import static seedu.healthmate.ChatParser.CALORIE_SIGNALLER;
import static seedu.healthmate.MealEntry.extractMealEntryFromString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MealEntriesList extends MealList {

    public MealEntriesList() {
        super();
    }

    public MealEntriesList(ArrayList<Meal> mealList) {
        super(mealList);
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
    public void extractAndAppendMeal(String userInput, String command, MealList mealOptions, User user) {
        try {

            MealEntry meal = extractMealEntryFromString(userInput, command, CALORIE_SIGNALLER, mealOptions);
            this.addMeal(meal);
            printDaysConsumptionBar(user, LocalDateTime.now());

        } catch (EmptyCalorieException e) {
            UI.printReply("Every meal needs a calorie integer. (e.g. 120)", "");
        } catch (StringIndexOutOfBoundsException s) {
            UI.printReply("Do not forget to use /c mark the following integer as calories",
                    "Retry: ");
        } catch (NumberFormatException n) {
            UI.printReply("A calorie entry needs to be an integer", "Error: ");
        }
    }

    public void removeMealWithFeedback(String userInput, String command, User user) {
        try {
            int mealNumber = Integer.parseInt(userInput.replaceAll(command, "").strip());
            deleteMeal(mealNumber);
            printDaysConsumptionBar(user, LocalDateTime.now());
        } catch (NumberFormatException n) {
            UI.printReply("Meal Entry index needs to be an integer", "Error: ");
        } catch (IndexOutOfBoundsException s) {
            UI.printReply("Meal Entry index needs to be within range", "Error: ");
        }
    }

    private void printDaysConsumptionBar(User user, LocalDateTime endOfDayTime) {
        LocalDateTime todayMidnight = endOfDayTime.truncatedTo(ChronoUnit.DAYS);
        LocalDate today = endOfDayTime.toLocalDate();
        MealEntriesList mealsConsumedToday = this.getMealEntriesByDate(endOfDayTime, todayMidnight);
        int caloriesConsumed = mealsConsumedToday.getTotalCaloriesConsumed();
        user.printUsersConsumptionBar("% of Expected Calorie Intake Consumed: ", caloriesConsumed, today);
    }

    public MealEntriesList getMealEntriesByDate(LocalDateTime upperDateBound, LocalDateTime lowerDateBound) {
        ArrayList<Meal> filteredMeals = super.mealList.stream()
                .filter(meal -> meal.isBeforeEqualDate(upperDateBound))
                .filter(meal -> meal.isAfterEqualDate(lowerDateBound))
                .collect(Collectors.toCollection(() -> new ArrayList<Meal>()));
        return new MealEntriesList(filteredMeals);
    }

    private int getTotalCaloriesConsumed() {
        return this.mealList.stream()
                .map(meal -> meal.getCalories())
                .reduce(0, (accumulator, calorie) -> accumulator + calorie);
    }

    public List<Meal> getMealEntries() {
        return new ArrayList<>(super.mealList);
    }

}
