package com.malinatran.mocks;

import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.utility.RequestLogger;
import com.malinatran.router.Router;

public class MockRouter extends Router {

    @Override
    public Response getResponse(Request request, RequestLogger requestLogger) {
        Response response = new Response("HTTP/1.1");
        response.setStatus(Status.OK);

        return response;
    }
}