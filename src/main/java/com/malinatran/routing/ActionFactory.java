package com.malinatran.routing;

import com.malinatran.request.MethodTypeReader;
import com.malinatran.request.Request;
import com.malinatran.utility.Authorizer;
import com.malinatran.utility.RequestLogger;

import java.io.IOException;

import java.util.Map;

public class ActionFactory {

    private Request request;
    private RequestLogger logger;
    private Map<String, Action> routes;

    public ActionFactory(Request request, RequestLogger logger, Map<String, Action> routes) {
        this.request = request;
        this.logger = logger;
        this.routes = routes;
    }

    public Action getAction() throws IOException {
        String route = request.getRoute();
        String method = request.getMethod();
        Action action = null;

        if (hasBasicAuth(request)) {
            action = new LoggedAction();
        } else if (hasLoggedBody(request, logger)) {
            action = new LoggedAction();
        } else if (hasRoute(route)) {
            action = routes.get(route);
        } else if (hasRoute(method + " *")) {
            action = routes.get(method + " *");
        }

        return action;
    }

    public boolean hasBasicAuth(Request request) {
        String method = request.getMethod();
        String path = request.getPath();
        String credentials = request.getHeaderValue(Header.AUTHORIZATION);

        return Authorizer.hasValidRouteAndCredentials(method, path, credentials);
    }

    public boolean hasLoggedBody(Request request, RequestLogger logger) {
        return MethodTypeReader.isGetRequestWithLoggedBody(request, logger);
    }

    private boolean hasRoute(String route) {
        return routes.containsKey(route);
    }
}
