package com.malinatran.router;

import com.malinatran.constant.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import java.io.IOException;

public class FormRouterCallback implements RouterCallback {

    public void run(Request request, Response response) throws IOException {
        response.setStatus(Status.OK);
    }
}
