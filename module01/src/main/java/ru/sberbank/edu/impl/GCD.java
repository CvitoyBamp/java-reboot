package ru.sberbank.edu.impl;

import ru.sberbank.edu.CommonDivisor;

/**
 * Class have method for getting devisior by Euclid's algorithm
 * Implementation of {@link CommonDivisor}
 */
public class GCD implements CommonDivisor {

    /**
     *
     * @param firstNumber in Euclid's algorithm
     * @param secondNumber in Euclid's algorithm
     * @return greatest common divisor of two numbers
     */
    @Override
    public int getDivisor(int firstNumber, int secondNumber) {
        while (secondNumber != 0) {
            int tmp = firstNumber % secondNumber;
            firstNumber = secondNumber;
            secondNumber = tmp;
        }
        return firstNumber;
    }
}
