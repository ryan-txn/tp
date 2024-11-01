package seedu.healthmate.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Utility class for handling LocalDate and LocalDateTime operations.
 */
public final class DateTimeUtils {

    // Private constructor to prevent instantiation
    private DateTimeUtils() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Gets the current date.
     *
     * @return the current LocalDate
     */
    public static LocalDate currentDate() {
        return LocalDate.now();
    }

    /**
     * Gets the LocalDateTime representing the end of the given day.
     * The end of day is defined as 23:59:59.999999999.
     *
     * @param date the LocalDate for which the end of day is needed
     * @return the LocalDateTime at the end of the given day
     * @throws IllegalArgumentException if the date is null
     */
    public static LocalDateTime endOfDayLocalDateTime(LocalDate date) {
        assert date != null : "Date cannot be null";
        return date.atTime(LocalTime.MAX);
    }

    /**
     * Gets the LocalDateTime representing the start of the given day.
     * The start of day is defined as 00:00:00.
     *
     * @param date the LocalDate for which the start of day is needed
     * @return the LocalDateTime at the start of the given day
     * @throws IllegalArgumentException if the date is null
     */
    public static LocalDateTime startOfDayLocalDateTime(LocalDate date) {
        assert date != null : "Date cannot be null";
        return date.atStartOfDay();
    }
}

