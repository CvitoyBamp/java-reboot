package ru.sberbank.edu.helper;

/**
 * Helper class for value checking
 */
public class ValueType {

    public ValueType() {
    }

    /**
     * Check for even value
     * @param x - integer
     * @return is number even?
     */
    public static boolean isEven(int x) {
        return x % 2 == 0;
    }

    /**
     * Check for odd value
     * @param x - integer
     * @return is number odd?
     */
    public static boolean isOdd(int x) {
        return x % 2 != 0;
    }
}
