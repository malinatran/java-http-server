package com.malinatran.router;

import com.malinatran.constant.Method;
import com.malinatran.constant.Status;
import com.malinatran.request.Request;
import com.malinatran.request.RequestLogger;
import com.malinatran.response.Response;

import java.io.IOException;

public class EasterEggRouterCallback implements RouterCallback {

    private static final String TEAPOT_MESSAGE = "I'm a teapot";
    private static final String COFFEE = "/coffee";
    private static final String TEA = "/tea";

    public void run(Request request, Response response) {
        String path = request.getPath();
        String method = request.getMethod();

        if (method.equals(Method.GET) && path.equals(COFFEE)) {
            response.setStatus(Status.TEAPOT);
            response.setBodyContent(TEAPOT_MESSAGE);
        } else if (method.equals(Method.GET) && path.equals(TEA)) {
            response.setStatus(Status.OK);
        }
    }

    public void run(Response response, RequestLogger logger) throws IOException {}
    public void run(Request request, Response response, RequestLogger logger) throws IOException {}
}
