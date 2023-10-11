package ru.sberbank.edu;

import ru.sberbank.edu.impl.GCD;
import ru.sberbank.edu.impl.GreetingImpl;

/**
 *
 * Main method
 */
public class App {
    public static void main( String[] args ) {
        Greeting greet = new GreetingImpl();
        System.out.println(greet.getBestHobby());

        CommonDivisor gcd = new GCD();
        System.out.println(gcd.getDivisor(10, 5));
    }
}
