package pgdp.triangles;

public class Point2D {

    private double x;
    private double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector2D vectorFrom(Point2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point2D other = (Point2D) obj;
        double delta = 1e-12;
        if (Math.abs(this.x - other.x) > delta)
            return false;
        if (Math.abs(this.y - other.y) > delta)
            return false;
        return true;
    }

}
