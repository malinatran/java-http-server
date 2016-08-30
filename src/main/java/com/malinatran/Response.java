package com.malinatran;

public class Response {

    private static final String ALLOW_ALL_METHODS = "Allow: GET,HEAD,POST,OPTIONS,PUT";
    private static final String ALLOW_SOME_METHODS = "Allow: GET,OPTIONS";

    public String getResponse(Request request) {
        String method = request.getMethod();
        String protocol = request.getProtocolAndVersion();
        String path = request.getPath();

        if (hasOptionsPath(path)) {
            return handleOptions(method, protocol, path);
        } else if (isPutOrPost(method)) {
            return handlePutAndPost(request);
        } else if (isGetOrHead(method) && hasRootPath(path)) {
            return handleOK(protocol);
        } else {
            return handleNotFound(protocol);
        }
    }

    private Boolean hasOptionsPath(String path) {
        return path.equals("/method_options") || path.equals("/method_options2");
    }

    private Boolean isPutOrPost(String method) {
        return method.equals(Method.PUT) || method.equals(Method.POST);
    }

    private Boolean isGetOrHead(String method) {
       return method.equals(Method.GET) || method.equals(Method.HEAD);
    }

    private Boolean hasRootPath(String path) {
        return path.equals("/");
    }

    private String handleOptions(String method, String protocol, String path) {
        String initialLine = protocol + " " + Status.OK;

        if (method.equals(Method.OPTIONS) && path.equals("/method_options")) {
            String[] lines = {initialLine, Formatter.addNewLine(ALLOW_ALL_METHODS)};
            return Formatter.buildResponse(lines);
        } else {
            String [] lines = {initialLine, Formatter.addNewLine(ALLOW_SOME_METHODS)};
            return Formatter.buildResponse(lines);
        }
    }

    private String handlePutAndPost(Request request) {
        String body = request.getBody();
        String protocol = request.getProtocolAndVersion();

        if (body.length() != 0) {
            return Formatter.buildInitialLine(protocol, Status.OK);
        } else {
            return Formatter.buildInitialLine(protocol, Status.NOT_FOUND);
        }
    }

    private String handleOK(String protocol) {
        return Formatter.buildInitialLine(protocol, Status.OK);

    }

    private String handleNotFound(String protocol) {
       return Formatter.buildInitialLine(protocol, Status.NOT_FOUND);
    }


}
