package com.malinatran.router;

import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.resource.TextFile;
import com.malinatran.response.Response;
import com.malinatran.request.RequestLogger;
import com.malinatran.response.ResponseBuilder;
import com.malinatran.utility.SHA1Encoder;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class LoggedRouterCallback implements RouterCallback {

    private static final String FORM = "/form";
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

        getBodyContent();
    }

    public void getBodyContent() throws IOException, NoSuchAlgorithmException {
        Map<String, Integer> ranges = request.getRangeValues();
        String path = request.getPath();

        if (path.equals(FORM)) {
            String content = String.valueOf(logger.getBody());
            ResponseBuilder.text(response, content);
        } else {
            buildResponseForTextFile(ranges);
        }
    }

    public void run(Request request, Response response) throws IOException {}

    private void buildResponseForTextFile(Map<String, Integer> ranges) throws IOException, NoSuchAlgorithmException {
        String content = getOriginalOrPatchedContent(ranges);

        if (ranges.isEmpty()) {
            ResponseBuilder.text(response, content);
        } else {
            ResponseBuilder.partialText(response, content, ranges);
        }
    }

    private String getOriginalOrPatchedContent(Map<String, Integer> ranges) throws IOException, NoSuchAlgorithmException {
        String filePath = request.getFilePath();
        String original = textFile.readTextFile(filePath, ranges);
        String encoded = SHA1Encoder.convert(original);

        return (doesETagMatch(encoded) ? String.valueOf(logger.getBody()) : original);
    }

    private boolean doesETagMatch(String encoded) {
        return encoded.equals(logger.getETag());
    }
}