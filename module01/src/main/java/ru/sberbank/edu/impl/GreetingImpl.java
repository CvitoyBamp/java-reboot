package ru.sberbank.edu.impl;

import ru.sberbank.edu.Greeting;

/**
 * Class have methid which return the best hobbies
 * Implementation of {@link Greeting}
 */
public class GreetingImpl implements Greeting {

    /**
     *
     * @return my Best Hobbies
     */
    @Override
    public String getBestHobby() {
        return "SRE, Devops, Russian Billiard, CrossCountry";
    }
}
