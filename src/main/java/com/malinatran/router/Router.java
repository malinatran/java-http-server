package com.malinatran.router;

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

import static com.malinatran.constant.Method.PATCH;
import static com.malinatran.constant.Method.DELETE;
import static com.malinatran.constant.Method.PUT;
import static com.malinatran.constant.Method.POST;

public class Router {

    private RouterValidator validator;
    private Map<String, RouterCallback> routes;
    private RouterCallback callback;
    private Request request;
    private Response response;
    private RequestLogger logger;
    private LoggedRouterCallback loggedRouterCallback;
    private Directory directory;

    public Router() {
        routes = new HashMap<String, RouterCallback>();
        validator = new RouterValidator();
        loggedRouterCallback = new LoggedRouterCallback();
        directory = new Directory();
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

    public boolean hasRoute(String route) {
        return routes.containsKey(route);
    }

    private void decodeParameter() {
        String decoded = ParameterDecoder.decodeText(request.getPath());
        response.setBodyContent(decoded);
    }

    private void logRequest() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String method = request.getMethod();
        String path = request.getPath();
        logger.addRequestLine(request);

        if (isMethod(method, PATCH)) {
            String eTag = request.getHeaderValue(Header.IF_MATCH);
            char[] data = request.getBody();
            logger.setETagAndBody(eTag, data);
        } else if (isPutOrPostToForm(method, path)) {
            char[] data = request.getBody();
            logger.setBody(data);
        } else if (isDeleteToForm(method, path)) {
            logger.setBody(new char[0]);
        }
    }

    private boolean isPutOrPostToForm(String method, String path) {
        return (isMethod(method, POST) || isMethod(method, PUT) && path.equals("/form"));
    }

    private boolean isDeleteToForm(String method, String path) {
        return (isMethod(method, DELETE) && path.equals("/form"));
    }

    private RouterCallback setCallback() throws IOException, NoSuchAlgorithmException {
        String route = request.getRoute();
        String method = request.getMethod();

        if (validator.isValidRouteAndCredentials(request)) {
            loggedRouterCallback.run(response, logger);
            callback = null;
        } else if (isRequestForPatchedContent() || isRequestForBody()) {
            loggedRouterCallback.run(request, response, logger);
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

    private boolean isRequestForBody() {
        String method = request.getMethod();
        String path = request.getPath();

        return (isMethod(method, Method.GET) && path.equals("/form"));
    }

    private boolean isRequestForPatchedContent() {
        String method = request.getMethod();
        String filePath = request.getFilePath();

        return (isMethod(method, Method.GET) &&
                logger.hasBody() &&
                directory.isTextFile(filePath) &&
                directory.existsInDirectory(filePath));
    }

    private boolean isMethod(String text, String methodType) {
        return text.equals(methodType);
    }
}