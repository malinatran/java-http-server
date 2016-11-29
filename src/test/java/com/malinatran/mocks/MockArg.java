package com.malinatran.mocks;

import com.malinatran.setup.Arg;

public class MockArg extends Arg {

    private boolean didExit;

    public MockArg() {
        didExit = false;
    }

    public boolean didExit() {
        return didExit;
    }

    public void setInteger() {
        didExit = true;
    }

    public void setString() {
        didExit = true;
    }
}