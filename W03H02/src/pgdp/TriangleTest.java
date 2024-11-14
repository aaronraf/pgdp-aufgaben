package pgdp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pgdp.triangles.Point2D;
import pgdp.triangles.Triangle;
import pgdp.triangles.TriangleFactory;
import pgdp.triangles.Vector2D;

import static org.junit.jupiter.api.Assertions.*;

public class TriangleTest {

    // ***********************************
    // DO NOT DELETE OR CHANGE THE FOLLOWING LINES
    // ***********************************
    @BeforeEach
    void setup() {
        PinguLib.setup();
    }

    @AfterEach
    void reset() {
        PinguLib.reset();
    }
    // ***********************************

    private TriangleFactory triangleFactory;
    private Point2D a;
    private Point2D b;
    private Point2D c;
    private Point2D aSharp;
    private Point2D bSharp;
    private Point2D cSharp;
    private Triangle triangle;
    private Triangle complement;

    public TriangleTest() {
        triangleFactory = TriangleFactory.getInstance();
        a = new Point2D(0.0, 0.0);
        b = new Point2D(1.0, 1.0);
        c = new Point2D(2.0, 0.0);
        aSharp = new Point2D(-1.0, -1.0);
        bSharp = new Point2D(-2.0, 2.0);
        cSharp = new Point2D(-5.0, -2.0);
        triangle = triangleFactory.createTriangle(a, b, c);
        complement = triangleFactory.createTriangle(aSharp, bSharp, cSharp);
    }

    @Test
    public void triangleStructureTest() {
        triangle = triangleFactory.createTriangle(aSharp, b, c);
        triangle.setA(a);

        assertEquals(triangle.getA(), a);
        assertNotEquals(triangle.getA(), aSharp);
    }

    @Test
    public void triangleGetVectorTest() {
        Vector2D ab = new Vector2D(1.0, 1.0);
        Vector2D ba = new Vector2D(-1.0, -1.0);

        assertEquals(triangle.getAB(), ab);
        assertNotEquals(triangle.getAB(), ba);
    }

    @Test
    public void trianglePointAngleTest() {
        Vector2D ab = triangle.getAB();
        Vector2D ac = triangle.getAC();
        Vector2D abSharp = complement.getAB();
        Vector2D acSharp = complement.getAC();

        assertEquals(triangle.getAngleAtA(), Math.acos(ab.dot(ac) / (ab.length() * ac.length())), 1e-12);
        assertEquals(complement.getAngleAtA(), Math.acos(abSharp.dot(acSharp) / (abSharp.length() * acSharp.length())), 1e-12);
    }

    @Test
    public void triangleAreaTest() {
        assertEquals(triangle.getArea(), 1.0, 1e-12);
        assertEquals(complement.getArea(), 6.5, 1e-12);
    }

    @Test
    public void triangleIsScaleneTest() {
        assertFalse(triangle.isScalene());
        assertTrue(complement.isScalene());
    }

    @Test
    public void triangleIsoscelesTest() {
//        assertNotEquals(triangle.getAB().length(), triangle.getAC().length(), 1e-12);
//        assertEquals(triangle.getAB().length(), triangle.getBC().length(), 1e-12);
//        assertNotEquals(triangle.getAC().length(), triangle.getBC().length(), 1e-12);
        assertTrue(triangle.isIsosceles());

//        assertNotEquals(complement.getAB().length(), complement.getAC().length(), 1e-12);
//        assertNotEquals(complement.getAB().length(), complement.getBC().length(), 1e-12);
//        assertNotEquals(complement.getAC().length(), complement.getBC().length(), 1e-12);
        assertFalse(complement.isIsosceles());
    }

    @Test
    public void triangleEquilateralTest() {
        assertFalse(triangle.isEquilateral());
        assertFalse(complement.isEquilateral());

        Point2D ae = new Point2D(0.0, 0.5773502691896257);
        Point2D be = new Point2D(-0.5, -0.28867513459481287);
        Point2D ce = new Point2D(0.5, -0.28867513459481287);
        Triangle equilateral = triangleFactory.createTriangle(ae, be, ce);

        assertTrue(equilateral.isEquilateral());
    }

}
