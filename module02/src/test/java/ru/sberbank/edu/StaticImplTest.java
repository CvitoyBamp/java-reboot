package ru.sberbank.edu;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.sberbank.edu.impl.FileStorage;
import ru.sberbank.edu.impl.StatisticImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for StaticImpl class
 */
public class StaticImplTest {
    StatisticImpl staticImpl;
    private static String inputFileName;
    private static String output;

    private final FileStorage fileStorage = new FileStorage();

    /**
     * Create test file and initialize variables once.
     */
    @BeforeClass
    public static void generateTestFile() {

        inputFileName = "testInput.txt";
        output = "testOutput.txt";

        try (BufferedWriter writter = new BufferedWriter(new FileWriter(inputFileName))) {
                writter.write("Это тестовый файл\n" + "Вот это самая длинная строчка!\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * init StatisticImpl class
     */
    @Before
    public void init() {
        staticImpl = new StatisticImpl(inputFileName, output);
    }

    /**
     * Test equality of lines count in input file.
     * @result content should be equal.
     */
    @Test
    public void getLineCountTest() {
        Assertions.assertThat(staticImpl.getLineCount()).isEqualTo(2).isNotNull();
    }

    /**
     * Test equality of whitespaces count in input file.
     * @result content should be equal.
     */
    @Test
    public void getSpaceCountTest() {
        Assertions.assertThat(staticImpl.getSpaceCount()).isEqualTo(6).isNotNull();
    }

    /**
     * Test equality of the longest line in input file.
     * @result content should be equal.
     */
    @Test
    public void getLongestLineTest() {
        Assertions.assertThat(staticImpl.getLongestLine()).isEqualTo("Вот это самая длинная строчка!");
    }

    /**
     * Check possibility of saving file to the filesystem.
     * @result output file should exist.
     */
    @Test
    public void possibilitySaveToFileTest() {
        staticImpl.save(2, 6, "Вот это самая длинная строчка!", fileStorage);
        Assertions.assertThat(new File(output)).exists();
    }

    /**
     * Check content of output file.
     * @result content should be equal to func results.
     */
    @Test
    public void outputContentTest() {
        staticImpl.save(2, 6, "Вот это самая длинная строчка!", fileStorage);
        List data = staticImpl.readFile(output);
        Assertions.assertThat(data.get(0)).isEqualTo(String.valueOf(staticImpl.getLineCount()));
        Assertions.assertThat(data.get(1)).isEqualTo(String.valueOf(staticImpl.getSpaceCount()));
        Assertions.assertThat(data.get(2)).isEqualTo(staticImpl.getLongestLine());
    }

}
