package com.malinatran.utility;

import com.malinatran.routing.*;

import java.util.Hashtable;
import java.util.Map;

import static com.malinatran.utility.Method.GET;
import static com.malinatran.utility.Method.POST;
import static com.malinatran.utility.Method.PUT;
import static com.malinatran.utility.Method.DELETE;
import static com.malinatran.utility.Method.OPTIONS;
import static com.malinatran.utility.Method.HEAD;
import static com.malinatran.utility.Method.PATCH;

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

    public static Map<String, Action> getRoutes() {
        Map<String, Action> routes = new Hashtable<String, Action>();
        routes.put(GET + "/form", new FormAction());
        routes.put(POST + "/form", new FormAction());
        routes.put(PUT + "/form", new FormAction());
        routes.put(DELETE + "/form", new FormAction());
        routes.put(GET + "/method_options", new OptionsAction());
        routes.put(POST + "/method_options", new OptionsAction());
        routes.put(PUT + "/method_options", new OptionsAction());
        routes.put(HEAD + "/method_options", new OptionsAction());
        routes.put(OPTIONS + "/method_options", new OptionsAction());
        routes.put(OPTIONS + "/method_options2", new OptionsAction());
        routes.put(GET + "/redirect", new RedirectAction());
        routes.put(GET + "/logs", new AuthorizedAction());
        routes.put(GET + "/coffee", new EasterEggAction());
        routes.put(GET + "/tea", new EasterEggAction());
        routes.put(PATCH + "*", new FileContentAction());
        routes.put(GET + "/", new IndexAction());
        routes.put(GET + "*", new FileContentAction());
        routes.put(PUT + "*", new CreateOrUpdateAction());
        routes.put(POST + "*", new CreateOrUpdateAction());
        routes.put(HEAD + "/", new IndexAction());
        routes.put(HEAD + "*", new NotFoundOrAllowedAction());

        return routes;
    }
}