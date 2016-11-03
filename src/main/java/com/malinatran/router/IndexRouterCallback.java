package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.resource.Directory;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import java.io.IOException;

public class IndexRouterCallback implements RouterCallback {

    private Directory directory;

    public void run(Request request, Response response) throws IOException {
        String path = request.getPath();
        String directoryPath = request.getDirectoryPath();
        directory = new Directory();

        if (path.equals("/")) {
            response.setStatus(Status.OK);
            response.setBodyContent(directory.getLinks(directoryPath));
        }
    }
}