package seedu.healthmate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import seedu.healthmate.services.UserHistoryTracker;
import seedu.healthmate.core.MealEntry;
import seedu.healthmate.core.User;
import seedu.healthmate.services.ChatParser;
import seedu.healthmate.services.UI;


public class ChatParserTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOutput = System.out;


    @BeforeEach
    public void setOutputStream() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreStream() {
        System.setOut(originalOutput);
    }

    /**
     * Executes the chatParser's run method with simulated Input.
     * Tests full integration with no stubs
     * @param chatParser
     * @param simulatedInput User input for which the behaviour of chatParser.run() is asserted
     * @param expectedOutput Expected output printed to the console that is to be compared with the actual output
     */
    private void compareChatParserOutput(ChatParser chatParser, String simulatedInput, String expectedOutput) {
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        chatParser.run();

        // Normalize both actual and expected outputs by trimming and standardizing line breaks
        String actualOutput = outputStream.toString().trim().replace("\r\n", "\n").replaceAll("\\s+$", "");
        String normalizedExpectedOutput = expectedOutput.trim().replace("\r\n", "\n").replaceAll("\\s+$", "");

        assertEquals(normalizedExpectedOutput, actualOutput);
        chatParser.cleanMealLists();
    }

    /**
     * Mocks a chatParser run with a User stub
     * @param chatParser
     * @param simulatedInput User input for which the behaviour of chatParser.run() is asserted
     * @param expectedOutput Expected output printed to the consule that is to be compared with the actual ouput
     */
    private void compareChatParserOutputWithStub(ChatParser chatParser, User userStub,
                                                 String simulatedInput, String expectedOutput) {
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        chatParser.simulateRunWithStub(userStub);
        assertEquals(expectedOutput,outputStream.toString());
        chatParser.cleanMealLists();
    }


    /**
     * Tests if the ChatParser correctly flags invalid random input
     * Inspired by: https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
     */
    @Test
    public void randomInput_printsError() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "hi\nbye\n";
        String expectedOutput = UI.simulateInitOutput()
            + UI.simulateReply("Use a valid command", "Retry: ")
            + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }

    /**
     * Tests the situation of adding a meal (without portions and dates specified)
     */
    @Test
    void addMealToOptionsWithName_success() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "save meal burger /c300\nmeal menu\nbye\n";
        String expectedOutput = UI.simulateInitOutput()
            + UI.simulateReply("burger with 300 calories", "Added to options: ")
            + chatParser.getMealOptionsStringWithNewMeal("burger with 300 calories")
            + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }

    /**
     * Tests if adding a meal to meal options without a name failes correctly
     */
    @Test void addMealToOptionsNoName_failure() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "save meal /c300\nbye";
        String expectedOuput = UI.simulateInitOutput()
            + UI.simulateReply("Meal options require a name", "Retry: ")
            + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOuput);
    }

    /**
     * Tests if tracking a meal entry with correctly specified calories was successful.
     */
    @Test void trackMealEntryWithCalories_success() {
        ChatParser chatParser = new ChatParser();
        UserHistoryTracker userHistoryTracker = chatParser.getUserHistoryTracker();
        User user = userHistoryTracker.checkForUserData();
        String simulatedInput = "add mealEntry pizza /c300\nbye";
        LocalDate today = LocalDateTime.now().toLocalDate();
        String timeString = "(at: " + today + ")";
        String expectedOutput = UI.simulateInitOutput()
            + UI.simulateReply("pizza with 300 calories " + timeString, "Tracked: ")
            + UI.simulateUsersConsumptionBar(300, today, user)
            + System.lineSeparator()
            + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }

    /**
     * Negative test of a wrong showHistoricCalories command
     */
    @Test void showHistoricCaloriesNoTime_failure() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "show historicCalories \n bye";
        String expectedOutput = UI.simulateInitOutput()
                + UI.simulateReply("Specify the number of days you want to look into the past",
                "Missing input: ")
                + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }

    /**
     * Tests adding 10 "burger /c300" mealEntries over the past 10 days as well as the show historicCalories feature
     * Scenario simulated:
     * A user adds one burger with 300 calories as a new mealEntrie for each of the past 10 days.
     * Afterwards, he wants to see the impact on the consumption bars (over the last 10 days).
     * The test is based on a user stub with the specifics:
     * height: 180, weight: 80.0, isMale: true, age: 20, HealthGoal: "BULKING"
     */
    @Test void addPastMealsAndShowHistory_success() {
        ChatParser chatParser = new ChatParser();
        User user = User.createUserStub();
        MealEntry testMeal = new MealEntry(Optional.of("burger"), 300);

        LocalDate today = LocalDateTime.now().toLocalDate();
        List<LocalDate> pastTenDays = generatePastDates(today, 10);
        String simulatedInput = buildInputHistoricCaloriesTest(pastTenDays, user);
        String expectedOutput = buildExpectedOutputHistoricCaloriesTest(testMeal, user, pastTenDays);

        compareChatParserOutputWithStub(chatParser, user, simulatedInput, expectedOutput);
    }

    /**
     * Helper method generating a List of past days (starting today)
     * @param today today's date
     * @param numberOfDaysIntoPast the number of past days for which we want to generate the dates
     * @return List<LocalDate></LocalDate> A list of past days
     */
    private List<LocalDate> generatePastDates(LocalDate today, int numberOfDaysIntoPast) {
        return IntStream.range(0, numberOfDaysIntoPast).boxed()
            .map(i -> today.minusDays(i))
            .toList();
    }

    /**
     * Helper method to generate the input for the test 'addPastMealsAndShowHistory_success'.
     * @param pastDays the days for which past mealEntries are added
     * @param user the user for whom the mealEntries are entered as consumption
     * @return String the input provided to the system to simulate the usage scenario described above
     */
    private String buildInputHistoricCaloriesTest(List<LocalDate> pastDays, User user) {
        String mealEntryBasePrompt = "add mealEntry burger /c300 /t";
        String addMealEntriesPrompts = pastDays.stream()
                .map(date -> mealEntryBasePrompt + date.toString())
                .reduce("", (total, prompt) -> total + prompt + "\n");
        String historicCaloriesPrompt = "show historicCalories 10";
        String closeAppPrompt = "\nbye";

        return addMealEntriesPrompts + historicCaloriesPrompt + closeAppPrompt;
    }

    /**
     * Helper method to build the expected output of the test 'addPastMealsAndShowHistory_success'
     * @param testMeal the meal for which the behaviour is tested
     * @param user the user for whom the ideal calorie intake is computed
     * @param pastDays the days for which the historic consumption bars are generated
     * @return String the output expected to be returned by the system.
     */
    private String buildExpectedOutputHistoricCaloriesTest(MealEntry testMeal, User user, List<LocalDate> pastDays) {
        String expectedAddPastMealResult = simulateAddingPastMeals(pastDays, user);
        String expectedShowHistoricCalories = simulateHistoricCalories(testMeal, pastDays);

        return UI.simulateInitOutputAddMeal()
                + expectedAddPastMealResult
                + expectedShowHistoricCalories
                + System.lineSeparator()
                + UI.simulateFareWell();
    }

    /**
     * Helper method for the test 'addPastMealsAndShowHistory_success'.
     * Creates the output String of the system when adding the past mealEntries.
     * @param pastDays the days for which the mealEntries are added
     * @param user the user for whom the mealEntries are entered as consumption
     * @return String the output caused by adding the testMeal to each of the pastDays.
     */
    private String simulateAddingPastMeals(List<LocalDate> pastDays, User user) {
        return pastDays.stream()
            .map(date ->
                UI.simulateReply("burger with 300 calories " +
                    "(at: " + date + ")", "Tracked: ")
                    + UI.simulateUsersConsumptionBar(300, date, user))
            .reduce("", (total, oneOutput) -> total + oneOutput + System.lineSeparator());
    }

    /**
     * Helper method for the test 'addPastMealsAndShowHistory_success'
     * @param testMealEntry the test meal that was added to each of the past days specified in the test
     * @param pastDays the past days
     * @return String the expected output of the system when executing the show historicCalories command
     */
    private String simulateHistoricCalories(MealEntry testMealEntry, List<LocalDate> pastDays) {
        User userStub = User.createUserStub();
        double idealCalories = userStub.getTargetCalories();
        int testMealCalories = testMealEntry.getCalories();
        assert (int)idealCalories == 2674 : "Test assumes ideal daily calories of 2674";
        assert pastDays.size() == 10 : "Test expects an input of 10";
        assert testMealCalories == 300 : "Test expects a calorie intake of 300";
        assert testMealEntry.getName().orElse("") == "burger" : "Tests expects a burger as test meal";

        String idealIntakeString = UI.simulateReply("2674", "Ideal Daily Caloric Intake: ");
        String historyBarsString = simulateHistoryConsumptionBars(pastDays, idealCalories, 300);
        String statsString = getStatsString(pastDays.get(9));

        return idealIntakeString + historyBarsString + statsString;
    }

    /**
     * Helper method for the test 'addPastMealsAndShowHistory_success'
     * Generates the consumptions bars in the format of the show historicCalories command.
     * @param pastDays List<LocalDate></LocalDate> days for which the consumptions bars are generated
     * @param idealCalories Double calories the respective user should consume dayls
     * @param testMealCalories Integer calories of the testMeal that is consumed per day
     * @return String the historic consumption bars for a daily consumption of testMealCalories
     */
    private String simulateHistoryConsumptionBars(List<LocalDate> pastDays, double idealCalories, int testMealCalories){
        return pastDays.stream().sorted()
                .map(date -> UI.simulateHistoricConsumptionBar(idealCalories, testMealCalories, date))
                .reduce("", (total, oneBar) -> total + oneBar + System.lineSeparator());
    }

    /**
     * Helper method for the test 'addPastMealsAndShowHistory_success'.
     * @param maxConsumptionDate The day which the system will select as the maximum consumption day
     * @return the Consumption statistics expected for the consumption tested in 'addPastMealsAndShowHistory_success'
     */
    private String getStatsString(LocalDate maxConsumptionDate) {
        return
                "      Stats over past 10 days" + System.lineSeparator() +
                "      Total Calories Consumed: 3000" + System.lineSeparator() +
                "      Total Ideal Calories: 26740" + System.lineSeparator() +
                "      Percentage of Total Ideal Calories : 11.0%" + System.lineSeparator() +
                "      Day With Heaviest Meal: " + maxConsumptionDate + System.lineSeparator() +
                "      Heaviest Meal Consumed: burger with 300 calories (at: "+ maxConsumptionDate + ")" +
                System.lineSeparator() +
                "      Meals Consumption's Percentage of Daily Ideal Calories: 11.0%" + System.lineSeparator() +
                "      _____________________________________________________________________________";
    }

    /**
     * Tests if deleting a meal option by its index is successful.
     */
    @Test
    void deleteMeal_existingMealByIndex_success() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "save meal burger /c300\n"
                + "delete meal 1\n"
                + "meal menu\nbye\n";
        String expectedOutput = UI.simulateInitOutput()
                + UI.simulateReply("burger with 300 calories", "Added to options: ")
                + UI.simulateReply("Deleted option: burger with 300 calories", "")
                + "      _____________________________________________________________________________\n"
                + "      No meal options added yet\n"
                + "      _____________________________________________________________________________\n"
                + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }

    /**
     * Tests if attempting to delete a non-existent meal option by index returns an error.
     */
    @Test
    void deleteMeal_nonExistentIndex_failure() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "delete meal 1\nbye\n";
        String expectedOutput = UI.simulateInitOutput()
                + UI.simulateReply("No Meal Options", "Error: ")
                + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }

    /**
     * Tests if trying to delete a meal option without specifying an index returns an error.
     */
    @Test
    void deleteMeal_noIndex_failure() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "save meal potato /c30\ndelete meal\nbye\n";
        String expectedOutput = UI.simulateInitOutput()
                + UI.simulateReply("potato with 30 calories", "Added to options: ")
                + UI.simulateReply("Meal index needs to be an integer", "Error: ")
                + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }


    private static String simulateConsumptionMessageWithBar(int idealCalories, int consumedCalories) {
        return UI.simulateReply("Ideal Daily Caloric Intake: " + idealCalories, "")
                + UI.simulateString("Current Calories Consumed: " + consumedCalories)
                + UI.buildConsumptionBar("% of Expected Calorie Intake Consumed: ", idealCalories,
                        consumedCalories, LocalDate.now(), true)
                + "\n";
    }

    /**
     * Tests if deleting a meal entry by index in the meal log is successful.
     */
    @Test
    void deleteMealEntry_existingEntryByIndex_success() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "add mealEntry pizza /c300\n"
                + "delete mealEntry 1\nbye\n";
        int idealCalories = 2674;
        String expectedOutput = UI.simulateInitOutput()
                + UI.simulateReply("pizza with 300 calories (at: " + LocalDate.now() + ")", "Tracked: ")
                + simulateConsumptionMessageWithBar(idealCalories, 300)
                + UI.simulateReply("Deleted entry: pizza with 300 calories (at: " + LocalDate.now() + ")", "")
                + simulateConsumptionMessageWithBar(idealCalories, 0)
                + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }

    /**
     * Tests if attempting to delete a non-existent meal entry by index returns an error.
     */
    @Test
    void deleteMealEntry_nonExistentIndex_failure() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "delete mealEntry 1\nbye\n";
        String expectedOutput = UI.simulateInitOutput()
                + UI.simulateReply("No Meal Entries", "Error: ")
                + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }

    /**
     * Tests if trying to delete a meal entry without specifying an index returns an error.
     */
    @Test
    void deleteMealEntry_noIndex_failure() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "add mealEntry potato /c30\ndelete mealEntry\nbye\n";
        int idealCalories = 2674;
        LocalDate today = LocalDate.now();
        String expectedOutput = UI.simulateInitOutput()
                + UI.simulateReply("potato with 30 calories (at: " + today + ")", "Tracked: ")
                + simulateConsumptionMessageWithBar(idealCalories, 30)
                + UI.simulateReply("Meal Entry index needs to be an integer", "Error: ")
                + UI.simulateFareWell();
        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }

    /**
     * Tests the scenario where todayCalories command is executed successfully with meals added.
     */
    @Test
    void todayCalorieProgress_withMeals_success() {
        ChatParser chatParser = new ChatParser();
        chatParser.cleanMealLists();
        String simulatedInput = "add mealEntry pizza /c500\nshow todayCalories\nbye\n";
        int idealCalories = 2674;
        LocalDate today = LocalDate.now();
        String expectedOutput = UI.simulateInitOutput()
                + UI.simulateReply("pizza with 500 calories (at: " + today + ")", "Tracked: ")
                + simulateConsumptionMessageWithBar(idealCalories, 500)
                + simulateConsumptionMessageWithBar(idealCalories, 500)
                + UI.simulateFareWell();

        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }

    /**
     * Tests the scenario where todayCalories command is executed with no meals added.
     */
    @Test
    void todayCalorieProgress_noMeals_success() {
        ChatParser chatParser = new ChatParser();
        String simulatedInput = "show todayCalories\nbye\n";
        int idealCalories = 2674;
        LocalDate today = LocalDate.now();
        String expectedOutput = UI.simulateInitOutput()
                + simulateConsumptionMessageWithBar(idealCalories, 0)
                + UI.simulateFareWell();

        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
    }


}
