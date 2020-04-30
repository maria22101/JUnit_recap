package com.virtualpairprogrammers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Parameterized test class example
 */
@RunWith(value = Parameterized.class)
public class GradesExplanationTest {
    private int numA;
    private int numB;
    private String expected;

    //Inject via constructor
    //for {8, 2, 5}, numA = 8, numB = 2, expected = 5
    public GradesExplanationTest(int numA, int numB, String expected) {
        this.numA = numA;
        this.numB = numB;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {8, 2, "5"},
                {4, 5, "5"},
                {10, 4, "7"},
                {3, 9, "6"},
                {5, 8, "7"}
        });
    }

    @Test
    public void getAverageYearGradeTest() {
        assertThat(GradesExplanation.getAverageYearGrade(numA, numB), is(expected));
    }
}