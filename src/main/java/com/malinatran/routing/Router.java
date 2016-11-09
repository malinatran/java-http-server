package com.malinatran.routing;

import com.malinatran.utility.Authorizer;
import com.malinatran.utility.Status;
import com.malinatran.request.MethodTypeReader;
import com.malinatran.utility.ParameterDecoder;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.utility.RequestLogger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.Map;

import static com.malinatran.utility.Method.*;

public class Router {

    private Map<String, Action> routes;
    private Action action;
    private LoggedAction loggedAction;

    public Router() {
        routes = new Hashtable<String, Action>();
        loggedAction = new LoggedAction();
    }

    public Response getResponse(Request request, RequestLogger logger) throws IOException, NoSuchAlgorithmException {
        Response response = new Response(request.getProtocolAndVersion());
        decodeParameter(request, response);
        logger.logRequest(request);
        runAction(request, response, logger);

        return response;
    }

    public boolean hasRoute(String route) {
        return routes.containsKey(route);
    }

    private Map<String, Action> addRoute(String method, String path, Action action) {
        routes.put(method + " " + path, action);

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

    private Router runAction(Request request, Response response, RequestLogger logger) throws IOException, NoSuchAlgorithmException {
        Action action = runOrGetCallback(request, response, logger);
        if (action != null) {
            action.run(request, response);
        }

        return this;
    }

    private Action runOrGetCallback(Request request, Response response, RequestLogger logger) throws IOException, NoSuchAlgorithmException {
        String route = request.getRoute();
        String method = request.getMethod();
        action = null;

        if (hasBasicAuth(request)) {
            loggedAction.run(response, logger);
        } else if (MethodTypeReader.isGetRequestWithLoggedBody(request, logger)) {
            loggedAction.run(request, response, logger);
        } else if (hasRoute(route)) {
            action = routes.get(route);
        } else if (hasRoute(method + " *")) {
            action = routes.get(method + " *");
        } else {
            response.setStatus(Status.METHOD_NOT_ALLOWED);
        }

        return action;
    }

    public void setupRoutes() {
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
    }
}