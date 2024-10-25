package seedu.healthmate;

public class MealNotFoundException extends RuntimeException {
  public MealNotFoundException(String message) {
    super(message);
  }
}
