package com.malinatran.router;

import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.resource.Directory;
import com.malinatran.response.Response;
import com.malinatran.request.RequestLogger;

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

    public Response getResponse(Request request, RequestLogger logger) throws IOException {
        Response response = new Response(request.getProtocolAndVersion());
        String method = request.getMethod();

        ParameterDecoder decoder = new ParameterDecoder();
        String decoded = decoder.decodeText(request.getPath());
        response.setBodyContent(decoded);

        logger.addRequestLine(request);

        if (method.equals(Method.PATCH)) {
            String eTag = request.getHeaderValue("If-Match");
            char[] data = request.getBody();
            logger.addData(eTag, data);
        }

        RouterCallback callback = setCallback(request, response, logger);
        runCallback(request, response, callback);

        return response;
    }

    private RouterCallback setCallback (Request request, Response response, RequestLogger logger) {
        String route = request.getRoute();
        String path = request.getCleanPath();
        String method = request.getMethod();
        String directoryPath = request.getDirectoryPath();

        RouterValidator validator = new RouterValidator();
        RouterCallback callback = null;

        if (validator.isValidRouteAndCredentials(request)) {
            response.setLogsToBody(logger);
            callback = null;
        } else if (isRequestToExistingResource(method, logger, directoryPath, path)) {
            response.setText(String.valueOf(logger.getData()));
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

    private Boolean isRequestToExistingResource(String method, RequestLogger logger, String directoryPath, String path) {
        Directory directory = new Directory();
        return (method.equals(Method.GET) && logger.hasData() && directory.existsInDirectory(directoryPath, path));
    }

    private void runCallback(Request request, Response response, RouterCallback callback) throws IOException {
        if (callback != null) {
            callback.run(request, response);
        }
    }
}