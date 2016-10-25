package com.malinatran.writer;

import com.malinatran.response.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ResponseWriter implements Writer {

    private OutputStream output;
    private DataOutputStream writer;

    public ResponseWriter(Socket socket) throws IOException {
        output = socket.getOutputStream();
        writer = new DataOutputStream(output);
    }

    public void write(Response response) throws IOException {
        writer.write(response.getResponseHeadersAndBody());
    }

    public void close() throws IOException {
        writer.close();
    }
}