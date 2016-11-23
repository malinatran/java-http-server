package com.malinatran.routing;

import com.malinatran.utility.RequestLogger;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.resource.Directory;
import com.malinatran.response.Response;

public class CreateOrUpdateAction implements Action {

    public void run(Request request, Response response) {
        char[] body = request.getBody();
        String filePath = request.getAbsolutePath();

        if (Directory.existsInDirectory(filePath)) {
            response.setStatus(Status.METHOD_NOT_ALLOWED);
        } else if (body != null) {
            response.setStatus(Status.OK);
        } else {
            response.setStatus(Status.NOT_FOUND);
        }
    }

    public void run(Response response, RequestLogger logger) {}

    public void run(Request request, Response response, RequestLogger logger) {}
}