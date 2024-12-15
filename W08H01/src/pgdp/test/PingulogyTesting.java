package pgdp.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pgdp.pingulogy.RecursivePingulogy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PingulogyTesting {

    /**
     * Extend this class to compare the functionality of your recursive
     * sorting algorithms with the provided BubbleSort implementation.
     * You will find 2 examples below.
     */


    //Example: Test the method pinguDna.
    @Test
    @DisplayName("Test Pingu DNA")
    void testpinguCode() {
        assertEquals("GCACTCGAGA", RecursivePingulogy.pinguDNA(21, 25));
        assertEquals("ATCTCACTC", RecursivePingulogy.pinguDNA(13, 18));

    }
}
