package com.malinatran;

import com.malinatran.reader.Reader;
import com.malinatran.writer.Writer;
import com.malinatran.request.Request;
import com.malinatran.request.RequestListener;
import com.malinatran.response.Response;
import com.malinatran.router.Router;

import java.io.*;

public class ClientHandler implements Runnable {

    private final Writer out;
    private final Reader in;
    private final Router router;

    ClientHandler (Writer out, Reader in, Router router) throws IOException {
        this.out = out;
        this.in = in;
        this.router = router;
    }

    public void run() {
        try {
            getRequestAndResponse(out, in);
            closeStreams(out, in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getRequestAndResponse(Writer out, Reader in) throws IOException {
        RequestListener requestListener = new RequestListener();
        Request request = requestListener.getNextRequest(in);
        Response response = router.getResponse(request);
        out.write(response.toString());
    }

    private void closeStreams(Writer out, Reader in) throws IOException {
        out.close();
        in.close();
    }
}
