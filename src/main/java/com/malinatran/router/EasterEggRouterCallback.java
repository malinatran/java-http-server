package com.malinatran.router;

import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class EasterEggRouterCallback implements RouterCallback {

    private static final String TEAPOT_MESSAGE = "I'm a teapot";

    public void run(Request request, Response response) {
        String path = request.getPath();
        String method = request.getMethod();

        if (method.equals(Method.GET) && path.equals("/coffee")) {
            response.setStatus(Status.TEAPOT);
            response.setBodyContent(TEAPOT_MESSAGE);
        } else if (method.equals(Method.GET) && path.equals("/tea")) {
            response.setStatus(Status.OK);
        }
    }
}
