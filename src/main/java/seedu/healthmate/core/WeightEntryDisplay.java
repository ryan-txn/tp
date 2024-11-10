package seedu.healthmate.core;

import seedu.healthmate.services.UI;
import seedu.healthmate.utils.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for displaying weight entries in a timeline graph.
 */
public class WeightEntryDisplay {
    private static List<Pair<LocalDateTime, Double>> weightEntries = new ArrayList<>();

    /**
     * Displays a timeline graph of weight entries from the provided user entry list.
     * The graph is printed in the console, with the most recent weight entries shown.
     * If there are no entries or if all weight entries are identical, an appropriate message is displayed.
     *
     * @param userEntryList An {@link Optional} containing the user entry list. If empty, no weight entries are
     *                      displayed.
     */
    public static void display(Optional<UserEntryList> userEntryList) {
        if (userEntryList.isEmpty()) {
            UI.printReply("User data is missing.","");
            return;
        }

        weightEntries = extractWeightEntries(userEntryList.get());
        if (weightEntries.isEmpty()) {
            UI.printReply("Weight Entries is missing.","");
            return;
        }

        double minWeight = calculateMinWeight();
        double maxWeight = calculateMaxWeight();

        if(minWeight == maxWeight) {
            UI.printReply("Not enough weight entries of different variance.","");
            return;
        }
        double scale = calculateScale(minWeight, maxWeight);
        printGraph(minWeight, maxWeight, scale);
    }

    /**
     * Extracts the weight entries from the provided user entry list.
     *
     * @param userEntryList The user entry list.
     * @return A list of pairs containing the weight entries (date-time and weight).
     */
    private static List<Pair<LocalDateTime, Double>> extractWeightEntries(UserEntryList userEntryList) {
        List<Pair<LocalDateTime, Double>> entries = new ArrayList<>();
        ArrayList<User> users = userEntryList.getUserEntryList();
        int size = users.size();
        int startIndex = Math.max(0, size - 10);

        for (int i = startIndex; i < size; i++) {
            User user = users.get(i);
            entries.add(new Pair<>(user.getLocalDateTime(), user.getWeight()));
        }
        return entries;
    }

    /**
     * Calculates the minimum weight value from the weight entries.
     *
     * @return The minimum weight value.
     */
    private static double calculateMinWeight() {
        return weightEntries.stream()
                .mapToDouble(Pair::u)
                .min()
                .orElse(Double.MAX_VALUE);
    }

    /**
     * Calculates the maximum weight value from the weight entries.
     *
     * @return The maximum weight value.
     */
    private static double calculateMaxWeight() {
        return weightEntries.stream()
                .mapToDouble(Pair::u)
                .max()
                .orElse(Double.MIN_VALUE);
    }

    /**
     * Calculates the scale factor to fit the weight entries into the graph.
     *
     * @param minWeight The minimum weight value.
     * @param maxWeight The maximum weight value.
     * @return The scale factor.
     */
    private static double calculateScale(double minWeight, double maxWeight) {
        final int graphHeight = 20;
        return graphHeight / (maxWeight - minWeight);
    }

    /**
     * Prints the weight timeline graph to the console.
     *
     * @param minWeight The minimum weight value.
     * @param maxWeight The maximum weight value.
     * @param scale     The scale factor for the graph.
     */
    private static void printGraph(double minWeight, double maxWeight, double scale) {
        final int graphHeight = 20;
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

        printGraphFooter();
    }

    /**
     * Prints the footer of the weight timeline graph (dates).
     */
    private static void printGraphFooter() {
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
