package com.malinatran.router;

import com.malinatran.action.LoggedAction;
import com.malinatran.action.PatchAction;
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
    private LoggedAction loggedAction;
    private PatchAction patchAction;
    private Directory directory;

    public Router() {
        routes = new HashMap<String, RouterCallback>();
        validator = new RouterValidator();
        decoder = new ParameterDecoder();
        loggedAction = new LoggedAction();
        patchAction = new PatchAction();
        directory = new Directory();
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

        if (isMethod(method, Method.PATCH)) {
            String eTag = request.getHeaderValue(Header.IF_MATCH);
            char[] data = request.getBody();
            logger.addETagAndPatchedContent(eTag, data);
        }
    }

    private RouterCallback setCallback() throws IOException, NoSuchAlgorithmException {
        String route = request.getRoute();
        String method = request.getMethod();

        if (validator.isValidRouteAndCredentials(request)) {
            loggedAction.setLogs(response, logger);
            callback = null;
        } else if (isRequestForPatchedContent()) {
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

    private boolean isRequestForPatchedContent() {
        String method = request.getMethod();
        String filePath = request.getFilePath();

        return (isMethod(method, Method.GET) &&
                logger.hasPatchedContent() &&
                request.isTextFile(filePath) &&
                directory.existsInDirectory(filePath));
    }

    private boolean isMethod(String text, String methodType) {
        return text.equals(methodType);
    }
}