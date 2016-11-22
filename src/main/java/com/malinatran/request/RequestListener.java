package com.malinatran.request;

import com.malinatran.routing.Header;
import com.malinatran.reader.Reader;

import java.io.IOException;

public class RequestListener {

    public Request getNextRequest(Reader in, String directory) {
        Request request = new Request();

        try {
            request.setRequestLine(in.readLine());
            request.setDirectory(directory);
            setRequestHeaders(request, in);
            setRequestBody(request, in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return request;
    }

    private String formatReadLine(Reader in) throws IOException {
        return in.readLine().trim();
    }

    private RequestListener setRequestBody(Request request, Reader in) throws IOException {
        if (request.hasHeader(Header.CONTENT_LENGTH)) {
            int contentLength = Integer.parseInt(request.getHeaderValue(Header.CONTENT_LENGTH));
            char[] body = new char[contentLength];
            in.read(body, 0, contentLength);
            request.setBody(body);
        }

        return this;
    }

    private RequestListener setRequestHeaders(Request request, Reader in) throws IOException {
        while (true) {
            String line = formatReadLine(in);

            if (line == null || line.isEmpty()) {
                break;
            }
            request.setHeader(line);
        }

        return this;
    }
}