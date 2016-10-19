package com.malinatran.writer;

import com.malinatran.response.Response;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ResponseWriter implements Writer {

    private OutputStream output;
    private PrintStream writer;

    public ResponseWriter(Socket socket) throws IOException {
        output = socket.getOutputStream();
        writer = new PrintStream(output);
    }

    public void write(Response response) throws IOException {
        writer.write(response.getHeaders());
        writer.write(response.getBodyContent());
    }

    public void close() throws IOException {
        writer.close();
    }
}