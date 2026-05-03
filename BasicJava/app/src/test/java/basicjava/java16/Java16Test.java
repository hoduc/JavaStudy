package basicjava.java16;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class Java16Test {

    record Range(int lo, int hi) {
        Range {
            if (lo > hi)
                throw new IllegalArgumentException(String.format("(%d,%d)", lo, hi));
        }
    }
    private static String runtimeType(Object obj) {
        String t = "unknown";
        // switch does not do pattern matching yet until java 21
        if (obj instanceof String s) {
            t = "[" + s + "]" + ":string";
        } else if (obj instanceof Integer i) {
            t = "[" + i + "]" + ":int";
        }
        return  t;
    }

    @Test
    void patternMatchingInstanceof() {
        assertEquals("[hello]:string", runtimeType("hello"));
        assertEquals("[42]:int", runtimeType(42));
        assertEquals("unknown", runtimeType(42.0));
    }

    @Test
    void record() {
        // example lifted from: https://openjdk.org/jeps/395
        record LocalRange(int lo, int hi) {
            LocalRange {
                if (lo > hi)
                    throw new IllegalArgumentException(String.format("(%d,%d)", lo, hi));
            }
        }

        var lr = new LocalRange(1, 10);
        assertEquals(1, lr.lo());
        assertEquals(10, lr.hi());

        assertThrows(IllegalArgumentException.class, () -> new LocalRange(10, 1));

        var r = new Range(1, 10);
        assertEquals(1, r.lo());
        assertEquals(10, r.hi());
    }
    
}
