package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, RouterCallback> routes;

    public Router() {
        routes = new HashMap<String, RouterCallback>();
    }

    public void addRoute(String method, String path, RouterCallback callback) {
        routes.put(method + " " + path, callback);
    }

    public Response getResponse(Request request) {
        Response response = new Response(
                request.getMethod(),
                request.getProtocolAndVersion(),
                request.getPath(),
                request.getBody()
        );

        String route = getRoute(request);
        String method = request.getMethod();
        RouterCallback callback = null;

        if (hasRoute(route)) {
            callback = routes.get(route);
        } else if (hasRoute(method + " *")) {
            callback = routes.get(method + " *");
        } else {
            response.setStatus(Status.NOT_FOUND);
        }

        if (callback != null) {
            callback.run(request, response);
        }
        return response;
    }

    public Boolean hasRoute(String route) {
        return routes.containsKey(route);
    }

    private String getRoute(Request request) {
        return request.getMethod() + " " + request.getPath();
    }
}