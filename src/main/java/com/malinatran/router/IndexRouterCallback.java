package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class IndexRouterCallback implements RouterCallback {

    public void run(Request request, Response response) {
       String path = request.getPath();

        if (path.equals("/foobar")) {
            response.setStatus(Status.NOT_FOUND);
        } else {
            response.setStatus(Status.OK);
        }
    }
}
