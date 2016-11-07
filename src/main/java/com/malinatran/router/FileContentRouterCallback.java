package com.malinatran.router;

import com.malinatran.constant.FileType;
import com.malinatran.constant.Header;
import com.malinatran.constant.Method;
import com.malinatran.constant.Status;
import com.malinatran.request.Request;
import com.malinatran.request.RequestLogger;
import com.malinatran.resource.Directory;
import com.malinatran.resource.Image;
import com.malinatran.response.Response;
import static com.malinatran.resource.TextFile.START;
import static com.malinatran.resource.TextFile.END;

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
            getContentByFileType();
        }
    }

    private void getContentByFileType() throws IOException {
        String filePath = request.getFilePath();
        Map<String, Integer> ranges = request.getRangeValues();
        FileType type = directory.getFileType(filePath, ranges);

        switch (type) {
            case TEXT:
                String content = directory.getContent(filePath);
                setText(content);
                break;
            case PARTIAL_TEXT:
                content = directory.getContent(filePath, ranges);
                setText(content, ranges);
                break;
            case IMAGE:
                byte[] imageBytes = directory.getBytes(filePath);
                String imageType = image.getImageType(filePath);
                setImage(imageType, imageBytes);
                break;
            default:
                response.setStatus(Status.UNSUPPORTED_MEDIA_TYPE);
        }
    }

    private void setText(String text) {
        response.setStatus(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, Header.TEXT_PLAIN);
        response.setBodyContent(text);
    }

    private void setText(String text, Map<String, Integer> range) {
        response.setStatus(Status.PARTIAL_CONTENT);
        response.setHeader(Header.CONTENT_TYPE, Header.TEXT_PLAIN);
        response.setHeader(Header.CONTENT_RANGE, range.get(START) + "-" + range.get(END));
        response.setBodyContent(text);
    }

    private void setImage(String fileType, byte[] image) {
        response.setStatus(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, Header.IMAGE + fileType);
        response.setHeader(Header.CONTENT_LENGTH, String.valueOf(image.length));
        response.setBodyContent(image);
    }

    public void run(Response response, RequestLogger logger) throws IOException {}
    public void run(Request request, Response response, RequestLogger logger) throws IOException {}
}