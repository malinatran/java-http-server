package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

import java.io.IOException;

public class IndexRouterCallback implements RouterCallback {
    public void run(Request request, Response response) throws IOException {

        String path = request.getPath();
        String revisedPath = path.replace("/", "").trim();
        DirectoryReader directoryReader = new DirectoryReader();
        Boolean isSupported = directoryReader.isFileFormatSupported(revisedPath);
        Boolean exists = directoryReader.existsInDirectory(revisedPath);

        if (path.equals("/")) {
            response.setStatus(Status.OK);
            response.setBodyContent(directoryReader.getLinks());
        } else if (isSupported && exists) {
            response.setStatus(Status.OK);
            response.setBodyContent(directoryReader.readTextFile(revisedPath));
        } else if (isSupported && !exists) {
            response.setStatus(Status.NOT_FOUND);
        } else {
            response.setStatus(Status.UNSUPPORTED_MEDIA_TYPE);
        }
    }
}