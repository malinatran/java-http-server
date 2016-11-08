package com.malinatran.router;

import com.malinatran.utility.Status;
import com.malinatran.request.RequestLogger;
import com.malinatran.resource.Directory;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import java.io.IOException;

public class IndexRouterCallback implements RouterCallback {

    public void run(Request request, Response response) throws IOException {
        String path = request.getPath();
        String directoryPath = request.getDirectoryPath();

        if (path.equals("/")) {
            response.setStatus(Status.OK);
            response.setBodyContent(Directory.getLinks(directoryPath));
        }
    }

    public void run(Response response, RequestLogger logger) throws IOException {}
    public void run(Request request, Response response, RequestLogger logger) throws IOException {}
}