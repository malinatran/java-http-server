package com.malinatran.request;

import com.malinatran.reader.Reader;

import java.io.IOException;

@SuppressWarnings("ALL")
public class RequestListener {

    public static final String CONTENT_LENGTH = "Content-Length";

    public Request getNextRequest(Reader in) {
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

    private String formatReadLine(Reader in) throws IOException {
        return in.readLine().trim();
    }

    private void setRequestHeaders(Request request, Reader in) throws IOException {
        while (true) {
            String line = formatReadLine(in);

            if (line == null || line.isEmpty()) {
                break;
            }
            request.setHeader(line);
        }
    }

    private void setRequestBody(Request request, Reader in) throws IOException {
        if (request.hasHeader(CONTENT_LENGTH)) {
            int contentLength = Integer.parseInt(request.getHeaderValue(CONTENT_LENGTH));
            char[] body = new char[contentLength];
            in.read(body, 0, contentLength);
            request.setBody(body.toString());
        }
    }
}
