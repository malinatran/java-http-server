package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, RouterCallback> routes;

    public Router() {
        routes = new HashMap<String, RouterCallback>();
    }

    public Boolean hasRoute(String route) {
        return routes.containsKey(route);
    }

    public void addRoute(String method, String path, RouterCallback callback) {
        routes.put(method + " " + path, callback);
    }

    public Response getResponse(Request request, Logger logger) throws IOException {
        Response response = new Response(request.getProtocolAndVersion());
        ParameterDecoder decoder = new ParameterDecoder();
        String decoded = decoder.decodeText(request.getPath());

        logger.addRequestLine(request);
        response.setBodyContent(decoded);
        RouterCallback callback = setCallback(request, response, logger);
        runCallback(request, response, callback);

        return response;
    }

    private RouterCallback setCallback (Request request, Response response, Logger logger) {
        String route = getRoute(request);
        String method = request.getMethod();
        RouterValidator validator = new RouterValidator();
        RouterCallback callback = null;

        if (validator.isValidRouteAndCredentials(request)) {
            response.setLogsToBody(logger);
            callback = null;
        } else if (hasRoute(route)) {
            callback = routes.get(route);
        } else if (hasRoute(method + " *")) {
            callback = routes.get(method + " *");
        } else {
            response.setStatus(Status.METHOD_NOT_ALLOWED);
        }

        return callback;
    }

    private void runCallback(Request request, Response response, RouterCallback callback) throws IOException {
        if (callback != null) {
            callback.run(request, response);
        }
    }

    private String getRoute(Request request) {
        return request.getMethod() + " " + request.getPath();
    }
}