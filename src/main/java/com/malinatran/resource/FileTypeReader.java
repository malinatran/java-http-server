package com.malinatran.resource;

import com.malinatran.utility.FileType;

import static com.malinatran.utility.FileType.*;

public class FileTypeReader {

    private static final String[] FILE_EXTENSIONS = { ".gif", ".jpeg", ".jpg", ".png" };
    private static final String TXT = ".txt";

    public static FileType getFileType(String fileName) {
        if (isTextFile(fileName)) {
            return TEXT;
        } else if (isImageFile(fileName)) {
            return IMAGE;
        } else {
            return UNSUPPORTED;
        }
    }

    public static String getFileExtension(String fileName) {
        int startIndex = fileName.indexOf(".") + 1;
        int endIndex = fileName.length();

        return fileName.substring(startIndex, endIndex);
    }

    public static boolean isTextFile(String fileName) {
        return doesNotHaveFileExtension(fileName) || hasTxtExtension(fileName);
    }

    private static boolean doesNotHaveFileExtension(String fileName) {
        return (fileName.indexOf(".") == -1);
    }

    private static boolean hasTxtExtension(String fileName) {
        return (fileName.endsWith(TXT));
    }

    private static boolean isImageFile(String fileName) {
        for (String extension : FILE_EXTENSIONS) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }
}