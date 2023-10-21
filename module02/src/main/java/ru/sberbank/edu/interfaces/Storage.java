package ru.sberbank.edu.interfaces;

/**
 * Интерфейс для описания хранилища
 */
public interface Storage {

    /**
     *
     * @param outputData Данные для записи
     * @param config Креды для записи, если файл - название файла, если БД - jdbc строка
     */
    void write(String[] outputData, String config);
}
