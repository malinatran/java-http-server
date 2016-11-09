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
import java.util.HashMap;
import java.util.Map;

public class Router {

    private Authorizer authorizer;
    private Map<String, RouterCallback> routes;
    private RouterCallback callback;
    private RouterCallback loggedRouterCallback;
    private Request request;
    private Response response;
    private RequestLogger logger;

    public Router() {
        routes = new HashMap<String, RouterCallback>();
        authorizer = new Authorizer();
        loggedRouterCallback = new LoggedRouterCallback();
    }

    public Map<String, RouterCallback> addRoute(String method, String path, RouterCallback callback) {
        routes.put(method + " " + path, callback);

        return routes;
    }

    public Response getResponse(Request request, RequestLogger logger) throws IOException, NoSuchAlgorithmException {
        this.request = request;
        this.response = new Response(request.getProtocolAndVersion());
        this.logger = logger;
        decodeParameter();
        logger.logRequest(request);
        runCallback(getCallback());

        return response;
    }

    public boolean hasRoute(String route) {
        return routes.containsKey(route);
    }

    private Router decodeParameter() {
        String decoded = ParameterDecoder.decode(request.getPath());
        response.setBodyContent(decoded);

        return this;
    }

    private boolean hasBasicAuth() {
        return authorizer.hasValidRouteAndCredentials(request);
    }

    private Router runCallback(RouterCallback callback) throws IOException {
        if (callback != null) {
            callback.run(request, response);
        }

        return this;
    }

    private RouterCallback getCallback() throws IOException, NoSuchAlgorithmException {
        String route = request.getRoute();
        String method = request.getMethod();
        callback = null;

        if (hasBasicAuth()) {
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