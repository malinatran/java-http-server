package com.malinatran.writer;

import java.io.IOException;

public interface Writer {

    void write(String response) throws IOException;

    void close() throws IOException;
}
