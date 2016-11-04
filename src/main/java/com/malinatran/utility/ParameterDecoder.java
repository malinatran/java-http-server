package com.malinatran.utility;

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
        List<Integer> voidChar = new ArrayList<Integer>();
        String text = "";

        for (int i = 0; i < formattedPath.length; i++) {
            String currentChar = formattedPath[i];

            if (currentChar.equals("%") && i == -3) {
                text += getCharacter(i, currentChar, formattedPath);
                break;
            } else if (currentChar.equals("%")) {
                text += getCharacter(i, currentChar, formattedPath);
                voidChar.add(i + 1);
                voidChar.add(i + 2);
            } else if (!voidChar.contains(i)) {
                text += currentChar;
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

    private boolean hasParametersQuery(String path) {
        return (path.startsWith("/parameters?"));
    }

    private String[] formatPath(String path) {
        return removeQuery(path).replace("=", " = ").replace("&", "\n").split("");
    }

    private String getCharacter(int index, String currentChar, String[] path) {
        String secondChar = path[index + 1];
        String thirdChar = path[index + 2];
        String code = currentChar + secondChar + thirdChar;

        return encodedCharacters.get(code);
    }

    private String removeQuery(String path) {
        String pathWithoutQuery = "";

        if (path.startsWith("/") && path.contains("?")) {
            int endChar = path.indexOf("?");
            String substring = path.substring(0, endChar + 1);
            pathWithoutQuery = path.replace(substring, "");
        }

        return pathWithoutQuery;
    }
}
