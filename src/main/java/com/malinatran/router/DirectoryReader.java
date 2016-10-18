package com.malinatran.router;

import java.io.File;

public class DirectoryReader {

    private static final String ROOT_DIRECTORY = System.getProperty("user.home") + "/Development/cob_spec/";

    public String getLinks(String directoryName) {
        File directory = new File(ROOT_DIRECTORY + directoryName);
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