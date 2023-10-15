package ru.sberbank.edu.impl;

import ru.sberbank.edu.interfaces.Statistic;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class for reading and saving files
 * Implementation of {@link Statistic}
 */
public class StatisticImpl implements Statistic {

    private String inputFile;
    private String outputFile;
    private final ArrayList<String> data;

    /**
     * Constructor for StatisticImpl class
     */
    public StatisticImpl(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        data = readFile(inputFile);
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * Read file func
     * @param inputFile - file name for reading
     * @return ArrayList of lines
     */
    @Override
    public ArrayList<String> readFile(String inputFile) {

        String strLine;
        ArrayList<String> ans = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(inputFile));
            while ((strLine = br.readLine()) != null) {
                ans.add(strLine);
            }
            return ans;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Couldn't find the file!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get lines count
     * @return lines count in file
     */
    @Override
    public int getLineCount() {
        return data.size();
    }

    /**
     * Get space count
     * @return count of whitespaces
     */
    @Override
    public int getSpaceCount() {
        return (int) data.stream().flatMapToInt(str -> str.chars().filter(Character::isWhitespace)).count();
    }

    /**
     * Get the longest line
     * @return line which have the most symbols
     */
    @Override
    public String getLongestLine() {
        return data.stream().max(Comparator.comparing(String::length)).orElseThrow();
    }

    /**
     * Save to file
     * @param lineCount value from getLineCount
     * @param spaceCount value from getSpaceCount
     * @param line value from getLongestLine
     */
    @Override
    public void save(int lineCount, int spaceCount, String line) {
        String[] outputData = {String.valueOf(lineCount), String.valueOf(spaceCount), line};
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(outputFile))) {
            for (String value : outputData) {
                writter.write(value + "\n");
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
