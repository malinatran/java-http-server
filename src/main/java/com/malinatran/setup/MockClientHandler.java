package com.malinatran.setup;

import com.malinatran.reader.Reader;
import com.malinatran.routing.Router;
import com.malinatran.utility.RequestLogger;
import com.malinatran.writer.Writer;

import java.io.IOException;

public class MockClientHandler extends ClientHandler {

    private boolean closed;

    public MockClientHandler(Writer out, Reader in, RequestLogger logger, Router router, String directory) throws IOException {
        super(out, in, logger, router, directory);
        this.closed = false;
    }

    public void run() {
        this.closed = true;
    }

    public boolean getClosed() {
        return closed;
    }
}