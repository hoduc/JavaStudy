package basicjava.java10;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Java10Test {
    @Test
    void localVariableTypeInference() {
        // convenient creation methods for collections
        var expectedSet = new HashSet<>();
        expectedSet.add("a");
        expectedSet.add("b");
        assertEquals(expectedSet, Set.of("a", "b"));

        var expectedList = new ArrayList<>();
        expectedList.add("a");
        expectedList.add("b");
        var actualList = List.of("a", "b");
        assertEquals(expectedList, actualList);

        // var can also do it in for loop but does not work in return type, fields, method decl
        for (var i = 0; i < expectedList.size(); i++) {
            var e = expectedList.get(i);
            var a = actualList.get(i);
            assertEquals(e, a);
        }

        var expecteMap = new HashMap<>();
        expecteMap.put("a", 1);
        expecteMap.put("b", 2);
        assertEquals(expecteMap, Map.of("a", 1, "b", 2));

        
    }
}
