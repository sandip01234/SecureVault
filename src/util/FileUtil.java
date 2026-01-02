package util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {

    /** Check if a file exists */
    public static boolean exists(Path path) {
        return Files.exists(path);
    }

    /** Create directory if it does not exist */
    public static void createDirIfNotExists(Path dir) throws Exception {
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
    }

    /** Get file extension */
    public static String getExtension(File file) {
        String name = file.getName();
        int lastIndex = name.lastIndexOf(".");
        if (lastIndex == -1) return "";
        return name.substring(lastIndex + 1);
    }

    /** Get file name without extension */
    public static String getFileNameWithoutExtension(File file) {
        String name = file.getName();
        int lastIndex = name.lastIndexOf(".");
        if (lastIndex == -1) return name;
        return name.substring(0, lastIndex);
    }
}
