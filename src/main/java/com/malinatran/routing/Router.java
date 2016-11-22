package com.malinatran.routing;

import com.malinatran.request.MethodTypeReader;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.utility.Mapping;
import com.malinatran.utility.RequestLogger;
import com.malinatran.utility.ParameterDecoder;
import com.malinatran.utility.Authorizer;
import com.malinatran.utility.Status;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class Router {

    private Map<String, Action> routes;
    private Action action;
    private LoggedAction loggedAction;

    public Router() {
        loggedAction = new LoggedAction();
        routes = Mapping.getRoutes();
    }

    public Response getResponse(Request request, RequestLogger logger) throws IOException, NoSuchAlgorithmException {
        Response response = new Response(request.getProtocolAndVersion());
        decodeParameter(request, response);
        logger.logRequest(request);
        runAction(request, response, logger);

        return response;
    }

    private Router decodeParameter(Request request, Response response) {
        String decoded = ParameterDecoder.decode(request.getPath());
        response.setBodyContent(decoded);

        return this;
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
        String path = request.getPath();
        String credentials = request.getHeaderValue(Header.AUTHORIZATION);
        action = null;

        if (hasBasicAuth(method, path, credentials)) {
            loggedAction.run(response, logger);
        } else if (hasLoggedBody(request, logger)) {
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

    private boolean hasBasicAuth(String method, String path, String credentials) {
        return Authorizer.hasValidRouteAndCredentials(method, path, credentials);
    }

    private boolean hasLoggedBody(Request request, RequestLogger logger) {
        return MethodTypeReader.isGetRequestWithLoggedBody(request, logger);
    }

    private boolean hasRoute(String route) {
        return routes.containsKey(route);
    }
}