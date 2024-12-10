package pgdp.FlipperFlow.Helpers;

public class Pair<T, R> {
    private T first;
    private R second;

    public Pair(T first, R second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(Pair.class) && ((Pair<T, R>) obj).getFirst().equals(first) && ((Pair<T, R>) obj).getSecond().equals(second);
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public R getSecond() {
        return second;
    }

    public void setSecond(R second) {
        this.second = second;
    }
}
