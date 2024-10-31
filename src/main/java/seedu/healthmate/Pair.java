package seedu.healthmate;
public class Pair<T, U> {

    private final T t;
    private final U u;

    public Pair(T t, U u) {
        this.t = t;
        this.u = u;
    }

    public T t() {
        return this.t;
    }

    public U u() {
        return this.u();
    }

    @Override
    public String toString() {
        return "Pair of " + this.t + ", " + this.u;
    }

}
