package com.malinatran.routing;

import com.malinatran.utility.FileType;
import com.malinatran.utility.Method;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.resource.Directory;
import com.malinatran.resource.FileTypeReader;
import com.malinatran.resource.Image;
import com.malinatran.resource.TextFile;
import com.malinatran.response.Response;
import com.malinatran.response.ResponseBuilder;

import java.io.IOException;
import java.util.Map;

public class FileContentAction implements Action {

    private Response response;
    private Request request;

    public void run(Request request, Response response) throws IOException {
        this.request = request;
        this.response = response;

        buildResponse();
    }

    private FileContentAction buildResponse() throws IOException {
        String absolutePath = request.getAbsolutePath();
        boolean exists = Directory.existsInDirectory(absolutePath);

        if (exists) {
            buildResponseByMethod(absolutePath);
        } else {
            response.setStatus(Status.NOT_FOUND);
        }

        return this;
    }

    private FileContentAction buildResponseByFileType(String absolutePath) throws IOException {
        FileType type = FileTypeReader.getFileType(absolutePath);

        switch (type) {
            case TEXT:
                setTextFileContent(absolutePath);
                break;
            case IMAGE:
                setImageFileContent(absolutePath);
                break;
            case UNSUPPORTED:
                response.setStatus(Status.UNSUPPORTED_MEDIA_TYPE);
                break;
        }

        return this;
    }

    private FileContentAction buildResponseByMethod(String absolutePath) throws IOException {
        String method = request.getMethod();

        if (method.equals(Method.PATCH)) {
            response.setStatus(Status.NO_CONTENT);
        } else {
            buildResponseByFileType(absolutePath);
        }

        return this;
    }

    private FileContentAction setImageFileContent(String absolutePath) throws IOException {
        byte[] image = Image.read(absolutePath);
        String imageType = Image.getImageType(absolutePath);
        ResponseBuilder.image(response, imageType, image);

        return this;
    }

    private FileContentAction setTextFileContent(String absolutePath) throws IOException {
        Map<String, Integer> ranges = request.getRangeValues();
        String content = TextFile.read(absolutePath, ranges);

        if (ranges.isEmpty()) {
            ResponseBuilder.text(response, content);
        } else {
            int total = TextFile.getCharacterCount(absolutePath);
            ResponseBuilder.partialText(response, content, ranges, total);
        }

        return this;
    }
}