package ru.sberbank.edu.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GreetingImplTest {
    String actualGreet = new GreetingImpl().getBestHobby();
    String expectedGreet = "SRE, Devops, Russian Billiard, CrossCountry";

    @Test
    public void testGetBestHobbyMethodIsEqual() {
        Assertions.assertEquals(expectedGreet, actualGreet);
    }
}
