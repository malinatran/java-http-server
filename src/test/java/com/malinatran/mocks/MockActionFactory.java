package com.malinatran.mocks;

import com.malinatran.request.Request;
import com.malinatran.routing.Action;
import com.malinatran.routing.ActionFactory;
import com.malinatran.utility.RequestLogger;

import java.util.Map;

public class MockActionFactory extends ActionFactory {

    public MockActionFactory(Request request, RequestLogger logger, Map<String, Action> routes) {
        super(request, logger, routes);
    }

    @Override
    public boolean hasBasicAuth(Request request) {
        return true;
    }
}