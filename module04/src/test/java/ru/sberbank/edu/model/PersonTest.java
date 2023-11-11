package ru.sberbank.edu.model;

import org.junit.jupiter.api.*;
import org.assertj.core.api.Assertions;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonTest {

    List<Person> personList = new ArrayList<>();

    @BeforeEach
    void initPersonList() {
        personList.add(new Person("Valya", "Moscow", 26));
        personList.add(new Person("George", "Moscow", 44));
        personList.add(new Person("George", "Anapa", 49));
    }

    @Test
    @DisplayName("Sort by two fields")
    void sortingTest() {
        personList.sort(Person::compareTo);
        Assertions.assertThat(personList).isEqualTo(Arrays.asList(new Person("George", "Anapa", 49),
                new Person("George", "Moscow", 44), new Person("Valya", "Moscow", 26)));
    }
}
