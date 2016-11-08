package com.malinatran.router;

import com.malinatran.utility.FileType;
import com.malinatran.utility.Method;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.request.RequestLogger;
import com.malinatran.resource.Directory;
import com.malinatran.resource.FileTypeReader;
import com.malinatran.resource.Image;
import com.malinatran.resource.TextFile;
import com.malinatran.response.Response;
import com.malinatran.response.ResponseBuilder;

import java.io.IOException;
import java.util.Map;

public class FileContentRouterCallback implements RouterCallback {

    private TextFile textFile = new TextFile();
    private Response response;
    private Request request;

    public void run(Request request, Response response) throws IOException {
        this.request = request;
        this.response = response;

        getResponse();
    }

    private void getResponse() throws IOException {
        String filePath = request.getFilePath();
        boolean exists = Directory.existsInDirectory(filePath);

        if (exists) {
            setResponse(filePath);
        } else {
            response.setStatus(Status.NOT_FOUND);
        }
    }

    private void setResponse(String filePath) throws IOException {
        String method = request.getMethod();

        if (method.equals(Method.PATCH)) {
            response.setStatus(Status.NO_CONTENT);
        } else {
            getContentByFileType(filePath);
        }
    }

    private void getContentByFileType(String filePath) throws IOException {
        FileType type = FileTypeReader.getFileType(filePath);

        switch (type) {
            case TEXT:
                setTextOrPartialText(filePath);
                break;
            case IMAGE:
                handleImage(filePath);
                break;
            default:
                response.setStatus(Status.UNSUPPORTED_MEDIA_TYPE);
        }
    }

    private void setTextOrPartialText(String filePath) throws IOException {
        Map<String, Integer> ranges = request.getRangeValues();
        String content = textFile.readTextFile(filePath, ranges);

        if (ranges.isEmpty()) {
            ResponseBuilder.text(response, content);
        } else {
            ResponseBuilder.partialText(response, content, ranges);
        }
    }

    private void handleImage(String filePath) throws IOException {
        byte[] imageBytes = Image.extractBytes(filePath);
        String imageType = Image.getImageType(filePath);
        ResponseBuilder.image(response, imageType, imageBytes);
    }

    public void run(Response response, RequestLogger logger) throws IOException {}
    public void run(Request request, Response response, RequestLogger logger) throws IOException {}
}