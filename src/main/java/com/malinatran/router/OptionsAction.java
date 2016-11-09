package com.malinatran.router;

import com.malinatran.utility.Header;
import com.malinatran.utility.Method;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class OptionsAction implements Action {

    private static final String METHOD_OPTIONS_PATH = "/method_options";

    public void run(Request request, Response response) {
        String method = request.getMethod();
        String path = request.getPath();
        response.setStatus(Status.OK);

        if (method.equals(Method.OPTIONS) && path.equals(METHOD_OPTIONS_PATH)) {
            response.setHeader(Header.ALLOW, Header.ALL_METHODS);
        } else {
            response.setHeader(Header.ALLOW, Header.SOME_METHODS);
        }
    }
}