package seedu.healthmate.core;


import static seedu.healthmate.core.MealEntry.extractMealEntryFromString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.healthmate.services.ConsumptionStatistics;
import seedu.healthmate.services.UI;
import seedu.healthmate.exceptions.BadCalorieException;
import seedu.healthmate.exceptions.BadPortionException;
import seedu.healthmate.exceptions.BadTimestampException;
import seedu.healthmate.exceptions.EmptyCalorieException;
import seedu.healthmate.exceptions.MealNotFoundException;
import seedu.healthmate.utils.DateTimeUtils;
import seedu.healthmate.utils.Parameter;


public class MealEntriesList extends MealList {

    public MealEntriesList() {
        super();
    }

    public MealEntriesList(ArrayList<Meal> mealList) {
        super(mealList);
    }

    /**
     * Creates a new mealEntry from user input and appends it to the list of mealEntries
     * @param userInput the original user input
     * @param command the command that triggered this action
     * @param mealOptions the list of available presaved meals
     * @param user the user's profile
     */
    @Override
    public void extractAndAppendMeal(String userInput, String command, MealList mealOptions, User user) {
        try {
            if (userInput.contains(",")) {
                UI.printReply("No Commas Allowed", "Retry: ");
                return;
            }
            int portions = Parameter.getPortions(userInput);
            MealEntry meal = extractMealEntryFromString(userInput, command, mealOptions);
            if (!meal.descriptionWithinMaxLength()) {
                UI.printReply(
                        "Keep description to less than " + Meal.MAX_DESCRIPTION_LENGTH + " characters",
                        "Retry: ");
                return;
            }
            addPortionsOfMeal(meal, portions);
            printDaysConsumptionBar(user, meal.getTimestamp());
        } catch (EmptyCalorieException | BadCalorieException e) {
            List<String> messages = List.of("Every meal needs a calorie integer (e.g. /c120).",
                    "Note: The integer has to be within the range of 0 and 2147483647");
            UI.printMultiLineReply(messages);
        } catch (StringIndexOutOfBoundsException s) {
            UI.printReply("Do not forget to use /c{Integer} mark the following integer as calories",
                    "Retry: ");
        } catch (MealNotFoundException e) {
            List<String> messages = List.of("Please save this meal to the meal menu first,",
                    "or use /c and /p to include calories and portion sizes");
            UI.printMultiLineReply(messages);
        } catch (BadPortionException e) {
            List<String> messages = List.of("Retry: Please use a rightly formatted nonzero integer " +
                            "to specify portion size.", "E.g for 2 portions {/p2}).");
            UI.printMultiLineReply(messages);
        } catch (BadTimestampException e) {
            UI.printReply("Please include a timestamp for your meal (e.g for 2024-10-30 {/t2024-10-30}).",
                    "Retry: ");
        }
    }

    /**
     * Removes a meal from the list of tracked meal consumption
     * Prints out visual feedback to highlight the
     * resulting change interms of today's calorie consumption bar
     * @param userInput The user input causing the this remove process
     * @param command The identified command
     * @param user The user's profile
     */
    public void extractAndRemoveMeal(String userInput, String command, User user) {
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

    /**
     * Given portions `p`, adds the mealEntry p times to the list of mealEntries
     * @param mealEntry the meal to be added to the {@code MealEntriesList}
     * @param portion the portions consumed of the {@code mealEntry}
     */
    public void addPortionsOfMeal(Meal mealEntry, int portion) {
        IntStream.range(0, Math.max(1, portion))
                .forEach(i -> this.addMeal(mealEntry));
    }

    /**
     * Adds a mealEntry to the mealEntriesList
     * @param mealEntry the mealEntry to be added to the {@code MealEntriesList}
     */
    @Override
    public void addMeal(Meal mealEntry) {
        super.mealList.add(mealEntry);
        UI.printReply(mealEntry.toString(), "Tracked: ");
    }

    /**
     * Deletes a mealEntry by its index in the log meals overview
     * @param mealNumber Index of the meal to be deleted
     */
    //@@author DarkDragoon2002
    @Override
    public void deleteMeal(int mealNumber) {
        Meal mealToDelete = this.mealList.get(mealNumber - 1);
        super.mealList.remove(mealNumber - 1);
        UI.printReply(mealToDelete.toString(), "Deleted entry: ");
    }
    //@@author

    public List<Meal> getMealEntries() {
        return new ArrayList<>(super.mealList);
    }

    /**
     * Computes actual calorie consumption and delegates the construction and actual printing of the
     * consumption bar to the user instance which forwards it to the UI class
     * @param user User profile for which the ideal calorie consumption
     *                  will be compared with the actual consumption
     * @param dateTime The date for which actual consumption is calculated and compared to the target.
     */
    public void printDaysConsumptionBar(User user, LocalDateTime dateTime) {
        assert user != null : "User cannot be null";
        assert dateTime != null: "Date needs to be specified to print todays consumption bar";

        LocalDate date = dateTime.toLocalDate();
        LocalDateTime todayStartOfDay = DateTimeUtils.startOfDayLocalDateTime(date);
        LocalDateTime todayEndOfDay = DateTimeUtils.endOfDayLocalDateTime(date);

        MealEntriesList mealsConsumedToday = this.getMealEntriesByDate(todayStartOfDay, todayEndOfDay);
        int caloriesConsumed = mealsConsumedToday.getTotalCaloriesConsumed();
        Integer targetCalories = user.getTargetCalories();
        boolean useSpecialChars = user.isAbleToSeeSpecialChars();

        UI.printReply(targetCalories.toString(), "Ideal Daily Caloric Intake: ");
        UI.printString("Current Calories Consumed: " + caloriesConsumed);
        UI.printConsumptionBar("% of Expected Calorie Intake Consumed: ",
                targetCalories,
                caloriesConsumed,
                date,
                useSpecialChars);
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

        Integer targetCalories = user.getTargetCalories();
        UI.printReply(targetCalories.toString(), "Ideal Daily Caloric Intake: ");
        ConsumptionStatistics consumptionStats = ConsumptionStatistics.computeStats(user, days, this);
        this.printHistoricBarPerDay(days, user);
        consumptionStats.printStats(days);
    }

    /**
     * Computes the total calories consumed from all meals in the list.
     * @return The sum of calories for all meals in {@code MealEntriesList}.
     */
    public int getTotalCaloriesConsumed() {
        return this.mealList.stream()
                .map(meal -> meal.getCalories())
                .reduce(0, (accumulator, calorie) -> accumulator + calorie);
    }

    /**
     * Retrieves the meal entry with the maximum calories in {@code MealEntriesList}.
     * @return An {@code Optional} containing the {@code MealEntry} with the highest calorie count,
     *         or an empty {@code Optional} if {@code mealList} is empty.
     */
    public Optional<MealEntry> getMaxCaloriesConsumed() {
        return this.mealList.stream()
                .map(meal -> (MealEntry) meal)
                .reduce((meal1, meal2) -> meal1.getCalories() > meal2.getCalories() ? meal1 : meal2);
    }

    /**
     * Collects a list of meal entries within a specified date range into a new MealEntriesList.
     * @param lowerDateBound The inclusive lower bound of the date range.
     * @param upperDateBound The inclusive upper bound of the date range.
     * @return A {@code MealEntriesList} containing meals that fall within the specified date range.
     */
    public MealEntriesList getMealEntriesByDate(LocalDateTime lowerDateBound, LocalDateTime upperDateBound) {
        ArrayList<Meal> filteredMeals = super.mealList.stream()
                .filter(meal -> meal.isBeforeEqualDate(upperDateBound))
                .filter(meal -> meal.isAfterEqualDate(lowerDateBound))
                .collect(Collectors.toCollection(() -> new ArrayList<Meal>()));
        return new MealEntriesList(filteredMeals);
    }

    /**
     * Returns the number of mealEntries tracked in this MealEntriesList
     * @return Integer the size of the List of meals stored in this instance
     */
    @Override
    public int size() {
        return super.size();
    }

    //@@author DarkDragoon2002
    /**
     * Iterates daily over this list of mealEntries and prints daily consumption bar
     * @param days number of days to go back in time
     * @param user user profile for which the progress bar is built
     */
    private void printHistoricBarPerDay(int days, User user) {
        LocalDate today = DateTimeUtils.currentDate();

        for (int i = days - 1; i >= 0; i--) {
            LocalDate printDate = today.minusDays(i);
            LocalDateTime upperDateBound = DateTimeUtils.endOfDayLocalDateTime(printDate);
            LocalDateTime lowerDateBound = DateTimeUtils.startOfDayLocalDateTime(printDate);

            MealEntriesList mealsConsumed = this.getMealEntriesByDate(lowerDateBound, upperDateBound);
            int caloriesConsumed = mealsConsumed.getTotalCaloriesConsumed();
            int targetCalories = user.getTargetCalories();
            boolean useSpecialChars = user.isAbleToSeeSpecialChars();

            UI.printHistoricConsumptionBar(targetCalories, caloriesConsumed, printDate, useSpecialChars);

        }
    }
    //@@author

    private LocalDateTime getDateOfMealEntry(int mealNumber) {
        MealEntry mealEntry = (MealEntry) this.mealList.get(mealNumber - 1);
        return mealEntry.getTimestamp();
    }

}
