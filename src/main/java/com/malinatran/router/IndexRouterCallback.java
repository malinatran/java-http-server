package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import java.io.IOException;

public class IndexRouterCallback implements RouterCallback {

    public void run(Request request, Response response) throws IOException {
        String path = request.getPath();
        String fullPath = request.getDirectoryPath();
        DirectoryReader directoryReader = new DirectoryReader();

        if (path.equals("/")) {
            response.setStatus(Status.OK);
            response.setBodyContent(directoryReader.getLinks(fullPath));
        }
    }
}