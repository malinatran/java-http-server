package com.malinatran;

import java.io.BufferedReader;
import java.io.IOException;

@SuppressWarnings("ALL")
public class RequestListener {

    public static final String CONTENT_LENGTH = "Content-Length";

    public Request getNextRequest(BufferedReader in) {
        try {
            Request request = new Request();
            request.setRequestLine(in.readLine());
            setRequestHeaders(request, in);
            setRequestBody(request, in);
            return request;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setRequestHeaders(Request request, BufferedReader in) throws IOException {
        String line;

        while (((line = in.readLine().trim()) != null) && !line.isEmpty()) {
            request.setHeader(line);
        }
    }

    public void setRequestBody(Request request, BufferedReader in) throws IOException {
        if (request.hasHeader(CONTENT_LENGTH)) {
            int contentLength = Integer.parseInt(request.getHeaderValue(CONTENT_LENGTH));
            char[] body = new char[contentLength];
            in.read(body, 0, contentLength);
            request.setBody(body.toString());
        }
    }
}
