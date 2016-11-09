package com.malinatran.router;

import com.malinatran.utility.Authorizer;
import com.malinatran.utility.Status;
import com.malinatran.request.MethodReader;
import com.malinatran.utility.ParameterDecoder;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.request.RequestLogger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.Map;

import static com.malinatran.utility.Method.*;

public class Router {

    private Map<String, RouterCallback> routes;
    private RouterCallback callback;
    private RouterCallback loggedRouterCallback;

    public Router() {
        routes = new Hashtable<String, RouterCallback>();
        loggedRouterCallback = new LoggedRouterCallback();
    }

    public Response getResponse(Request request, RequestLogger logger) throws IOException, NoSuchAlgorithmException {
        Response response = new Response(request.getProtocolAndVersion());
        decodeParameter(request, response);
        logger.logRequest(request);
        runCallback(request, response, logger);

        return response;
    }

    public boolean hasRoute(String route) {
        return routes.containsKey(route);
    }

    private Map<String, RouterCallback> addRoute(String method, String path, RouterCallback callback) {
        routes.put(method + " " + path, callback);

        return routes;
    }

    private Router decodeParameter(Request request, Response response) {
        String decoded = ParameterDecoder.decode(request.getPath());
        response.setBodyContent(decoded);

        return this;
    }

    private boolean hasBasicAuth(Request request) {
        return Authorizer.hasValidRouteAndCredentials(request);
    }

    private Router runCallback(Request request, Response response, RequestLogger logger) throws IOException, NoSuchAlgorithmException {
        RouterCallback callback = getCallback(request, response, logger);
        if (callback != null) {
            callback.run(request, response);
        }

        return this;
    }

    private RouterCallback getCallback(Request request, Response response, RequestLogger logger) throws IOException, NoSuchAlgorithmException {
        String route = request.getRoute();
        String method = request.getMethod();
        callback = null;

        if (hasBasicAuth(request)) {
            loggedRouterCallback.run(response, logger);
        } else if (MethodReader.isGetRequestWithLoggedBody(request, logger)) {
            loggedRouterCallback.run(request, response, logger);
        } else if (hasRoute(route)) {
            callback = routes.get(route);
        } else if (hasRoute(method + " *")) {
            callback = routes.get(method + " *");
        } else {
            response.setStatus(Status.METHOD_NOT_ALLOWED);
        }

        return callback;
    }

    public void setupRoutes() {
        addRoute(GET, "/form", new FormRouterCallback());
        addRoute(POST, "/form", new FormRouterCallback());
        addRoute(PUT, "/form", new FormRouterCallback());
        addRoute(DELETE, "/form", new FormRouterCallback());
        addRoute(GET, "/method_options", new OptionsRouterCallback());
        addRoute(POST, "/method_options", new OptionsRouterCallback());
        addRoute(PUT, "/method_options", new OptionsRouterCallback());
        addRoute(HEAD, "/method_options", new OptionsRouterCallback());
        addRoute(OPTIONS, "/method_options", new OptionsRouterCallback());
        addRoute(OPTIONS, "/method_options2", new OptionsRouterCallback());
        addRoute(GET, "/redirect", new RedirectRouterCallback());
        addRoute(GET, "/logs", new AuthorizedRouterCallback());
        addRoute(GET, "/coffee", new EasterEggRouterCallback());
        addRoute(GET, "/tea", new EasterEggRouterCallback());
        addRoute(PATCH, "*", new FileContentRouterCallback());
        addRoute(GET, "/", new IndexRouterCallback());
        addRoute(GET, "*", new FileContentRouterCallback());
        addRoute(PUT, "*", new CreateOrUpdateRouterCallback());
        addRoute(POST, "*", new CreateOrUpdateRouterCallback());
        addRoute(HEAD, "/", new IndexRouterCallback());
        addRoute(HEAD, "*", new NotFoundOrAllowedRouterCallback());
    }
}