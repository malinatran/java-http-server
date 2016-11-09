package com.malinatran.router;

import com.malinatran.utility.Method;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class NotFoundOrAllowedAction implements Action {

    public void run(Request request, Response response) {
        String method = request.getMethod();

        if (method.equals(Method.HEAD) || method.equals(Method.GET)) {
            response.setStatus(Status.NOT_FOUND);
        } else {
            response.setStatus(Status.METHOD_NOT_ALLOWED);
        }
    }
}