package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import java.io.IOException;
import java.util.Map;

public class FileContentRouterCallback implements RouterCallback {

    DirectoryReader reader = new DirectoryReader();

    public void run(Request request, Response response) throws IOException {
        String path = request.getPath();
        String fullPath = request.getDirectoryPath();
        Map<String, Integer> rangeBytes = request.getRangeBytes();
        String fileName = path.replace("/", "").trim();
        Boolean exists = reader.existsInDirectory(fullPath, fileName);

        if (exists) {
            processExistingTextOrImageFile(fullPath, fileName, rangeBytes, response);
        } else {
            response.setStatus(Status.NOT_FOUND);
        }
    }

    private void processExistingTextOrImageFile(String fullPath, String fileName, Map<String, Integer> rangeBytes, Response response) throws IOException {
        Boolean isTextFile = reader.isTextFile(fileName);
        Boolean isImageFile = reader.isImageFile(fileName);

        if (isTextFile) {
            processTextFile(fullPath, fileName, rangeBytes, response);
        } else if (isImageFile) {
            processImageFile(fileName, response);
        } else {
            response.setStatus(Status.UNSUPPORTED_MEDIA_TYPE);
        }
    }

    private void processTextFile(String fullPath, String fileName, Map<String, Integer> rangeBytes, Response response) throws IOException {
        String content = reader.readTextFile(fullPath, fileName);

        if (rangeBytes.isEmpty()) {
            response.setText(content);
        } else {
            response.setPartialText(content, rangeBytes);
        }
    }

    private void processImageFile(String fileName, Response response) throws IOException {
        Image image = new Image();
        String fileType = image.getImageType(fileName);
        byte[] imageAsBytes = image.extractBytes(fileName);

        response.setImage(fileType, imageAsBytes);
    }
}
