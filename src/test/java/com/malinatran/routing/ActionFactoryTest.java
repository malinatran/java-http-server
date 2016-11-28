package com.malinatran.routing;

import com.malinatran.mocks.MockActionFactory;
import com.malinatran.request.Request;
import com.malinatran.setup.ServerSettings;
import com.malinatran.utility.Mapping;
import com.malinatran.utility.RequestLogger;

import org.junit.Before;
import org.junit.Test;
import java.util.Map;

import static org.junit.Assert.*;

public class ActionFactoryTest {

    private String PATH = ServerSettings.HOME_DIRECTORY + ServerSettings.DEFAULT_DIRECTORY;
    private Request request;
    private RequestLogger logger;
    private Map<String, Action> routes = Mapping.getRoutes();
    private Action action;
    boolean result;

    @Before
    public void setUp() {
        request = new Request();
        logger = new RequestLogger();
    }

    @Test
    public void getActionReturnsCorrespondingAction() throws Exception {
        request.setRequestLine("GET / HTTP/1.1");
        ActionFactory factory = new ActionFactory(request, logger, routes);

        action = factory.getAction();

        assertEquals(new IndexAction().getClass(), action.getClass());
    }

    @Test
    public void getActionReturnsNullIfNonexistentRoute() throws Exception {
        request.setRequestLine("BOGUS /request HTTP/1.1");
        ActionFactory factory = new ActionFactory(request, logger, routes);

        action = factory.getAction();

        assertNull(action);
    }

    @Test
    public void hasBasicAuthReturnsTrue() throws Exception {
        request.setRequestLine("GET /logs HTTP/1.1");
        request.setHeader("Authorization: Basic");
        ActionFactory factory = new MockActionFactory(request, logger, routes);

        result = factory.hasBasicAuth(request);

        assertTrue(result);
    }

    @Test
    public void hasLoggedBody() throws Exception {
        String patched = "patched content";
        String hash = "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec";
        request.setDirectory(PATH);
        request.setRequestLine("PATCH /patch-content.txt HTTP/1.1");
        request.setHeader("If-Match: " + hash);
        request.setBody(patched.toCharArray());
        logger.logRequest(request);
        Request secondRequest = new Request();
        secondRequest.setDirectory(PATH);
        secondRequest.setRequestLine("GET /patch-content.txt HTTP/1.1");
        secondRequest.setBody("default content".toCharArray());
        ActionFactory factory = new ActionFactory(secondRequest, logger, routes);

        result = factory.hasLoggedBody(secondRequest, logger);

        assertTrue(result);
    }
}