package com.malinatran;

import com.malinatran.reader.Reader;
import com.malinatran.mocks.ResponseLogger;
import com.malinatran.mocks.MockRequestReader;
import com.malinatran.mocks.MockResponseWriter;
import com.malinatran.mocks.MockRouter;
import com.malinatran.router.Logger;
import com.malinatran.router.Router;
import com.malinatran.writer.Writer;

import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

public class ClientHandlerTest {

    @Test
    public void runWritesResponse() throws IOException {
        ResponseLogger messageLogger = new ResponseLogger();
        Router mockRouter = new MockRouter();
        Logger logger = new Logger();
        Writer writer = new MockResponseWriter(messageLogger);
        Reader reader = new MockRequestReader(new String[]
                {"GET / HTTP/1.1", "User-Agent: MalinaBrowser", "Host: localhost:6000", ""});
        ClientHandler clientHandler = new ClientHandler(writer, reader, logger, mockRouter);

        clientHandler.run();

        assertEquals("HTTP/1.1 200 OK\r\n", messageLogger.getLoggedResponse().getStatusLine());
    }
}
