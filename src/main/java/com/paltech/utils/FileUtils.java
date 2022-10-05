package com.code88.utils;

import com.paltech.constant.StopWatch;
import org.openqa.selenium.TimeoutException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author isabella.huynh
 * created on Jan/05/2019.
 */
public class FileUtils {
    /**
     * @param pathFile C:\path\filename.abc
     * @return true if file exists
     * @throws TimeoutException
     */
    public static boolean doesFileNameExist(String pathFile) throws TimeoutException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Path path = Paths.get(pathFile);
        while (stopWatch.getElapsedTime() < 2 * 1000) {
            if (Files.isDirectory(path)) {
                System.out.println(String.format("Info: '%s' is a directory", pathFile));
                return false;
            }
            if (Files.exists(path)) {
                System.out.println("Info: The file name exists at " + pathFile);
                return true;
            }
        }
        System.out.println("Info: The file name doesn't exist at " + pathFile);
        return false;
    }


    public static void removeFile(String pathFile) throws IOException {
        Path path = Paths.get(pathFile);
        if (Files.exists(path)) {
            Files.delete(path);
            System.out.println(String.format("Info: Succeeded deleting the file name at '%s'", pathFile));
        } else {
            System.out.println(String.format("Info: File doesn't exist at '%s'", pathFile));
        }
    }
}
