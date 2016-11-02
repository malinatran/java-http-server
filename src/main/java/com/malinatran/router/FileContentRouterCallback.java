package com.malinatran.router;

import com.malinatran.constants.FileType;
import com.malinatran.constants.Method;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.resource.Directory;
import com.malinatran.resource.Image;
import com.malinatran.response.Response;

import java.io.IOException;
import java.util.Map;

public class FileContentRouterCallback implements RouterCallback {

    Directory directory = new Directory();
    Image image = new Image();

    public void run(Request request, Response response) throws IOException {
        String method = request.getMethod();
        String fileName = request.getCleanPath();
        String directoryPath = request.getDirectoryPath();
        String filePath = request.getFilePath();
        Map<String, Integer> ranges = request.getRangeValues();
        Boolean exists = directory.existsInDirectory(directoryPath, fileName);

        if (exists) {
            if (method.equals(Method.PATCH)) {
                response.setStatus(Status.NO_CONTENT);
            } else {
                readFile(filePath, ranges, response);
            }
        } else {
            response.setStatus(Status.NOT_FOUND);
        }
    }

    private void readFile(String filePath, Map<String, Integer> ranges, Response response) throws IOException {
        FileType type = directory.getFileType(filePath, ranges);

        switch (type) {
            case TEXT:
                String content = directory.getFileContent(filePath);
                response.setText(content);
                break;
            case PARTIAL_TEXT:
                content = directory.getFileContent(filePath, ranges);
                response.setText(content, ranges);
                break;
            case IMAGE:
                byte[] imageBytes = directory.getBytes(filePath);
                String imageType = image.getImageType(filePath);
                response.setImage(imageType, imageBytes);
                break;
            default:
                response.setStatus(Status.UNSUPPORTED_MEDIA_TYPE);
        }
    }
}
