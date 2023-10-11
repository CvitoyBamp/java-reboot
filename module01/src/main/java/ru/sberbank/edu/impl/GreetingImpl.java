package ru.sberbank.edu.impl;

import ru.sberbank.edu.Greeting;

/**
 *
 * Implementation of {@link Greeting}
 */
public class GreetingImpl implements Greeting {

    /**
     *
     * @return my Best Hobbies
     */
    @Override
    public String getBestHobby() {
        String hobbies = "SRE, Devops, Russian Billiard, CrossCountry";
        return hobbies;
    }
}
