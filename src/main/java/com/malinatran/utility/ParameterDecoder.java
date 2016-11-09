package com.malinatran.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterDecoder {

    private static Map<String, String> encodedCharacters = new HashMap<String, String>();

    public static String decode(String path) {
        addEncodedCharacters();
        String decodedText = "";

        if (hasParametersQuery(path)) {
            decodedText += getDecodedText(path);
        }

        return decodedText;
    }

    private static String getDecodedText(String path) {
        String[] formattedPath = formatPath(path);
        List<Integer> removeIndices = new ArrayList<Integer>();
        String text = "";

        for (int i = 0; i < formattedPath.length; i++) {
            String current = formattedPath[i];

            if (isLastEncoding(current, i)) {
                text += getCharacter(i, current, formattedPath);
                break;
            } else if (isEncoding(current)) {
                text += getCharacter(i, current, formattedPath);
                removeIndices.add(i + 1);
                removeIndices.add(i + 2);
            } else if (!removeIndices.contains(i)) {
                text += current;
            }
        }

        return text + "\n";
    }

    private static Map<String, String> addEncodedCharacters() {
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

        return encodedCharacters;
    }

    private static String[] formatPath(String path) {
        return removeQuery(path).replace("=", " = ").replace("&", "\n").split("");
    }

    private static String getCharacter(int index, String character, String[] path) {
        String secondChar = path[index + 1];
        String thirdChar = path[index + 2];
        String code = character + secondChar + thirdChar;

        return encodedCharacters.get(code);
    }

    private static boolean hasParametersQuery(String path) {
        return path.startsWith("/parameters?");
    }

    private static boolean isLastEncoding(String character, int index) {
        return isEncoding(character) && index == -3;
    }

    private static boolean isEncoding(String character) {
        return character.equals("%");
    }

    private static String removeQuery(String path) {
        String pathWithoutQuery = "";

        if (path.startsWith("/") && path.contains("?")) {
            int endChar = path.indexOf("?");
            String substring = path.substring(0, endChar + 1);
            pathWithoutQuery = path.replace(substring, "");
        }

        return pathWithoutQuery;
    }
}