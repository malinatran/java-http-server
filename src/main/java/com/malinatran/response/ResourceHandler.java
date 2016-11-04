package com.malinatran.response;

import com.malinatran.constant.FileType;
import com.malinatran.constant.Header;
import com.malinatran.constant.Status;
import com.malinatran.request.Request;
import com.malinatran.resource.Directory;
import com.malinatran.resource.Image;

import java.io.IOException;
import java.util.Map;

import static com.malinatran.resource.TextFile.END;
import static com.malinatran.resource.TextFile.START;

public class ResourceHandler {

    private Directory directory = new Directory();
    private Image image = new Image();
    private Response response;

    public void read(Request request, Response response) throws IOException {
        this.response = response;
        String filePath = request.getFilePath();
        Map<String, Integer> ranges = request.getRangeValues();
        FileType type = request.getFileType(filePath, ranges);

        switch (type) {
            case TEXT:
                String content = directory.getFileContent(filePath);
                setText(content);
                break;
            case PARTIAL_TEXT:
                content = directory.getFileContent(filePath, ranges);
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

    public void setText(String text) {
        response.setStatus(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, Header.TEXT_PLAIN);
        response.setBodyContent(text);
    }

    public void setText(String text, Map<String, Integer> range) {
        response.setStatus(Status.PARTIAL_CONTENT);
        response.setHeader(Header.CONTENT_TYPE, Header.TEXT_PLAIN);
        response.setHeader(Header.CONTENT_RANGE, range.get(START) + "-" + range.get(END));
        response.setBodyContent(text);
    }

    public void setImage(String fileType, byte[] image) {
        response.setStatus(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, Header.IMAGE + fileType);
        response.setHeader(Header.CONTENT_LENGTH, String.valueOf(image.length));
        response.setBodyContent(image);
    }
}