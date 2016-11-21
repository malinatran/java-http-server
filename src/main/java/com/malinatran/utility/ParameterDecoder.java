package com.malinatran.utility;

import java.util.*;

public class ParameterDecoder {

    private static Map<String, String> encodedCharacters = Mapping.getEncodedCharacters();

    public static String decode(String path) {
        String decodedText = "";

        if (hasParametersQuery(path)) {
            decodedText += getDecodedText(path);
        }

        return decodedText;
    }

    private static String getDecodedText(String path) {
        String[] formattedPath = formatPath(path);
        List<Integer> indicesToRemove = new Vector<Integer>();
        String text = "";

        for (int i = 0; i < formattedPath.length; i++) {
            String current = formattedPath[i];

            if (isLastEncoding(current, i)) {
                text += getCharacter(i, current, formattedPath);
                break;
            } else if (isEncoding(current)) {
                text += getCharacter(i, current, formattedPath);
                indicesToRemove.add(i + 1);
                indicesToRemove.add(i + 2);
            } else if (!indicesToRemove.contains(i)) {
                text += current;
            }
        }

        return text + "\n";
    }

    private static String[] formatPath(String path) {
        String[] formattedPath = path.replace("/parameters?", "").replace("=", " = ").replace("&", "\n").split("");

        return formattedPath;
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
}