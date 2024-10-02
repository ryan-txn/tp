package seedu.healthmate;

import java.time.LocalDateTime;

class HeightEntry {
    private double height;
    private final LocalDateTime timestamp;

    public HeightEntry(double height) {
        this.height = height;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "weight:" + this.height;
    }
}
