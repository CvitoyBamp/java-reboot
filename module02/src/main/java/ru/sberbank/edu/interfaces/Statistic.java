package ru.sberbank.edu.interfaces;

import java.util.List;

public interface Statistic {

    int getLineCount();
    int getSpaceCount();
    String getLongestLine();
    void save(int lineCount, int spaceCount, String line, Storage storage);
    List readFile(String inputFilePath);

}
