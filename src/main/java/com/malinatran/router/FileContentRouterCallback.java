package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import java.io.IOException;

public class FileContentRouterCallback implements RouterCallback {

    public void run(Request request, Response response) throws IOException {
        String path = request.getPath();
        String fileName = path.replace("/", "").trim();
        DirectoryReader reader = new DirectoryReader();
        Boolean isTextFile = reader.isTextFile(fileName);
        Boolean isImageFile = reader.isImageFile(fileName);

        if (isTextFile) {
            handleTextFile(fileName, reader, response);
        } else if (isImageFile) {
            handleImageFile(fileName, response);
        } else {
            response.setStatus(Status.UNSUPPORTED_MEDIA_TYPE);
        }
    }

    private void handleTextFile(String fileName, DirectoryReader reader, Response response) throws IOException {
        Boolean exists = reader.existsInDirectory(fileName);

        if (exists) {
            response.setStatus(Status.OK);
            response.setBodyContent(reader.readTextFile(fileName));
        } else {
            response.setStatus(Status.NOT_FOUND);
        }
    }

    private void handleImageFile(String fileName, Response response) throws IOException {
        Image image = new Image();
        String fileType = image.getImageType(fileName);
        byte[] imageInBytes = image.extractBytes(fileName);

        response.setImage(fileType, imageInBytes);
    }
}
