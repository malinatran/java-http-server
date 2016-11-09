package com.malinatran.reader;

import java.io.IOException;

public interface Reader {

    void close() throws IOException;

    void read(char[] body, int offset, int length) throws IOException;

    String readLine() throws IOException;
}