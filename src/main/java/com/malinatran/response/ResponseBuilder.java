package com.malinatran.response;

import com.malinatran.utility.Header;
import com.malinatran.utility.Status;

import java.util.Map;

public class ResponseBuilder {

    private static final String END = "End";
    private static final String IMAGE = "image/";
    private static final String START = "Start";
    private static final String TEXT_PLAIN = "text/plain";

    public static Response image(Response response, String imageType, byte[] image) {
        response.setStatus(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, IMAGE + imageType);
        response.setHeader(Header.CONTENT_LENGTH, String.valueOf(image.length));
        response.setBodyContent(image);

        return response;
    }

    public static Response partialText(Response response, String text, Map<String, Integer> ranges, int total) {
        response.setStatus(Status.PARTIAL_CONTENT);
        response.setHeader(Header.CONTENT_TYPE, TEXT_PLAIN);
        response.setHeader(Header.CONTENT_RANGE, ranges.get(START) + "-" + ranges.get(END) + "/" + String.valueOf(total));
        response.setBodyContent(text);

        return response;
    }

    public static Response text(Response response, String text) {
        response.setStatus(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, TEXT_PLAIN);
        response.setBodyContent(text);

        return response;
    }
}