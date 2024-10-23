package seedu.healthmate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtils {

    public static LocalDate currentDate() {
        return LocalDate.now();
    }

    public static LocalDateTime endOfDayLocalDateTime(LocalDate date) {
        return date.atTime(LocalTime.MAX);
    }

    public static LocalDateTime startOfDayLocalDateTime(LocalDate date) {
        return date.atStartOfDay();
    }
}
