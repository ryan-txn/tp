package seedu.healthmate;

import java.io.File;


public class Storage {
    private static final String userDir = System.getProperty("user.dir");
    private static final String USER_FILE_PATH = userDir + "/user.txt";
    private static final String MEAL_OPTIONS_FILE_PATH = userDir + "/mealOptions.txt";
    private static final String MEAL_ENTRIES_FILE_PATH = userDir + "/mealEntries.txt";
    private final MealList mealOptions;
    private final File userFile;
    private final File mealOptionsFile;
    private final File mealEntriesFile;

    public Storage(MealList mealOptions) {
        this.mealOptions = mealOptions;
        this.userFile = new File(USER_FILE_PATH);
        this.mealOptionsFile = new File(MEAL_OPTIONS_FILE_PATH);
        this.mealEntriesFile = new File(MEAL_ENTRIES_FILE_PATH);
    }

}
