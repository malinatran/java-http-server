package com.malinatran.utility;

import java.io.File;

public class FileSeparator {

    public static String format(String arg) {
        if (hasNone(arg)) {
            return File.separator + arg + File.separator;
        } else if (hasStartOnly(arg)) {
            return arg + File.separator;
        } else if (hasEndOnly(arg)) {
            return File.separator + arg;
        } else {
            return arg;
        }
    }

    private static boolean hasNone(String arg) {
        return (!hasStart(arg) && !hasEnd(arg));
    }

    private static boolean hasEndOnly(String arg) {
        return (hasEnd(arg) && !hasStart(arg));
    }

    private static boolean hasStartOnly(String arg) {
        return (hasStart(arg) && !hasEnd(arg));
    }

    private static boolean hasStart(String arg) {
        return (arg.startsWith(File.separator));
    }

    private static boolean hasEnd(String arg) {
        return (arg.endsWith(File.separator));
    }
}