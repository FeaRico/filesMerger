package ru.makhach.dialog;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Диалог для выбора корневой директории
 */
public class DirectoryOpener implements AutoCloseable {
    private final FileDialog fileDialog;
    private final JFrame parent;

    /**
     * Создаётся файловый диалог, способный работать только с каталогами
     */
    public DirectoryOpener() {
        parent = new JFrame();
        fileDialog = new FileDialog(parent, "Выберите каталог", FileDialog.LOAD);
        FilenameFilter directoryFilter = (dir, name) -> new File(dir, name).isDirectory();
        fileDialog.setFilenameFilter(directoryFilter);
        System.setProperty("apple.awt.fileDialogForDirectories", "true");
        fileDialog.setVisible(true);
        System.setProperty("apple.awt.fileDialogForDirectories", "false");
    }

    /**
     * Освобождение ресурсов окна
     */
    public void close() {
        fileDialog.dispose();
        parent.dispose();
    }

    /**
     * Возвращает выбранный каталог пользователем.
     * Может вернуть пустой Optional в случае ошибки.
     *
     * @return каталог, обёрнутый в Optional
     */
    public Optional<Path> getSelectedDirectory() {
        File[] selectedFiles = fileDialog.getFiles();
        Path selectedDirectory = Paths.get(selectedFiles[0].getPath());
        if (selectedDirectory.toFile().isDirectory())
            return Optional.of(selectedDirectory);
        else
            return Optional.empty();
    }
}
