package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class IndexRouterCallback implements RouterCallback {

    public void run(Request request, Response response) {
        response.setStatus(Status.OK);
    }
}
