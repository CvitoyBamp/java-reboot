package ru.sberbank.edu;

import ru.sberbank.edu.impl.CustomDigitComparator;
import ru.sberbank.edu.model.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Main class of 4th module
 */
public class App 
{
    public static void main( String[] args ) {
        CustomDigitComparator customDigitComparator = new CustomDigitComparator();
        List<Integer> al = new ArrayList<>();

        for (int i = 0; i <= 20; i++) {
            al.add(i);
        }

        System.out.println("Before sort: " + al);

        al.sort(customDigitComparator);

        System.out.println("After sort: " + al);

        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Valya", "Moscow", 26));
        personList.add(new Person("Vasya", "Moscow", 44));
        personList.add(new Person("Vasilisa", "Omsk", 21));
        personList.add(new Person("Andrey", "Omsk", 13));
        personList.add(new Person("Vasya", "Omsk", 44));
        personList.add(new Person("Vasya", "Tula", 53));
        personList.add(new Person("Andrey", "Volgograd", 33));
        personList.add(new Person("Artem", "Smolensk", 67));
        personList.add(new Person("George", "Anapa", 49));

        System.out.println(personList);

        personList.sort(Person::compareTo);

        System.out.println(personList);
    }
}
