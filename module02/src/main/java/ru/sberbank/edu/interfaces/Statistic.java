package ru.sberbank.edu.interfaces;

import java.io.IOException;
import java.util.ArrayList;

public interface Statistic {

    int getLineCount();
    int getSpaceCount();
    String getLongestLine();
    void save(int lineCount, int spaceCount, String line, Storage storage);
    ArrayList<String> readFile(String inputFilePath);

}
