package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.resource.DirectoryReader;
import com.malinatran.response.Response;

public class CreateOrUpdateRouterCallback implements RouterCallback {

    public void run(Request request, Response response) {
        String body = request.getBody();
        String directoryPath = request.getDirectoryPath();
        String fileName = request.getPath().replace("/", "");
        DirectoryReader reader = new DirectoryReader();

        if (reader.existsInDirectory(directoryPath, fileName)) {
            response.setStatus(Status.METHOD_NOT_ALLOWED);
        } else if (body != null) {
            response.setStatus(Status.OK);
        } else {
            response.setStatus(Status.NOT_FOUND);
        }
    }
}
