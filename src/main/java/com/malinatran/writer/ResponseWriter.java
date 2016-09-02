package com.malinatran.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ResponseWriter implements Writer {

    private BufferedWriter writer;

    public ResponseWriter(Socket socket) throws IOException {
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void write(String response) throws IOException {
        writer.write(response);
    }

    public void close() throws IOException {
        writer.close();
    }
}
