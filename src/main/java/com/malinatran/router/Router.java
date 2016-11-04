package com.malinatran.router;

import com.malinatran.action.LoggedAction;
import com.malinatran.action.PatchAction;
import com.malinatran.action.ResourceAction;
import com.malinatran.constant.Header;
import com.malinatran.constant.Method;
import com.malinatran.constant.Status;
import com.malinatran.utility.ParameterDecoder;
import com.malinatran.request.Request;
import com.malinatran.resource.Directory;
import com.malinatran.response.Response;
import com.malinatran.request.RequestLogger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
    private Directory directory;
    private ResourceAction resourceAction;
    private LoggedAction loggedAction;
    private PatchAction patchAction;

    public Router() {
        routes = new HashMap<String, RouterCallback>();
        validator = new RouterValidator();
        decoder = new ParameterDecoder();
        directory = new Directory();
        resourceAction = new ResourceAction();
        loggedAction = new LoggedAction();
        patchAction = new PatchAction();
    }

    public boolean hasRoute(String route) {
        return routes.containsKey(route);
    }

    public void addRoute(String method, String path, RouterCallback callback) {
        routes.put(method + " " + path, callback);
    }

    public Response getResponse(Request request, RequestLogger logger) throws IOException, NoSuchAlgorithmException {
        this.request = request;
        this.response = new Response(request.getProtocolAndVersion());
        this.logger = logger;
        decodeParameter();
        logRequest();
        runCallback(setCallback());

        return response;
    }

    private void decodeParameter() {
        String decoded = decoder.decodeText(request.getPath());
        response.setBodyContent(decoded);
    }

    private void logRequest() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String method = request.getMethod();
        logger.addRequestLine(request);

        if (method.equals(Method.PATCH)) {
            String eTag = request.getHeaderValue(Header.IF_MATCH);
            char[] data = request.getBody();
            logger.addData(eTag, data);
        }
    }

    private RouterCallback setCallback() throws IOException, NoSuchAlgorithmException {
        String route = request.getRoute();
        String method = request.getMethod();

        if (validator.isValidRouteAndCredentials(request)) {
            loggedAction.setLogs(response, logger);
            callback = null;
        } else if (isModifiable()) {
            patchAction.setPatchedContent(request, response, logger);
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

    private boolean isModifiable() {
        String method = request.getMethod();
        String filePath = request.getFilePath();
        boolean isTextFile = request.isTextFile(filePath);

        return (isGetMethod(method) && hasData(logger) && exists(filePath) && isTextFile);
    }

    private boolean isGetMethod(String method) {
        return method.equals(Method.GET);
    }

    private boolean hasData(RequestLogger logger) {
        return logger.hasPatchedContent();
    }

    private boolean exists(String filePath) {
        Directory directory = new Directory();

        return directory.existsInDirectory(filePath);
    }
}
