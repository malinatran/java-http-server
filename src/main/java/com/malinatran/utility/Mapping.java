package com.malinatran.utility;

import java.util.Hashtable;
import java.util.Map;

public class Mapping {

    public static Map<String, String> getEncodedCharacters() {
        Map<String,String> encodedCharacters = new Hashtable<String, String>();

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
}