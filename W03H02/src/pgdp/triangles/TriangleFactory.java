package pgdp.triangles;

public class TriangleFactory {
    private static TriangleFactory instance;

    public static TriangleFactory getInstance(){
        if (instance == null) {
            instance = new TriangleFactory();
        }
        return instance;
    }

    public Triangle createTriangle(Point2D p1, Point2D p2, Point2D p3) {
        return new Triangle(p1, p2 ,p3);
    }

}
