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
    private final String fullPath;

    ClientHandler (Writer out, Reader in, Logger logger, Router router, String fullPath) throws IOException {
        this.out = out;
        this.in = in;
        this.logger = logger;
        this.router = router;
        this.fullPath = fullPath;
    }

    public void run() {
        try {
            getRequestAndResponse(out, in, router, logger, fullPath);
            closeStreams(out, in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getRequestAndResponse(Writer out, Reader in, Router router, Logger logger, String fullPath) throws IOException {
        RequestListener requestListener = new RequestListener();
        Request request = requestListener.getNextRequest(in, fullPath);
        Response response = router.getResponse(request, logger);
        out.write(response);
    }

    private void closeStreams(Writer out, Reader in) throws IOException {
        out.close();
        in.close();
    }
}
