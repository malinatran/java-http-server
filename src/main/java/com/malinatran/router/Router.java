package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, RouterCallback> routes;

    public Router() {
        routes = new HashMap<String, RouterCallback>();
    }

    public void addRoute(String method, String path, RouterCallback callback) {
        routes.put(method + " " + path, callback);
    }

    public Response getResponse(Request request, Logger logger) {
        Response response = new Response(
                request.getMethod(),
                request.getProtocolAndVersion(),
                request.getPath(),
                request.getBody()
        );

        logger.addRequestLine(request);
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
            response.setStatus(Status.NOT_FOUND);
        }

        return callback;
    }

    private void runCallback(Request request, Response response, RouterCallback callback) {
        if (callback != null) {
            callback.run(request, response);
        }
    }

    public Boolean hasRoute(String route) {
        return routes.containsKey(route);
    }

    private String getRoute(Request request) {
        return request.getMethod() + " " + request.getPath();
    }
}