package com.malinatran.setup;

import com.malinatran.mocks.*;
import com.malinatran.reader.Reader;
import com.malinatran.request.RequestLogger;
import com.malinatran.router.Router;
import com.malinatran.writer.Writer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;
import static org.junit.Assert.*;

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
}