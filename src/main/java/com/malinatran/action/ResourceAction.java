package com.malinatran.action;

import com.malinatran.constant.FileType;
import com.malinatran.constant.Header;
import com.malinatran.constant.Status;
import com.malinatran.request.Request;
import com.malinatran.resource.Directory;
import com.malinatran.resource.Image;
import com.malinatran.response.Response;

import static com.malinatran.resource.TextFile.END;
import static com.malinatran.resource.TextFile.START;

import java.io.IOException;
import java.util.Map;

public class ResourceAction {

    private Directory directory = new Directory();
    private Image image = new Image();
    private Response response;
    private Request request;

    public void setContent(Request request, Response response) throws IOException {
        this.response = response;
        this.request = request;
        getContentByFileType();
    }

    private void getContentByFileType() throws IOException {
        String filePath = request.getFilePath();
        Map<String, Integer> ranges = request.getRangeValues();
        FileType type = request.getFileType(filePath, ranges);

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
}