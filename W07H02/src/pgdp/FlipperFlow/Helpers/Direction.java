package pgdp.FlipperFlow.Helpers;

public enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final Vector2 vector;

    private Direction(int x, int y) {
        this.vector = new Vector2(x, y);
    }

    public static Direction reverse(Direction direction) {
        return switch (direction) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }

    public static Vector2 getCoordinatesFromDirection(Direction dir, Vector2 pos) {
        return pos.add(dir.getVector());
    }

    public static Direction getDirectionFromCoordinates(Vector2 current, Vector2 next) {
        int deltaX = next.getX() - current.getX();
        int deltaY = next.getY() - current.getY();

        return switch (deltaX) {
            case 0 -> deltaY == 1 ? UP : deltaY == -1 ? DOWN : null;
            case -1 -> deltaY == 0 ? LEFT : null;
            case 1 -> deltaY == 0 ? RIGHT : null;
            default -> null;
        };
    }

    public Vector2 getVector() {
        return vector;
    }
}
