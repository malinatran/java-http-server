package com.malinatran.routing;

import com.malinatran.request.Method;
import com.malinatran.utility.RequestLogger;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class NotFoundOrAllowedAction extends Action {

    public void run(Request request, Response response, RequestLogger logger) {
        String method = request.getMethod();

        if (method.equals(Method.HEAD) || method.equals(Method.GET)) {
            response.setStatus(Status.NOT_FOUND);
        } else {
            response.setStatus(Status.METHOD_NOT_ALLOWED);
        }
    }
}