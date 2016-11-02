package com.malinatran.router;

import com.malinatran.constants.FileType;
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
        String fileName = request.getPath().replace("/", "").trim();
        String directoryPath = request.getDirectoryPath();
        Map<String, Integer> ranges = request.getRangeValues();
        Boolean exists = directory.existsInDirectory(directoryPath, fileName);

        if (exists) {
            readFile(directoryPath + fileName, ranges, response);
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
