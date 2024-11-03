package seedu.healthmate.core;

import seedu.healthmate.utils.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WeightEntryDisplay {
    private static List<Pair<LocalDateTime, Double>> weightEntries = new ArrayList<>();

    public static void display(Optional<UserEntryList> userEntryList) {
        if (userEntryList.isEmpty()) {
            System.out.println("No weight entries available.");
            return;
        }

        ArrayList<User> users = userEntryList.get().getUserEntryList();
        int size = users.size();
        int startIndex = Math.max(0, size - 10);
        weightEntries.clear();

        for (int i = startIndex; i < size; i++) {
            User user = users.get(i);
            weightEntries.add(new Pair<>(user.getLocalDateTime(), user.getWeight()));
        }

        double minWeight = Double.MAX_VALUE;
        double maxWeight = Double.MIN_VALUE;
        for (Pair<LocalDateTime, Double> entry : weightEntries) {
            double weight = entry.u();
            if (weight < minWeight) {
                minWeight = weight;
            }
            if (weight > maxWeight) {
                maxWeight = weight;
            }
        }

        final int graphHeight = 10;

        if (maxWeight == minWeight) {
            System.out.println("All weight entries are the same.");
            return;
        }

        double scale = graphHeight / (maxWeight - minWeight);

        System.out.println("Weight Timeline");

        for (int y = graphHeight; y >= 0; y--) {

            double weightValue = minWeight + (y * (maxWeight - minWeight) / graphHeight);
            System.out.printf("%5.1f | ", weightValue);

            for (Pair<LocalDateTime, Double> entry : weightEntries) {
                double weight = entry.u();
                if ((weight - minWeight) * scale >= y) {
                    System.out.print(" *    ");
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }

        System.out.print("       ");
        for (int i = 0; i < weightEntries.size(); i++) {
            System.out.print("----- ");
        }
        System.out.println();

        System.out.print("       ");
        for (Pair<LocalDateTime, Double> entry : weightEntries) {
            System.out.printf("%-5s ", entry.t().toLocalDate().toString().substring(5)); // Format MM-DD, padded
        }
        System.out.println();
    }
}
