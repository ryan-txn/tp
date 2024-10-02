package seedu.healthmate;

public class HealthMate {
    private static MealList mealOptions = new MealList();
    private static MealEntriesList mealEntries = new MealEntriesList();
    private static HealthGoal healthGoal;
    private static ChatParser chatParser = new ChatParser(mealEntries, mealOptions);

    public static void main(String[] args) {
        UI.printGreeting();
        chatParser.run();

    }
}


