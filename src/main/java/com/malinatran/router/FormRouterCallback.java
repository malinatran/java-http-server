package com.malinatran.router;

import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.request.RequestLogger;
import com.malinatran.response.Response;

import java.io.IOException;

public class FormRouterCallback implements RouterCallback {

    public void run(Request request, Response response) throws IOException {
        response.setStatus(Status.OK);
    }

    public void run(Response response, RequestLogger logger) throws IOException {}

    public void run(Request request, Response response, RequestLogger logger) throws IOException {}
}