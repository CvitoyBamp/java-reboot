package ru.sberbank.edu;

import ru.sberbank.edu.impl.CustomArrayImpl;

import java.util.ArrayList;

/**
 * Main class
 *
 */
public class App 
{
    public static void main( String[] args ) {

        /**
         * См. тесты
         */
        CustomArrayImpl<String> customArrayString = new CustomArrayImpl<>();
        customArrayString.add("1");
        customArrayString.add("2");
        customArrayString.add("3");
        customArrayString.reverse();
        System.out.println(customArrayString);
    }
}
