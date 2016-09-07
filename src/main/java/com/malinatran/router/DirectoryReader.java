package com.malinatran.router;

import java.io.File;

public class DirectoryReader {

    public String getListing(String fileName) {
        String listing = "";
        File file = new File(System.getProperty("user.dir") + "/" + fileName);
        String[] paths = file.list();

        if (paths != null) {
            for (String path : paths) {
                listing += path;
            }

            return listing;
        }

        return "";
    }
}