package com.malinatran.reader;

import java.io.IOException;

public interface Reader {

    String readLine() throws IOException;

    void read(char[] body, int offset, int length) throws IOException;

    void close() throws IOException;
}
