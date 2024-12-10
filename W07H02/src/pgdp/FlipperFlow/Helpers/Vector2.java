package pgdp.FlipperFlow.Helpers;

public class Vector2 {
    private Pair<Integer, Integer> xy;

    public Vector2(int x, int y) {
        this.xy = new Pair<>(x, y);
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(getX() + other.getX(), getY() + other.getY());
    }

    @Override
    public boolean equals(Object obj) {
        Vector2 other = (Vector2) obj;
        return obj.getClass().equals(Vector2.class) && getX() == other.getX() && getY() == other.getY();
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", getX(), getY());
    }

    public void setX(int x) {
        xy.setFirst(x);
    }

    public int getX() {
        return xy.getFirst();
    }

    public void setY(int y) {
        xy.setSecond(y);
    }

    public int getY() {
        return xy.getSecond();
    }
}
