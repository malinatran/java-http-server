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

    private Directory directory = new Directory();
    private Image image = new Image();
    private Response response;
    private Request request;

    public void run(Request request, Response response) throws IOException {
        this.request = request;
        this.response = response;
        String filePath = request.getFilePath();
        boolean exists = directory.existsInDirectory(filePath);

        if (exists) {
            setResponse();
        } else {
            response.setStatus(Status.NOT_FOUND);
        }
    }

    private void setResponse() throws IOException {
        String method = request.getMethod();

        if (method.equals(Method.PATCH)) {
            response.setStatus(Status.NO_CONTENT);
        } else {
            readFile();
        }
    }

    private void readFile() throws IOException {
        String filePath = request.getFilePath();
        Map<String, Integer> ranges = request.getRangeValues();
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
