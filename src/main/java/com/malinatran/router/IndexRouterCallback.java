package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import java.io.IOException;

public class IndexRouterCallback implements RouterCallback {

    public void run(Request request, Response response) throws IOException {
        String path = request.getPath();
        String file = path.replace("/", "").trim();
        DirectoryReader directoryReader = new DirectoryReader();
        Boolean isTextFile = directoryReader.isTextFile(file);
        Boolean exists = directoryReader.existsInDirectory(file);

        if (path.equals("/")) {
            response.setStatus(Status.OK);
            response.setBodyContent(directoryReader.getLinks());
        } else if (isTextFile && exists) {
            response.setStatus(Status.OK);
            response.setBodyContent(directoryReader.readTextFile(file));
        } else if (isTextFile && !exists) {
            response.setStatus(Status.NOT_FOUND);
        } else {
            response.setStatus(Status.UNSUPPORTED_MEDIA_TYPE);
        }
    }
}