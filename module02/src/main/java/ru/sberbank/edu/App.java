package ru.sberbank.edu;

import ru.sberbank.edu.impl.DbStorage;
import ru.sberbank.edu.impl.FileStorage;
import ru.sberbank.edu.impl.StatisticImpl;
import ru.sberbank.edu.interfaces.Statistic;

/**
 * Main class
 *
 */
public class App {
    public static void main( String[] args ) {

        final String INPUT_FILE = "D:\\java\\sber\\homework-javareboot-2023-group-06\\module02\\src\\main\\java\\ru\\sberbank\\edu\\input.txt";
        final String OUTPUT_FILE = "D:\\java\\sber\\homework-javareboot-2023-group-06\\module02\\src\\main\\java\\ru\\sberbank\\edu\\output.txt";

        FileStorage fileStorage = new FileStorage();
        DbStorage db = new DbStorage();

        Statistic statistic = new StatisticImpl(INPUT_FILE, OUTPUT_FILE);
        statistic.save(statistic.getLineCount(), statistic.getSpaceCount(), statistic.getLongestLine(), db);
    }
}
