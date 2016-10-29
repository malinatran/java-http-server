package com.malinatran.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterDecoder {

    private Map<String, String> encodedCharacters;

    public Map<String, String> getEncodedCharacters() {
        return encodedCharacters;
    }

    public String decodeText(String path) {
        encodedCharacters = new HashMap<String, String>();
        addEncodedCharacters();
        String decodedText = "";

        if (hasParametersQuery(path)) {
            decodedText += readAndDecodeCharacter(path);
        }

        return decodedText;
    }

    private String readAndDecodeCharacter(String path) {
        String[] formattedPath = formatPath(path);
        List<Integer> voidIndex = new ArrayList<Integer>();
        String text = "";

        for (int i = 0; i < formattedPath.length; i++) {
            String current = formattedPath[i];

            if (current.equals("%") && i == -3) {
                text += getCharacter(i, current, formattedPath);
                break;
            } else if (current.equals("%")) {
                text += getCharacter(i, current, formattedPath);
                voidIndex.add(i + 1);
                voidIndex.add(i + 2);
            } else if (!voidIndex.contains(i)) {
                text += current;
            }
        }

        return text + "\n";
    }

    private void addEncodedCharacters() {
        encodedCharacters.put("%20", " ");
        encodedCharacters.put("%22", "\"");
        encodedCharacters.put("%23", "#");
        encodedCharacters.put("%24", "$");
        encodedCharacters.put("%26", "&");
        encodedCharacters.put("%2B", "+");
        encodedCharacters.put("%2C", ",");
        encodedCharacters.put("%3A", ":");
        encodedCharacters.put("%3B", ";");
        encodedCharacters.put("%3C", "<");
        encodedCharacters.put("%3D", "=");
        encodedCharacters.put("%3E", ">");
        encodedCharacters.put("%3F", "?");
        encodedCharacters.put("%40", "@");
        encodedCharacters.put("%5B", "[");
        encodedCharacters.put("%5D", "]");
    }

    private Boolean hasParametersQuery(String path) {
        return (path.startsWith("/parameters?"));
    }

    private String[] formatPath(String path) {
        return removeQuery(path).replace("=", " = ").replace("&", "\n").split("");
    }

    private String getCharacter(int index, String current, String[] path) {
        String first = path[index + 1];
        String second = path[index + 2];
        String code = current + first + second;

        return encodedCharacters.get(code);
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
