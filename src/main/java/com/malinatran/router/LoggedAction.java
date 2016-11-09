package com.malinatran.router;

import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.resource.TextFile;
import com.malinatran.response.Response;
import com.malinatran.utility.RequestLogger;
import com.malinatran.response.ResponseBuilder;
import com.malinatran.utility.SHA1Encoder;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class LoggedAction {

    private static final String FORM = "/form";
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

        buildResponse();
    }

    private LoggedAction buildResponse() throws IOException, NoSuchAlgorithmException {
        Map<String, Integer> ranges = request.getRangeValues();
        String path = request.getPath();

        if (path.equals(FORM)) {
            String content = String.valueOf(logger.getBody());
            ResponseBuilder.text(response, content);
        } else {
            setTextFileContent(ranges);
        }

        return this;
    }

    private boolean doesETagMatch(String encoded) {
        return encoded.equals(logger.getETag());
    }

    private String getOriginalOrPatchedContent(Map<String, Integer> ranges) throws IOException, NoSuchAlgorithmException {
        String filePath = request.getAbsolutePath();
        String original = TextFile.read(filePath, ranges);
        String encoded = SHA1Encoder.encode(original);

        return (doesETagMatch(encoded) ? String.valueOf(logger.getBody()) : original);
    }

    private LoggedAction setTextFileContent(Map<String, Integer> ranges) throws IOException, NoSuchAlgorithmException {
        String content = getOriginalOrPatchedContent(ranges);

        if (ranges.isEmpty()) {
            ResponseBuilder.text(response, content);
        } else {
            int total = TextFile.getCharacterCount(content);
            ResponseBuilder.partialText(response, content, ranges, total);
        }

        return this;
    }
}