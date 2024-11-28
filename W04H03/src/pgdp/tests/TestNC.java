package pgdp.tests;

import pgdp.pingumath.NumberConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestNC {

    @Test
    void testIntToPinguNum() {
        assertEquals("Pin", NumberConverter.intToPinguNum(2));
    }
}
