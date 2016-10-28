package com.malinatran.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterDecoder {

    private Map<String, String> decoderMapping;

    public String decodeText(String path) {
        setupDecoderMapping();
        String[] formattedPath = formatPath(path);
        List<Integer> voidIndex = new ArrayList<Integer>();
        String decodedText = "";

        for (int i = 0; i < formattedPath.length; i++) {
            String currentChar = formattedPath[i];

            if (currentChar.equals("%") && i == -3) {
                decodedText += replaceCode(i, currentChar, formattedPath);
                break;
            } else if (currentChar.equals("%")) {
                decodedText += replaceCode(i, currentChar, formattedPath);
                voidIndex.add(i + 1);
                voidIndex.add(i + 2);
            } else if (!voidIndex.contains(i)) {
                decodedText += currentChar;
            }
        }

        return decodedText;
    }

    public Map<String, String> getDecoderMapping() {
        return decoderMapping;
    }

    private void setupDecoderMapping() {
        decoderMapping = new HashMap<String, String>();
        addDecoderMapping();
    }

    private void addDecoderMapping() {
        decoderMapping.put("%20", " ");
        decoderMapping.put("%22", "\"");
        decoderMapping.put("%23", "#");
        decoderMapping.put("%24", "$");
        decoderMapping.put("%26", "&");
        decoderMapping.put("%2B", "+");
        decoderMapping.put("%2C", ",");
        decoderMapping.put("%3A", ":");
        decoderMapping.put("%3B", ";");
        decoderMapping.put("%3C", "<");
        decoderMapping.put("%3D", "=");
        decoderMapping.put("%3E", ">");
        decoderMapping.put("%3F", "?");
        decoderMapping.put("%40", "@");
        decoderMapping.put("%5B", "[");
        decoderMapping.put("%5D", "]");
    }

    private String[] formatPath(String path) {
        return removeQuery(path).replace("=", " = ").replace("&", "\n").split("");
    }

    private String replaceCode(int index, String currentChar, String[] path) {
        String first = path[index + 1];
        String second = path[index + 2];
        String code = currentChar + first + second;

        return decoderMapping.get(code);
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
