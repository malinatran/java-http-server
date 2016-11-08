package com.malinatran.router;

import com.malinatran.constant.Header;
import com.malinatran.constant.Status;
import com.malinatran.request.Request;
import com.malinatran.resource.TextFile;
import com.malinatran.response.Response;
import com.malinatran.request.RequestLogger;
import com.malinatran.utility.SHA1Encoder;
import static com.malinatran.resource.TextFile.END;
import static com.malinatran.resource.TextFile.START;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class LoggedRouterCallback implements RouterCallback {

    public static final String TEXT_PLAIN = "text/plain";
    private TextFile textFile = new TextFile();
    private Request request;
    private Response response;
    private RequestLogger logger;

    public void run(Response response, RequestLogger logger) {
        response.setStatus(Status.OK);
        response.setBodyContent(logger.getLoggedRequests());
    }

    public void run(Request request, Response response, RequestLogger logger) throws IOException, NoSuchAlgorithmException {
        this.request = request;
        this.response = response;
        this.logger = logger;

        Map<String, Integer> ranges = request.getRangeValues();
        String path = request.getPath();
        String content;

        if (path.equals("/form")) {
            content = new String(logger.getBody());
            setText(content);
        } else {
            content = getOriginalOrPatchedContent(ranges);
            if (ranges.isEmpty()) {
                setText(content);
            } else {
                setText(content, ranges);
            }
        }
    }

    public void run(Request request, Response response) throws IOException {}

    private String getOriginalOrPatchedContent(Map<String, Integer> ranges) throws IOException, NoSuchAlgorithmException {
        String filePath = request.getFilePath();
        String originalContent = textFile.readTextFile(filePath, ranges);
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
        response.setHeader(Header.CONTENT_TYPE, TEXT_PLAIN);
        response.setBodyContent(text);
    }

    private void setText(String text, Map<String, Integer> ranges) {
        response.setStatus(Status.PARTIAL_CONTENT);
        response.setHeader(Header.CONTENT_TYPE, TEXT_PLAIN);
        response.setHeader(Header.CONTENT_RANGE, ranges.get(START) + "-" + ranges.get(END));
        response.setBodyContent(text);
    }
}
