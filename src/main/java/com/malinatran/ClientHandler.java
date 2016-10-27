package com.malinatran;

import com.malinatran.reader.Reader;
import com.malinatran.router.Logger;
import com.malinatran.writer.Writer;
import com.malinatran.request.Request;
import com.malinatran.request.RequestListener;
import com.malinatran.response.Response;
import com.malinatran.router.Router;

import java.io.IOException;

public class ClientHandler implements Runnable {

    private final Writer out;
    private final Reader in;
    private final Router router;
    private final Logger logger;
    private final String directoryPath;

    ClientHandler (Writer out, Reader in, Logger logger, Router router, String directoryPath) throws IOException {
        this.out = out;
        this.in = in;
        this.logger = logger;
        this.router = router;
        this.directoryPath = directoryPath;
    }

    public void run() {
        try {
            getRequestAndResponse();
            closeStreams();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getRequestAndResponse() throws IOException {
        RequestListener requestListener = new RequestListener();
        Request request = requestListener.getNextRequest(in, directoryPath);
        Response response = router.getResponse(request, logger);
        out.write(response);
    }

    private void closeStreams() throws IOException {
        out.close();
        in.close();
    }
}
