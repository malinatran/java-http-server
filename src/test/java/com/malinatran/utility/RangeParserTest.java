package com.malinatran.utility;

import org.junit.Test;
import static org.junit.Assert.*;

public class RangeParserTest {

    @Test
    public void getValuesReturnsRangeStartAndEnd() {
        RangeParser parser = new RangeParser();
        String header = "Range: bytes=4-10";

        String[] result = parser.getValues(header);

        assertEquals("4", result[0]);
        assertEquals("10", result[1]);
    }

    @Test
    public void getValuesReturnsRangeStart() {
        RangeParser parser = new RangeParser();
        String header = "Range: bytes=4-";

        String[] result = parser.getValues(header);

        assertEquals("4", result[0]);
        assertEquals("", result[1]);
    }

    @Test
    public void getValuesReturnsRangeEnd() {
        RangeParser parser = new RangeParser();
        String header = "Range: bytes=-10";

        String[] result = parser.getValues(header);

        assertEquals("", result[0]);
        assertEquals("10", result[1]);
    }
}