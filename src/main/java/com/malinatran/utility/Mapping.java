package com.malinatran.utility;

import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.routing.Action;
import com.malinatran.routing.FormAction;
import com.malinatran.routing.OptionsAction;
import com.malinatran.routing.RedirectAction;
import com.malinatran.routing.AuthorizedAction;
import com.malinatran.routing.EasterEggAction;
import com.malinatran.routing.FileContentAction;
import com.malinatran.routing.IndexAction;
import com.malinatran.routing.CreateOrUpdateAction;
import com.malinatran.routing.NotFoundOrAllowedAction;

import java.util.Hashtable;
import java.util.Map;

import static com.malinatran.request.Method.*;

public class Mapping {

    private static Map<String, String> encodedCharacters;
    private static Map<String, Action> routes;

    public static Map<String, String> getEncodedCharacters() {
        encodedCharacters = new Hashtable<String, String>();

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
        routes = new Hashtable<String, Action>();

        addRoute(GET, "/form", new FormAction());
        addRoute(POST, "/form", new FormAction());
        addRoute(PUT, "/form", new FormAction());
        addRoute(DELETE, "/form", new FormAction());
        addRoute(GET, "/method_options", new OptionsAction());
        addRoute(POST, "/method_options", new OptionsAction());
        addRoute(PUT, "/method_options", new OptionsAction());
        addRoute(HEAD, "/method_options", new OptionsAction());
        addRoute(OPTIONS, "/method_options", new OptionsAction());
        addRoute(OPTIONS, "/method_options2", new OptionsAction());
        addRoute(GET, "/redirect", new RedirectAction());
        addRoute(GET, "/logs", new AuthorizedAction());
        addRoute(GET, "/coffee", new EasterEggAction());
        addRoute(GET, "/tea", new EasterEggAction());
        addRoute(PATCH, "*", new FileContentAction());
        addRoute(GET, "/", new IndexAction());
        addRoute(GET, "*", new FileContentAction());
        addRoute(PUT, "*", new CreateOrUpdateAction());
        addRoute(POST, "*", new CreateOrUpdateAction());
        addRoute(HEAD, "/", new IndexAction());
        addRoute(HEAD, "*", new NotFoundOrAllowedAction());

        return routes;
    }

    public static Map<String, Action> addRoute(String method, String path, Action action) {
        String key = String.format("%s %s", method, path);
        routes.put(key, action);

        return routes;
    }
}