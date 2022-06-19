package ru.makhach.processor;

import ru.makhach.io.MergedFile;
import ru.makhach.utils.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Класс, отвечает за обработку файлов
 */
public class FileProcessor {
    private final Path rootDirectory;
    private final List<Path> files;
    private final MergedFile mergedFile;
    private final Logger logger;

    public FileProcessor(Path rootDirectory, MergedFile mergedFile) {
        this.rootDirectory = rootDirectory;
        this.mergedFile = mergedFile;
        this.files = new ArrayList<>();
        this.logger = Logger.getInstance();
    }

    public FileProcessor(Path rootDirectory) {
        this.rootDirectory = rootDirectory;
        this.mergedFile = new MergedFile(System.getProperty("user.dir"));
        this.files = new ArrayList<>();
        this.logger = Logger.getInstance();
    }

    /**
     * Запускает объединение текстовых файлов
     */
    public void mergeTextFiles() {
        logger.log("Start merge text files");
        if (mergedFile.isExist()) {
            collectTextFiles(rootDirectory.toFile());
            int filesCount = files.size();
            if (filesCount > 1) {
                sortFilesByName();
                mergeFiles();
            } else if (filesCount == 1)
                mergedFile.writeFromPath(files.get(0));
        }
    }

    /**
     * Рекурсивно обходит все директории и собирает текстовые файлы
     *
     * @param file директория поиска
     */
    private void collectTextFiles(File file) {
        File[] directoryFiles = file.listFiles();
        if (directoryFiles != null && directoryFiles.length > 0) {
            for (File directoryFile : directoryFiles) {
                if (directoryFile.isDirectory())
                    collectTextFiles(directoryFile);
                else if (directoryFile.getName().endsWith(MergedFile.TXT))
                    this.files.add(Paths.get(directoryFile.getAbsolutePath()));
            }
        }
    }

    /**
     * Сортировка файлов по имени
     */
    private void sortFilesByName() {
        this.files.sort(Comparator.comparing(Path::getFileName));
    }

    /**
     * Запись данных всех файлов в один
     */
    private void mergeFiles() {
        files.forEach(mergedFile::writeFromPath);
        logger.log("Merged " + files.size() + " files");
    }
}
