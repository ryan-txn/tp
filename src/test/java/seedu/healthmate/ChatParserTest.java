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
     * @param expectedOutput Expected output printed to the consule that is to be compared with the actual ouput
     */
    private void compareChatParserOutput(ChatParser chatParser, String simulatedInput, String expectedOutput) {
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        chatParser.run();
        assertEquals(outputStream.toString(), expectedOutput);
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
        assertEquals(outputStream.toString(), expectedOutput);
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
            + UI.simulateReply("Invalid command", "Retry: ")
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
        User user = User.checkForUserData(chatParser.getHistoryTracker());
        String simulatedInput = "add mealEntry pizza /c300\nbye";
        LocalDate today = LocalDateTime.now().toLocalDate();
        String timeString = "(at: " + today + ")";
        String expectedOutput = UI.simulateInitOutput()
            + UI.simulateReply("pizza with 300 calories " + timeString, "Tracked: ")
            + user.simulateUsersConsumptionBar(300, today)
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

        compareChatParserOutput(chatParser, simulatedInput, expectedOutput);
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

        return UI.simulateInitOutput()
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
                    + user.simulateUsersConsumptionBar(300, date))
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
        double idealCalories = userStub.getIdealCalories();
        int testMealCalories = testMealEntry.getCalories();
        assert (int)idealCalories == 2865 : "Test assumes ideal daily calories of 2865";
        assert pastDays.size() == 10 : "Test expects an input of 10";
        assert testMealCalories == 300 : "Test expects a calorie intake of 300";
        assert testMealEntry.getName().orElse("") == "burger" : "Tests expects a burger as test meal";

        String idealIntakeString = UI.simulateReply("2865", "Ideal Daily Caloric Intake: ");
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
                "      Total Ideal Calories: 28650" + System.lineSeparator() +
                "      Percentage of Total Ideal Calories : 10.0%" + System.lineSeparator() +
                "      Day With Heaviest Meal: 2024-10-24" + System.lineSeparator() +
                "      Heaviest Meal Consumed: burger with 300 calories (at: "+ maxConsumptionDate + ")" +
                System.lineSeparator() +
                "      Meals Consumption's Percentage of Daily Ideal Calories: 10.0%" + System.lineSeparator() +
                "      _____________________________________________________________________________";
    }
}
