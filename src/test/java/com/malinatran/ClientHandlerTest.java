package com.malinatran;

import com.malinatran.mocks.MockBufferedReader;
import com.malinatran.mocks.MockBufferedWriter;
import com.malinatran.mocks.MockRouter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.malinatran.router.Router;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClientHandlerTest {

    @Test
    public void testRunWrites() throws IOException {
        com.malinatran.MessageLogger messageLogger = new com.malinatran.MessageLogger();
        Router mockRouter = new MockRouter();
        BufferedWriter mockBufferedWriter = new MockBufferedWriter(messageLogger);
        BufferedReader mockBufferedReader = new MockBufferedReader(new String[]
                {"GET / HTTP/1.1", "User-Agent: MalinaBrowser", "Host: localhost:5000", ""});
        ClientHandler clientHandler = new ClientHandler(mockBufferedWriter, mockBufferedReader, mockRouter);
        clientHandler.run();
        assertEquals("HTTP/1.1 200 OK\r\n", messageLogger.getLoggedMessage());
    }

    @Test(expected=IOException.class)
    public void testRunClosesStreams() throws IOException {
        Socket socket = new Socket();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader br = new MockBufferedReader(new String[]
                {"GET / HTTP/1.1", "User-Agent: MalinaBrowser", "Host: localhost:5000", ""});
        Router router = new Router();
        ClientHandler ch = new ClientHandler(out, br, router);
        ch.run();
        br.close();
    }
}

