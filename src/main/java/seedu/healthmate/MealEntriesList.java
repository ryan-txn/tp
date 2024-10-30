package seedu.healthmate;


import static seedu.healthmate.MealEntry.extractMealEntryFromString;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public void extractAndAppendMeal(String userInput, String command, MealList mealOptions, User user) {
        try {
            int portions = Parameter.getPortions(userInput);
            MealEntry meal = extractMealEntryFromString(userInput, command, mealOptions);
            for (int i =0; i<portions; i++) {
                this.addMeal(meal);
            }
            printDaysConsumptionBar(user, meal.getTimestamp());
        } catch (EmptyCalorieException | BadCalorieException e) {
            UI.printReply("Every meal needs a calorie integer. (e.g. /c120)", "");
        } catch (StringIndexOutOfBoundsException s) {
            UI.printReply("Do not forget to use /c mark the following integer as calories",
                    "Retry: ");
        } catch (MealNotFoundException e) {
            UI.printReply("Please save this meal to the meal menu first, or use /c and /p to include calories and"
                    + " portion sizes", "");
        } catch (BadPortionException e) {
            UI.printReply("Please reformat your portion size properly. (e.g for 2 portions {/p2})",
                    "Retry: ");
        }
    }

    /**
     * Removes a meal from the list of tracked meal consumption
     * Prints out visual feedback to highlight the
     * resulting change interms of today's calorie consumption bar
     * @param userInput The user input causing the this remove process
     * @param command The identified command instance
     * @param user The user's profile
     */
    public void removeMealWithFeedback(String userInput, String command, User user) {
        try {
            int mealNumber = Integer.parseInt(userInput.replaceAll(command, "").strip());
            LocalDateTime mealEntryDate = this.getDateOfMealEntry(mealNumber);
            deleteMeal(mealNumber);
            printDaysConsumptionBar(user, mealEntryDate);
        } catch (NumberFormatException n) {
            UI.printReply("Meal Entry index needs to be an integer", "Error: ");
        } catch (IndexOutOfBoundsException s) {
            UI.printReply("Meal Entry index needs to be within range", "Error: ");
        }
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

    public List<Meal> getMealEntries() {
        return new ArrayList<>(super.mealList);
    }

    /**
     * Computes actual calorie consumption and delegates the construction of the
     * consumption bar to the user instance with the relevant idealCalories consumption data
     * @param user User profile for which the ideal calorie consumption will be compared with the actual consumption
     * @param dateTime
     */
    public void printDaysConsumptionBar(User user, LocalDateTime dateTime) {
        assert user != null : "User cannot be null";
        assert dateTime != null: "Date needs to be specified to print todays consumption bar";
        LocalDateTime todayMidnight = dateTime.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime todayEndOfDay = dateTime.toLocalDate().atTime(LocalTime.MAX);

        MealEntriesList mealsConsumedToday = this.getMealEntriesByDate(todayEndOfDay, todayMidnight);
        int caloriesConsumed = mealsConsumedToday.getTotalCaloriesConsumed();
        user.printTargetCalories();
        UI.printString("Current Calories Consumed: " + caloriesConsumed);
        user.printUsersConsumptionBar("% of Expected Calorie Intake Consumed: ",
                caloriesConsumed,
                dateTime.toLocalDate());
    }


    /**
     * Prints the historic consumption bars for a specified number of days.
     *
     * @param user the User whose consumption history is to be printed
     * @param days the number of days to include in the consumption history
     * @throws IllegalArgumentException if user is null or days is negative
     */
    public void printHistoricConsumptionBars(User user, int days) {
        assert user != null : "User cannot be null";
        assert days >= 0 : "Days cannot be negative";

        LocalDate today = DateTimeUtils.currentDate();
        user.printTargetCalories();

        int idealCalories = (int) user.getIdealCalories();
        int totalIdealCalories = 0;
        int totalCaloriesConsumed = 0;
        int maxCaloriesConsumed = 0;
        LocalDate maxCaloriesDate = today;

        for (int i = days - 1; i >= 0; i--) {
            LocalDate printDate = today.minusDays(i);
            LocalDateTime upperDateBound = DateTimeUtils.endOfDayLocalDateTime(printDate);
            LocalDateTime lowerDateBound = DateTimeUtils.startOfDayLocalDateTime(printDate);

            MealEntriesList mealsConsumed = this.getMealEntriesByDate(upperDateBound, lowerDateBound);
            int caloriesConsumed = mealsConsumed.getTotalCaloriesConsumed();
            user.printHistoricConsumptionBar(caloriesConsumed, printDate);

            totalCaloriesConsumed += caloriesConsumed;
            totalIdealCalories += idealCalories;

            if (maxCaloriesConsumed < caloriesConsumed) {
                maxCaloriesDate = printDate;
                maxCaloriesConsumed = caloriesConsumed;
            }
        }

        UI.printString("Stats over past " + days + " days");

        UI.printString("Total Calories Consumed: " + totalCaloriesConsumed);
        UI.printString("Total Ideal Calories: " + totalIdealCalories);

        UI.printString("Percent Target Consumed: "
                + Math.round(100.0 * (float)totalCaloriesConsumed / (float)totalIdealCalories) + "%");

        UI.printString( "Day with Max Calories Consumed: " + maxCaloriesDate);

        UI.printString("Calories Consumed: " + maxCaloriesConsumed);

        UI.printString("Percent Target Consumed: "
                + Math.round(100.0 * (float)maxCaloriesConsumed / (float)idealCalories) + "%");

        UI.printSeparator();
    }

    private MealEntriesList getMealEntriesByDate(LocalDateTime upperDateBound, LocalDateTime lowerDateBound) {
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

    private LocalDateTime getDateOfMealEntry(int mealNumber) {
        MealEntry mealEntry = (MealEntry) this.mealList.get(mealNumber - 1);
        return mealEntry.getTimestamp();
    }

}
