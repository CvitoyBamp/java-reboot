package ru.sberbank.edu.impl;

import ru.sberbank.edu.interfaces.Statistic;
import ru.sberbank.edu.interfaces.Storage;

/**
 * Class for DB saving
 * Implementation of {@link Storage}
 */
public class DbStorage implements Storage {
    @Override
    public void write(String[] outputData, String config) {
        for (String value : outputData) {
            System.out.println(value);
        }
        System.out.println("Записал в БД " + config);
    }
}
