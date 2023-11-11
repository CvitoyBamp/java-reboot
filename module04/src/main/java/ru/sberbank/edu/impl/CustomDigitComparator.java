package ru.sberbank.edu.impl;

import java.util.Comparator;
import static ru.sberbank.edu.helper.ValueType.isEven;
import static ru.sberbank.edu.helper.ValueType.isOdd;

/**
 * Custom Comparator for even/odd values
 */
public class CustomDigitComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
        if (o1.intValue() == o2.intValue()) {
            return 0;
        }

        if ((isEven(o1) && isEven(o2)) || (isOdd(o1) && isOdd(o2))) {
            if (o1 > o2) {
                return 1;
            } else {
                return -1;
            }
        }

        if ((isEven(o1) && isOdd(o2))) {
            return -1;
        } else {
            return 1;
        }
    }
}
