package com.malinatran.response;

import com.malinatran.constant.Header;
import com.malinatran.constant.Status;

import java.util.Map;

import static com.malinatran.resource.TextFile.END;
import static com.malinatran.resource.TextFile.START;

public class ResponseBuilder {

    private static final String IMAGE = "/image";
    private static final String TEXT_PLAIN = "text/plain";

    public static void text(Response response, String text) {
        response.setStatus(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, TEXT_PLAIN);
        response.setBodyContent(text);
    }

    public static void partialText(Response response, String text, Map<String, Integer> ranges) {
        response.setStatus(Status.PARTIAL_CONTENT);
        response.setHeader(Header.CONTENT_TYPE, TEXT_PLAIN);
        response.setHeader(Header.CONTENT_RANGE, ranges.get(START) + "-" + ranges.get(END)); // TODO: Add total count
        response.setBodyContent(text);
    }

    public static void image(Response response, String imageType, byte[] image) {
        response.setStatus(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, IMAGE + imageType);
        response.setHeader(Header.CONTENT_LENGTH, String.valueOf(image.length));
        response.setBodyContent(image);
    }
}