package com.malinatran.resource;

import com.malinatran.utility.FileType;

public class FileTypeReader {

    private static final String[] FILE_EXTENSIONS = { ".gif", ".jpeg", ".jpg", ".png" };
    private static final String TXT = ".txt";

    public static FileType getFileType(String fileName) {
        if (isTextFile(fileName)) {
            return FileType.TEXT;
        } else if (isImageFile(fileName)) {
            return FileType.IMAGE;
        }

        return FileType.UNSUPPORTED;
    }

    public static boolean isTextFile(String fileName) {
        return isFileWithoutExtension(fileName) || isFileWithTxtExtension(fileName);
    }

    private static boolean isImageFile(String fileName) {
        for (String extension : FILE_EXTENSIONS) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isFileWithoutExtension(String fileName) {
        return (fileName.indexOf(".") == -1);
    }

    private static boolean isFileWithTxtExtension(String fileName) {
        return (fileName.endsWith(TXT));
    }
}