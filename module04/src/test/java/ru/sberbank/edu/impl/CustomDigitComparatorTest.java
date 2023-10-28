package ru.sberbank.edu.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomDigitComparatorTest {
    List<Integer> testList;
    CustomDigitComparator customDigitComparator;

    @BeforeEach
    void initList() {
        testList = new ArrayList<>();
        customDigitComparator = new CustomDigitComparator();
    }

    @AfterEach
    void clearList() {
        testList.clear();
    }

    @Test
    @DisplayName("Sorting values in list, no duplicates")
    void evenOddSortingNoDuplicates() {

        for (int i = 0; i <= 20; i++) {
            testList.add(i);
        }

        testList.sort(customDigitComparator);

        Assertions.assertThat(testList).isEqualTo(Arrays.asList(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19));
    }

    @Test
    @DisplayName("Sorting values in list with duplicates")
    void evenOddSortingHasDuplicates() {

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 4; j++) {
                testList.add(j);
            }
        }

        testList.sort(customDigitComparator);

        Assertions.assertThat(testList).isEqualTo(Arrays.asList(0, 0, 0, 2, 2, 2, 4, 4, 4, 1, 1, 1, 3, 3, 3));
    }
}
