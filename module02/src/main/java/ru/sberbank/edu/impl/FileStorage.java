package ru.sberbank.edu.impl;

import ru.sberbank.edu.interfaces.Storage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for File saving
 * Implementation of {@link Storage}
 */
public class FileStorage implements Storage {
    @Override
    public void write(String[] outputData, String config) {
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(config))) {
            for (String value : outputData) {
                writter.write(value + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
