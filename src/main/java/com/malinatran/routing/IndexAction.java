package com.malinatran.routing;

import com.malinatran.utility.Status;
import com.malinatran.resource.Directory;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import java.io.IOException;

public class IndexAction implements Action {

    public void run(Request request, Response response) throws IOException {
        String path = request.getPath();
        String directoryPath = request.getDirectoryPath();

        if (path.equals("/")) {
            response.setStatus(Status.OK);
            response.setBodyContent(Directory.getLinks(directoryPath));
        }
    }
}