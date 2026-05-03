package basicjava.java14;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;


public class Java14Test {
    
    public static String switchGetDayOfWeekFromDate(LocalDate date) {
        return switch (date.getDayOfWeek()) {
            case MONDAY -> "Monday";            
            default -> date.getDayOfWeek().name();
        };
    }

    @Test
    void switchExpression() {

        assertEquals(DayOfWeek.THURSDAY.name(), 
            switchGetDayOfWeekFromDate(LocalDate.parse("1970-01-01"))
        );

        assertEquals("Monday", 
            switchGetDayOfWeekFromDate(LocalDate.parse("1970-01-05"))
        );

        // yield
        /*            
                0:Zero
                1:odd
                2:even
                3:odd
                4:even
         */
        for (var i = 0; i < 5; i++) {
            System.out.println(i + ":"  + switch(i) {
                case 0 -> "Zero";
                default -> {
                    var result = "odd";
                    if (i % 2 == 0) {
                        result = "even";
                    }
                    yield result;
                }
            });
        }
    }
}
