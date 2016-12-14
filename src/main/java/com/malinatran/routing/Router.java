package com.malinatran.routing;

import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.utility.RequestLogger;
import com.malinatran.utility.ParameterDecoder;
import com.malinatran.utility.Status;

import java.io.IOException;

import java.util.Map;

public class Router {

    private Map<String, Action> routes;
    private Action action;

    public Router(Map<String, Action> routes) {
        this.routes = routes;
    }

    public Response getResponse(Request request, RequestLogger logger) throws IOException {
        Response response = new Response(request.getProtocolAndVersion());
        decodeParameter(request, response);
        logger.logRequest(request);
        runAction(request, response, logger);

        return response;
    }

    private Router decodeParameter(Request request, Response response) {
        String decoded = ParameterDecoder.decode(request.getPath());
        response.setBodyContent(decoded);

        return this;
    }

    private Router runAction(Request request, Response response, RequestLogger logger) throws IOException {
        ActionFactory factory = new ActionFactory(request, logger, routes);
        action = factory.getAction();

        if (factory.hasBasicAuth(request)) {
            action.run(response, logger);
        } else if (action != null) {
            action.run(request, response, logger);
        } else {
            response.setStatus(Status.METHOD_NOT_ALLOWED);
        }

        return this;
    }
}
