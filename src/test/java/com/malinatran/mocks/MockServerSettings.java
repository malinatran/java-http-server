package com.malinatran.mocks;

import com.malinatran.setup.ServerSettings;

import java.util.Map;

public class MockServerSettings extends ServerSettings {

    private boolean didExit;

    public MockServerSettings(Map<String, String> configuration) {
        super(configuration);
        didExit = false;
    }

    public boolean didExit() {
       return didExit;
    }

    @Override
    protected void printAndTerminate(String key, String value) {
        didExit = true;
    }
}