package com.malinatran.router;

import java.io.File;

public class DirectoryReader {

    public String getLinks(String directoryName) {
        File directory = new File("/Users/mteatran/Development/cob_spec/" + directoryName);
        String[] files = directory.list();

        return (files != null) ? getAnchorTagLinks(files) : "";
    }

    private String getAnchorTagLinks(String[] files) {
        String links = "";

        for (String file : files) {
            links += "<a href=\"/" + file + "\">" + file + "</a>\n";
        }

        return links;
    }
}