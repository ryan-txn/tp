package seedu.Healthmate;

import java.util.Scanner;

public class HealthMate {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        String logo = "HealthMate";
        System.out.println("Welcome to \n" + logo);
        System.out.println("What is your name?");

        Scanner in = new Scanner(System.in);
        System.out.println("Hello " + in.nextLine());
        in.close();
    }
}
