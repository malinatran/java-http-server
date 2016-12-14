package com.malinatran.mocks;

import com.malinatran.utility.Status;
import com.malinatran.routing.Action;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.utility.RequestLogger;
import com.malinatran.routing.Router;

import java.util.Map;

public class MockRouter extends Router {

    private Map<String, Action> routes;

    public MockRouter(Map<String, Action> routes) {
        super(routes);
        this.routes = routes;
    }

    @Override
    public Response getResponse(Request request, RequestLogger requestLogger) {
        Response response = new Response("HTTP/1.1");
        response.setStatus(Status.OK);

        return response;
    }
}