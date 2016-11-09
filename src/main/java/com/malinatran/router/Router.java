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

public class Router {

    private Map<String, RouterCallback> routes;
    private RouterCallback callback;
    private RouterCallback loggedRouterCallback;

    public Router() {
        routes = new Hashtable<String, RouterCallback>();
        loggedRouterCallback = new LoggedRouterCallback();
    }

    public Map<String, RouterCallback> addRoute(String method, String path, RouterCallback callback) {
        routes.put(method + " " + path, callback);

        return routes;
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
}
