package com.malinatran.router;

import com.malinatran.constant.Header;
import com.malinatran.constant.Method;
import com.malinatran.constant.Status;
import com.malinatran.utility.ParameterDecoder;
import com.malinatran.request.Request;
import com.malinatran.resource.Directory;
import com.malinatran.resource.TextFile;
import com.malinatran.response.ResourceHandler;
import com.malinatran.response.Response;
import com.malinatran.request.RequestLogger;
import com.malinatran.utility.SHA1Encoder;

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
    private TextFile textFile;
    private ResourceHandler resourceHandler;

    public Router() {
        routes = new HashMap<String, RouterCallback>();
        validator = new RouterValidator();
        decoder = new ParameterDecoder();
        directory = new Directory();
        textFile = new TextFile();
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
            response.setLogsToBody(logger);
            callback = null;
        } else if (isModifiable()) {
            modifyExistingResource();
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

    private void modifyExistingResource() throws IOException, NoSuchAlgorithmException {
        Map<String, Integer> ranges = request.getRangeValues();
        String content = getOriginalOrPatchedContent();

        if (!ranges.isEmpty()) {
            resourceHandler.setText(content, ranges);
        } else {
            resourceHandler.setText(content);
        }
    }

    private String getOriginalOrPatchedContent() throws IOException, NoSuchAlgorithmException {
        String filePath = request.getFilePath();
        Map<String, Integer> ranges = request.getRangeValues();
        String originalContent = textFile.readPartialTextFile(filePath, ranges);
        String encodedContent = SHA1Encoder.convert(originalContent);

        if (encodedContent.equals(logger.getETag())) {
            return logger.getPatchedContent();
        } else {
            return originalContent;
        }
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
