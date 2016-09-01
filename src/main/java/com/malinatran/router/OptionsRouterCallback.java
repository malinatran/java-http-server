package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class OptionsRouterCallback extends RouterCallback {

    @Override
    public void run(Request request, Response response) {
        String method = request.getMethod();
        String path = request.getPath();

        response.setStatus(Status.OK);

        if (method.equals("OPTIONS") && path.equals("/method_options")) {
            response.setHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        } else {
            response.setHeader("Allow", "GET,OPTIONS");
        }
    }
}
