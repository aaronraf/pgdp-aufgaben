package pgdp.tests;

import pgdp.pingumath.SAT;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSAT {

    @Test
    void testIsPow() {
        assertTrue(SAT.isPow(3, 8));
    }
}
