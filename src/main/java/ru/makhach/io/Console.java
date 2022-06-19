package ru.makhach.io;

import ru.makhach.dialog.DirectoryOpener;
import ru.makhach.processor.FileProcessor;
import ru.makhach.utils.Logger;

import java.nio.file.Path;
import java.util.Optional;

/**
 * Консольная оболочка
 */
public class Console {
    private final Logger logger;

    public Console() {
        this.logger = Logger.getInstance();
    }

    public void start() {
        logger.log("Console started");
        try (DirectoryOpener opener = new DirectoryOpener()) {
            MergedFile mergedFile = new MergedFile("/home/xamelion/text");
            Optional<Path> selectedDirectory = opener.getSelectedDirectory();
            if (selectedDirectory.isPresent()) {
                FileProcessor fileProcessor = new FileProcessor(selectedDirectory.get(), mergedFile);
                fileProcessor.mergeTextFiles();
            } else
                logger.log("An error occurred while reading the directory!");
        }
        logger.log("Console stopped");
    }
}
