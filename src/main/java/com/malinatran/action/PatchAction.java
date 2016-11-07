package com.malinatran.action;

import com.malinatran.constant.Header;
import com.malinatran.constant.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;
import com.malinatran.request.RequestLogger;
import com.malinatran.resource.TextFile;
import com.malinatran.utility.SHA1Encoder;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static com.malinatran.resource.TextFile.END;
import static com.malinatran.resource.TextFile.START;

public class PatchAction {

    private TextFile textFile = new TextFile();
    private Request request;
    private Response response;
    private RequestLogger logger;

    public void setBody(Request request, Response response, RequestLogger logger) throws IOException, NoSuchAlgorithmException {
        this.request = request;
        this.response = response;
        this.logger = logger;

        Map<String, Integer> ranges = request.getRangeValues();
        String path = request.getPath();
        String content;

        if (path.equals("/form")) {
            content = new String(logger.getBody());
            setText(content);
        } else if (!ranges.isEmpty()) {
            content = getContent(ranges);
            setText(content, ranges);
        } else {
            content = getContent(ranges);
            setText(content);
        }
    }

    private String getContent(Map<String, Integer> ranges) throws IOException, NoSuchAlgorithmException {
        String filePath = request.getFilePath();
        String originalContent = textFile.readPartialTextFile(filePath, ranges);
        String encodedContent = SHA1Encoder.convert(originalContent);

        if (encodedContent.equals(logger.getETag())) {
            String body = String.valueOf(logger.getBody());
            return body;
        } else {
            return originalContent;
        }
    }

    private void setText(String text) {
        response.setStatus(Status.OK);
        response.setHeader(Header.CONTENT_TYPE, Header.TEXT_PLAIN);
        response.setBodyContent(text);
    }

    private void setText(String text, Map<String, Integer> ranges) {
        response.setStatus(Status.PARTIAL_CONTENT);
        response.setHeader(Header.CONTENT_TYPE, Header.TEXT_PLAIN);
        response.setHeader(Header.CONTENT_RANGE, ranges.get(START) + "-" + ranges.get(END));
        response.setBodyContent(text);
    }
}
