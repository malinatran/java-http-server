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

    ClientHandler (Writer out, Reader in, Logger logger, Router router) throws IOException {
        this.out = out;
        this.in = in;
        this.logger = logger;
        this.router = router;
    }

    public void run() {
        try {
            getRequestAndResponse(out, in, logger);
            closeStreams(out, in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getRequestAndResponse(Writer out, Reader in, Logger logger) throws IOException {
        RequestListener requestListener = new RequestListener();
        Request request = requestListener.getNextRequest(in);
        Response response = router.getResponse(request, logger);
        out.write(response);
    }

    private void closeStreams(Writer out, Reader in) throws IOException {
        out.close();
        in.close();
    }
}
