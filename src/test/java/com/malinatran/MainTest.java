package com.malinatran.mocks;

import com.malinatran.response.Formatter;
import com.malinatran.routing.Router;
import com.malinatran.setup.DirectoryArg;
import com.malinatran.setup.PortArg;
import com.malinatran.utility.RequestLogger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainTest {

    private int PORT_5000 = 5000;
    private int PORT_6000 = 6000;
    private String expected;
    private MockMain main = new MockMain();
    private ByteArrayOutputStream out;
    private PrintStream mainOut;

    @Before
    public void setUp() throws IOException {
        out = new ByteArrayOutputStream();
        mainOut = System.out;
        System.setOut(new PrintStream(out));
    }

    @After
    public void tearDown() {
        System.setOut(mainOut);
    }

    @Test
    public void mainSetsPortAndDirectory() throws IOException {
        String[] args = {};

        main.main(args);

        assertEquals(PORT_5000, main.getPort());
        assertEquals(DirectoryArg.DEFAULT_PATH, main.getDirectory());
        main.closeSocket();
    }

    @Test
    public void mainSetsRequestLoggerAndRouter() throws IOException {
        String[] args = {};
        Router testRouter = new Router();
        RequestLogger testLogger = new RequestLogger();

        main.main(args);
        Router router = main.getRouter();
        RequestLogger logger = main.getLogger();

        assertEquals(testRouter.getClass(), router.getClass());
        assertEquals(testLogger.getClass(), logger.getClass());
        main.closeSocket();
    }

    @Test
    public void mainPrintsDefaultPortAndDirectory() throws IOException {
        expected = Formatter.addLF("Port: 5000" + Formatter.LF + "Directory: " + DirectoryArg.DEFAULT_PATH);
        String[] args = {};

        main.main(args);

        assertEquals(expected, out.toString());
    }

    @Test
    public void mainPrintsPortAndDirectoryInCommandLine() throws Exception {
        expected = Formatter.addLF("Port: 1111" + Formatter.LF + "Directory: " + DirectoryArg.DEFAULT_PATH);
        String[] args = {PortArg.FLAG, "1111"};

        main.main(args);

        assertEquals(expected, out.toString());
    }

    @Test
    public void mainPrintsBusyMessageError() throws IOException {
        new ServerSocket(PORT_6000);
        expected = Formatter.addLF("Busy port: 6000. Exiting now.");
        String[] args = {PortArg.FLAG, "6000"};

        main.main(args);

        assertTrue(out.toString().contains(expected));
    }

    @Test
    public void finalizeClosesServerSocket() throws Exception {
        String[] args = {};
        main.main(args);

        main.finalize();

        ServerSocket serverSocket = main.getServerSocket();
        assertTrue(serverSocket.isClosed());
    }
}