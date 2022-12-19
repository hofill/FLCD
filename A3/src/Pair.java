import java.util.Objects;

public class Pair<T1, T2> {

    private final T1 firstElement;
    private final T2 secondElement;

    public Pair(T1 firstElement, T2 secondElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
    }

    @Override
    public String toString() {
        return "(" +
                firstElement +
                "," + secondElement +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return firstElement.equals(pair.firstElement) &&
                secondElement.equals(pair.secondElement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstElement, secondElement);
    }

    public T1 getFirst() {
        return this.firstElement;
    }

    public T2 getSecond() {
        return this.secondElement;
    }


}
