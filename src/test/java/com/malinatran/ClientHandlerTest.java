package com.malinatran;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import org.junit.Test;

public class ClientHandlerTest {

    @Test(expected=IOException.class)
    public void testRunClosesStreams() throws IOException {
        Socket socket = new Socket();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader br = new MockBufferedReader(new String[]
                {"GET / HTTP/1.1", "User-Agent: MalinaBrowser", "Host: localhost/5000", ""});
        ClientHandler clientHandler = new ClientHandler(out, br);
        clientHandler.run();
        br.close();
    }
}
