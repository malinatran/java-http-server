package com.malinatran.router;

import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class NotFoundOrAllowedRouterCallback implements RouterCallback {

    public void run(Request request, Response response) {
        String method = request.getMethod();

        if (method.equals(Method.HEAD) || method.equals(Method.GET)) {
            response.setStatus(Status.NOT_FOUND);
        } else {
            response.setStatus(Status.METHOD_NOT_ALLOWED);
        }
    }
}
