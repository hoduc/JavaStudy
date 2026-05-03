package basicjava.java16;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Java16Test {

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
    
}
