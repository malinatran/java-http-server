package com.malinatran.router;

import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class OptionsRouterCallback implements RouterCallback {

    public static final String ALLOW_HEADER = "Allow";
    public static final String ALL_METHODS = "GET,HEAD,POST,OPTIONS,PUT";
    public static final String SOME_METHODS = "GET,OPTIONS";
    public static final String METHOD_OPTIONS_PATH = "/method_options";

    public void run(Request request, Response response) {
        String method = request.getMethod();
        String path = request.getPath();

        response.setStatus(Status.OK);

        if (method.equals(Method.OPTIONS) && path.equals(METHOD_OPTIONS_PATH)) {
            response.setHeader(ALLOW_HEADER, ALL_METHODS);
        } else {
            response.setHeader(ALLOW_HEADER, SOME_METHODS);
        }
    }
}