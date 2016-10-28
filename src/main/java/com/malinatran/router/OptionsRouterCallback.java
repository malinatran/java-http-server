package com.malinatran.router;

import com.malinatran.constants.Header;
import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class OptionsRouterCallback implements RouterCallback {

    private static final String ALL_METHODS = "GET,HEAD,POST,OPTIONS,PUT";
    private static final String SOME_METHODS = "GET,OPTIONS";
    private static final String METHOD_OPTIONS_PATH = "/method_options";

    public void run(Request request, Response response) {
        String method = request.getMethod();
        String path = request.getPath();

        response.setStatus(Status.OK);

        if (method.equals(Method.OPTIONS) && path.equals(METHOD_OPTIONS_PATH)) {
            response.setHeader(Header.ALLOW, ALL_METHODS);
        } else {
            response.setHeader(Header.ALLOW, SOME_METHODS);
        }
    }
}