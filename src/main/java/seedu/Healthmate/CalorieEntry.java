package seedu.Healthmate;

import java.time.LocalDateTime;

class CalorieEntry {
    private final int calories;
    private final LocalDateTime timestamp;

    public CalorieEntry(int calories) {
        this.calories = calories;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Calorie Entry: " + this.calories + " (at: " + this.timestamp + ")";
    }
}
