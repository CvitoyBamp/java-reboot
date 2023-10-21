package ru.sberbank.edu.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class GCDTest {
    private final int expectedNOD;
    private final int firstNumber;
    private final int secondNumber;

    public GCDTest(int expectedNOD, int firstNumber, int secondNumber) {
        this.expectedNOD = expectedNOD;
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
    }

    @Parameterized.Parameters(name = "Test №{index}: data for Euclids algorithm - {1} {2}")
    public static Object[][] testData() {
        return new Object[][]{
                {5, 5, 10},
                {4, 312, 44},
                {12, 876, 96},
        };
    }

    @Test
    public void testGetDivisorMethod() {
        Assertions.assertEquals(expectedNOD, new GCD().getDivisor(firstNumber, secondNumber));
    }
}
