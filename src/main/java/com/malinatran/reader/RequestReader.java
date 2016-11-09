package com.malinatran.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestReader implements Reader {

    private BufferedReader reader;
    private Socket socket;

    public RequestReader(Socket socket) throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket = socket;
    }

    public void close() throws IOException {
        reader.close();
        socket.close();
    }

    public void read(char[] body, int offset, int length) throws IOException {
        reader.read(body, offset, length);
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }
}