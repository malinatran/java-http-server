package com.malinatran.routing;

import com.malinatran.utility.RequestLogger;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import java.io.IOException;

public class FormAction implements Action {

    public void run(Request request, Response response) throws IOException {
        response.setStatus(Status.OK);
    }

    public void run(Response response, RequestLogger logger) {}

    public void run(Request request, Response response, RequestLogger logger) {}
}