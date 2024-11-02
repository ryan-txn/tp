package seedu.healthmate.utils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import seedu.healthmate.core.Meal;

public class DuplicateEntryChecker {
    
    /**
     * Checks if the given entry is a duplicate in the list of meal options.
     *
     * @param optional The entry to check for duplication.
     * @param mealList The list of meal options to check against.
     * @return true if the entry is a duplicate, false otherwise.
     */
    public static boolean isDuplicate(Optional<String> optional, List<Meal> mealList) {
        for (Meal option : mealList) {
            if (option.getName().equals(optional)) {
                return true;
            }
        }
        return false;
    }

    
    /**
     * Checks if there are any duplicate entries in the given list of meal options.
     *
     * @param mealOptions The list of meal options to check for duplicates.
     * @return true if duplicates are found, false otherwise.
     */
    public static boolean hasDuplicates(List<String> mealOptions) {
        Set<String> uniqueOptions = new HashSet<>();
        for (String option : mealOptions) {
            if (!uniqueOptions.add(option)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds and returns the first duplicate entry in the given list of meal options.
     *
     * @param mealOptions The list of meal options to check for duplicates.
     * @return The first duplicate entry found, or null if no duplicates exist.
     */
    public static String findFirstDuplicate(List<String> mealOptions) {
        Set<String> uniqueOptions = new HashSet<>();
        for (String option : mealOptions) {
            if (!uniqueOptions.add(option)) {
                return option;
            }
        }
        return null;
    }
}
