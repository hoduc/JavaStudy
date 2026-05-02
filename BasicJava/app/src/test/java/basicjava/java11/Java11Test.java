package basicjava.java11;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Java11Test {
    @Test
    void localVariableTypeInferenceOnLambdaExpression() {
        
        assertEquals(
            List.of("A", "B", "C"),
            List.of("a", "b", "c").stream()
            .map((@NonNull var s) -> s.toUpperCase())
            .toList()
        );
    }

    @Test
    void unicode() {
        // 🥇 on unicode 10
        assertTrue(Character.isDefined(0x1F948));
    }
}
