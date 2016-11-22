package com.malinatran.routing;

import com.malinatran.request.Method;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class EasterEggAction implements Action {

    private static final String TEAPOT_MESSAGE = "I'm a teapot";
    private static final String COFFEE_PATH = "/coffee";
    private static final String TEA_PATH = "/tea";

    public void run(Request request, Response response) {
        String path = request.getPath();
        String method = request.getMethod();

        if (method.equals(Method.GET) && path.equals(COFFEE_PATH)) {
            response.setStatus(Status.TEAPOT);
            response.setBodyContent(TEAPOT_MESSAGE);
        } else if (method.equals(Method.GET) && path.equals(TEA_PATH)) {
            response.setStatus(Status.OK);
        }
    }
}