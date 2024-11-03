package seedu.healthmate.utils;

/**
 * A generic class representing a pair of two values of potentially different types.
 * @param <T> The type of the first element
 * @param <U> The type of the second element
 */
public class Pair<T, U> {

    private final T t;
    private final U u;

    /**
     * Constructs a new Pair with the given values.
     * @param t The first element
     * @param u The second element
     */
    public Pair(T t, U u) {
        this.t = t;
        this.u = u;
    }

    /**
     * Returns the first element of the pair.
     * @return The first element
     */
    public T t() {
        return this.t;
    }

    /**
     * Returns the second element of the pair.
     * @return The second element
     */
    public U u() {
        return this.u;
    }

    /**
     * Returns a string representation of the pair.
     * @return A string in the format "Pair of [first element], [second element]"
     */
    @Override
    public String toString() {
        return "Pair of " + this.t + ", " + this.u;
    }

}
