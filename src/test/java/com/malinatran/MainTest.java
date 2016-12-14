package com.malinatran.mocks;

import com.malinatran.response.Formatter;
import com.malinatran.routing.Action;
import com.malinatran.routing.Router;
import com.malinatran.routing.Action;
import com.malinatran.setup.DirectoryArg;
import com.malinatran.setup.PortArg;
import com.malinatran.utility.Mapping;
import com.malinatran.utility.RequestLogger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainTest {

    private int PORT_5000 = 5000;
    private int PORT_6000 = 6000;
    private String expected;
    private MockMain main = new MockMain();
    private ByteArrayOutputStream out;
    private PrintStream mainOut;
    private Map<String, Action> routes;

    @Before
    public void setUp() throws IOException {
        out = new ByteArrayOutputStream();
        mainOut = System.out;
        System.setOut(new PrintStream(out));
        routes = Mapping.getRoutes();
    }

    @After
    public void tearDown() {
        System.setOut(mainOut);
    }

    @Test
    public void runServerSetsPortAndDirectory() throws IOException {
        String[] args = {};

        main.runServer(routes, args);

        assertEquals(PORT_5000, main.getPort());
        assertEquals(DirectoryArg.DEFAULT_PATH, main.getDirectory());
        main.closeSocket();
    }

    @Test
    public void runServerSetsRequestLoggerAndRouter() throws IOException {
        String[] args = {};
        Map<String, Action> routes = Mapping.getRoutes();
        Router testRouter = new Router(routes);
        RequestLogger testLogger = new RequestLogger();

        main.runServer(routes, args);
        Router router = main.getRouter();
        RequestLogger logger = main.getLogger();

        assertEquals(testRouter.getClass(), router.getClass());
        assertEquals(testLogger.getClass(), logger.getClass());
        main.closeSocket();
    }

    @Test
    public void runServerPrintsDefaultPortAndDirectory() throws IOException {
        expected = Formatter.addLF("Port: 5000" + Formatter.LF + "Directory: " + DirectoryArg.DEFAULT_PATH);
        String[] args = {};

        main.runServer(routes, args);

        assertEquals(expected, out.toString());
    }

    @Test
    public void runServerPrintsPortAndDirectoryInCommandLine() throws Exception {
        expected = Formatter.addLF("Port: 1111" + Formatter.LF + "Directory: " + DirectoryArg.DEFAULT_PATH);
        String[] args = {PortArg.FLAG, "1111"};

        main.runServer(routes, args);

        assertEquals(expected, out.toString());
    }

    @Test
    public void runServerPrintsBusyMessageError() throws IOException {
        new ServerSocket(PORT_6000);
        expected = Formatter.addLF("Busy port: 6000. Exiting now.");
        String[] args = {PortArg.FLAG, "6000"};

        main.runServer(routes, args);

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