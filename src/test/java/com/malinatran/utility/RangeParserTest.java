package com.malinatran.utility;

import org.junit.Test;
import static org.junit.Assert.*;

public class RangeParserTest {

    String header;
    String[] result;

    @Test
    public void getValuesReturnsRangeStartAndEnd() {
        header = "Range: bytes=4-10";

        result = RangeParser.getValues(header);

        assertEquals("4", result[0]);
        assertEquals("10", result[1]);
    }

    @Test
    public void getValuesReturnsRangeStart() {
        header = "Range: bytes=4-";

        result = RangeParser.getValues(header);

        assertEquals("4", result[0]);
        assertEquals("", result[1]);
    }

    @Test
    public void getValuesReturnsRangeEnd() {
        header = "Range: bytes=-10";

        result = RangeParser.getValues(header);

        assertEquals("", result[0]);
        assertEquals("10", result[1]);
    }
}