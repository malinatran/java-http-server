package com.malinatran.router;

import com.malinatran.constants.Header;
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

    private RouterValidator validator;
    private ParameterDecoder decoder;
    private Map<String, RouterCallback> routes;
    private RouterCallback callback;
    private Request request;
    private Response response;
    private RequestLogger logger;

    public Router() {
        routes = new HashMap<String, RouterCallback>();
        validator = new RouterValidator();
        decoder = new ParameterDecoder();
    }

    public boolean hasRoute(String route) {
        return routes.containsKey(route);
    }

    public void addRoute(String method, String path, RouterCallback callback) {
        routes.put(method + " " + path, callback);
    }

    public Response getResponse(Request request, RequestLogger logger) throws IOException {
        this.request = request;
        this.response = new Response(request.getProtocolAndVersion());
        this.logger = logger;
        decodeParameter();
        logRequest();
        setAndRunCallback();

        return response;
    }

    private void decodeParameter() {
        String decoded = decoder.decodeText(request.getPath());
        response.setBodyContent(decoded);
    }

    private void logRequest() {
        String method = request.getMethod();
        logger.addRequestLine(request);

        if (method.equals(Method.PATCH)) {
            String eTag = request.getHeaderValue(Header.IF_MATCH);
            char[] data = request.getBody();
            logger.addData(eTag, data);
        }
    }

    private void setAndRunCallback() throws IOException {
        RouterCallback callback = setCallback();
        runCallback(callback);
    }

    private RouterCallback setCallback () {
        String route = request.getRoute();
        String method = request.getMethod();

        if (validator.isValidRouteAndCredentials(request)) {
            response.setLogsToBody(logger);
            callback = null;
        } else if (modifyExistingResource()) {
            response.setText(logger.getData());
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

    private void runCallback(RouterCallback callback) throws IOException {
        if (callback != null) {
            callback.run(request, response);
        }
    }

    private boolean modifyExistingResource() {
        String method = request.getMethod();
        String filePath = request.getFilePath();

        return (isGetMethod(method) && hasData(logger) && exists(filePath));
    }

    private boolean isGetMethod(String method) {
       return method.equals(Method.GET);
    }

    private boolean hasData(RequestLogger logger) {
        return logger.hasData();
    }

    private boolean exists(String filePath) {
        Directory directory = new Directory();

        return directory.existsInDirectory(filePath);
    }
}
