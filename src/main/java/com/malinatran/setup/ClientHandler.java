package com.malinatran.setup;

import com.malinatran.reader.Reader;
import com.malinatran.request.RequestLogger;
import com.malinatran.utility.Status;
import com.malinatran.writer.Writer;
import com.malinatran.request.Request;
import com.malinatran.request.RequestListener;
import com.malinatran.response.Response;
import com.malinatran.router.Router;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class ClientHandler implements Runnable {

    private final Writer out;
    private final Reader in;
    private final Router router;
    private final RequestLogger logger;
    private final String directory;

    public ClientHandler (Writer out, Reader in, RequestLogger logger, Router router, String directory) throws IOException {
        this.out = out;
        this.in = in;
        this.logger = logger;
        this.router = router;
        this.directory = directory;
    }

    public void run() {
        try {
            getRequestAndResponse();
            closeStreams();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void closeStreams() throws IOException {
        out.close();
        in.close();
    }

    private void getRequestAndResponse() throws IOException, NoSuchAlgorithmException {
        RequestListener requestListener = new RequestListener();
        Request request = requestListener.getNextRequest(in, directory);
        Response response = router.getResponse(request, logger);
        out.write(response);
    }
}