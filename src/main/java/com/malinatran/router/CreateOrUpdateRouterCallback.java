package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class CreateOrUpdateRouterCallback implements RouterCallback {

    public void run(Request request, Response response) {
        String body = request.getBody();

        if (body != null) {
            response.setStatus(Status.OK);
        } else {
            response.setStatus(Status.NOT_FOUND);
        }
    }
}
