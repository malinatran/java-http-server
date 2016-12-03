package com.malinatran.setup;

import com.malinatran.mocks.*;
import com.malinatran.reader.Reader;
import com.malinatran.reader.RequestReader;
import com.malinatran.utility.RequestLogger;
import com.malinatran.routing.Router;
import com.malinatran.utility.ResponseLogger;
import com.malinatran.writer.Writer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientHandlerTest {

    @Test
    public void runWritesResponse() throws IOException {
        ResponseLogger messageLogger = new ResponseLogger();
        Router mockRouter = new MockRouter();
        RequestLogger requestLogger = new RequestLogger();
        Writer writer = new MockResponseWriter(messageLogger);
        Reader reader = new MockRequestReader(new String[]
                {"GET / HTTP/1.1", "User-Agent: MalinaBrowser", "Host: localhost:8000", ""});
        ClientHandler clientHandler = new ClientHandler(writer, reader, requestLogger, mockRouter, "/path/to/somewhere/");

        clientHandler.run();

        assertEquals("HTTP/1.1 200 OK\r\n", messageLogger.getLoggedResponse().getStatusLine());
    }

    @Test(expected=IOException.class)
    public void runThrowsIOException() throws IOException {

        ServerSocket serverSocket = new ServerSocket(1001);
        Socket socket = serverSocket.accept();
        ResponseLogger messageLogger = new ResponseLogger();
        Router mockRouter = new MockRouter();
        RequestLogger requestLogger = new RequestLogger();
        Writer writer = new MockResponseWriter(messageLogger);
        Reader reader = new RequestReader(socket);
        ClientHandler clientHandler = new ClientHandler(writer, reader, requestLogger, mockRouter, "/path/to/somewhere/");

        clientHandler.run();
        serverSocket.close();
        socket.close();
    }

    @Test
    public void runClosesStreams() throws IOException {
        ResponseLogger messageLogger = new ResponseLogger();
        Router mockRouter = new MockRouter();
        RequestLogger requestLogger = new RequestLogger();
        Writer writer = new MockResponseWriter(messageLogger);
        Reader reader = new MockRequestReader(new String[]
                {"GET / HTTP/1.1", "User-Agent: MalinaBrowser", "Host: localhost:8000", ""});
        MockClientHandler mockClientHandler = new MockClientHandler(writer, reader, requestLogger, mockRouter, "/path/to/somewhere/");

        mockClientHandler.run();

        assertTrue(mockClientHandler.getClosed());
    }
}