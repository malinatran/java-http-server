package com.malinatran.router;

import com.malinatran.constant.Method;
import com.malinatran.constant.Status;
import com.malinatran.request.Request;
import com.malinatran.resource.Directory;
import com.malinatran.response.ResourceHandler;
import com.malinatran.response.Response;

import java.io.IOException;

public class FileContentRouterCallback implements RouterCallback {

    private Directory directory = new Directory();
    private ResourceHandler resourceHandler = new ResourceHandler();
    private Response response;
    private Request request;

    public void run(Request request, Response response) throws IOException {
        this.request = request;
        this.response = response;
        String filePath = request.getFilePath();
        boolean exists = directory.existsInDirectory(filePath);

        if (exists) {
            setResponse();
        } else {
            response.setStatus(Status.NOT_FOUND);
        }
    }

    private void setResponse() throws IOException {
        String method = request.getMethod();

        if (method.equals(Method.PATCH)) {
            response.setStatus(Status.NO_CONTENT);
        } else {
            resourceHandler.read(request, response);
        }
    }
}