package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.resource.DirectoryReader;
import com.malinatran.resource.Image;
import com.malinatran.resource.TextFile;
import com.malinatran.response.Response;

import java.io.IOException;
import java.util.Map;

public class FileContentRouterCallback implements RouterCallback {

    DirectoryReader directoryReader = new DirectoryReader();
    TextFile textFile = new TextFile();

    public void run(Request request, Response response) throws IOException {
        String path = request.getPath();
        String directoryPath = request.getDirectoryPath();
        Map<String, Integer> ranges = request.getRangeValues();
        String fileName = path.replace("/", "").trim();
        Boolean exists = directoryReader.existsInDirectory(directoryPath, fileName);

        if (exists) {
            processExistingFile(directoryPath, fileName, ranges, response);
        } else {
            response.setStatus(Status.NOT_FOUND);
        }
    }

    private void processExistingFile(String directoryPath, String fileName, Map<String, Integer> ranges, Response response) throws IOException {
        Boolean isTextFile = directoryReader.isTextFile(fileName);
        Boolean isImageFile = directoryReader.isImageFile(fileName);

        if (isTextFile) {
            processTextFile(directoryPath, fileName, ranges, response);
        } else if (isImageFile) {
            processImageFile(directoryPath, fileName, response);
        } else {
            response.setStatus(Status.UNSUPPORTED_MEDIA_TYPE);
        }
    }

    private void processTextFile(String directoryPath, String fileName, Map<String, Integer> ranges, Response response) throws IOException {
        if (ranges.isEmpty()) {
            String content = textFile.readTextFile(directoryPath, fileName);
            response.setText(content);
        } else {
            String content = textFile.readPartialTextFile(directoryPath, fileName, ranges);
            response.setPartialText(content, ranges);
        }
    }

    private void processImageFile(String directoryPath, String fileName, Response response) throws IOException {
        Image image = new Image();
        String fileType = image.getImageType(fileName);
        byte[] imageBytes = image.extractBytes(directoryPath, fileName);

        response.setImage(fileType, imageBytes);
    }
}
