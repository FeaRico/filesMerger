package ru.makhach.io;

import ru.makhach.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Файл, в который объединяется вся информация
 */
public class MergedFile extends File {
    public static final String TXT = ".txt";
    private final Logger logger;

    public MergedFile(String path) {
        super(path + TXT);
        logger = Logger.getInstance();
        initializeMergedFile();
    }

    /**
     * @return true - если файл существует.
     * Иначе false
     */
    public Boolean isExist() {
        return Files.exists(toPath());
    }

    /**
     * Записывает в себя данные из файла
     *
     * @param path путь файла
     */
    public void writeFromPath(Path path) {
        try {
            Files.write(toPath(), Files.readAllBytes(path), StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Начальная инициализация файла.
     * В случае если файла по переданному пути нет, создаёт его.
     * Если такой файл уже есть и имеет уже в себе информарцию,
     * чистит её.
     */
    private void initializeMergedFile() {
        if (Files.notExists(toPath()))
            createMergedFile(this.getAbsolutePath());
        cleanMergedFileIfDirty();
    }

    /**
     * Создание файла по пути
     *
     * @param path путь к файлу
     */
    private void createMergedFile(String path) {
        try {
            Files.createFile(Paths.get(path));
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Очищает файл, если имеются какие-либо записанные данные
     */
    private void cleanMergedFileIfDirty() {
        try {
            if (Files.readAllLines(toPath()).size() > 0)
                Files.write(toPath(), new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
