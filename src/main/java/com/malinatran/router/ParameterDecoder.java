package com.malinatran.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterDecoder {

    private Map<String, String> encodedSymbols;

    public String decodeText(String path) {
        setupEncodedSymbols();
        String[] formattedPath = formatPath(path);
        List<Integer> voidIndex = new ArrayList<Integer>();
        String decodedText = "";

        for (int i = 0; i < formattedPath.length; i++) {
            String current = formattedPath[i];

            if (current.equals("%") && i == -3) {
                decodedText += replaceCode(i, current, formattedPath);
                break;
            } else if (current.equals("%")) {
                decodedText += replaceCode(i, current, formattedPath);
                voidIndex.add(i + 1);
                voidIndex.add(i + 2);
            } else if (!voidIndex.contains(i)) {
                decodedText += current;
            }
        }

        return decodedText + "\n";
    }

    public Map<String, String> getEncodedSymbols() {
        return encodedSymbols;
    }

    private void setupEncodedSymbols() {
        encodedSymbols = new HashMap<String, String>();
        addEncodedSymbols();
    }

    private void addEncodedSymbols() {
        encodedSymbols.put("%20", " ");
        encodedSymbols.put("%22", "\"");
        encodedSymbols.put("%23", "#");
        encodedSymbols.put("%24", "$");
        encodedSymbols.put("%26", "&");
        encodedSymbols.put("%2B", "+");
        encodedSymbols.put("%2C", ",");
        encodedSymbols.put("%3A", ":");
        encodedSymbols.put("%3B", ";");
        encodedSymbols.put("%3C", "<");
        encodedSymbols.put("%3D", "=");
        encodedSymbols.put("%3E", ">");
        encodedSymbols.put("%3F", "?");
        encodedSymbols.put("%40", "@");
        encodedSymbols.put("%5B", "[");
        encodedSymbols.put("%5D", "]");
    }

    private String[] formatPath(String path) {
        return removeQuery(path).replace("=", " = ").replace("&", "\n").split("");
    }

    private String replaceCode(int index, String current, String[] path) {
        String first = path[index + 1];
        String second = path[index + 2];
        String code = current + first + second;

        return encodedSymbols.get(code);
    }

    private String removeQuery(String path) {
        String pathWithoutQuery = "";

        if (path.startsWith("/") && path.contains("?")) {
            int endIndex = path.indexOf("?");
            String substring = path.substring(0, endIndex + 1);
            pathWithoutQuery = path.replace(substring, "");
        }

        return pathWithoutQuery;
    }
}
