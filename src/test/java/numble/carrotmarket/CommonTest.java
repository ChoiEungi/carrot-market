package numble.carrotmarket;


import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.TemporalAmount;

public class CommonTest {


    @Test
    void dateTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime to = LocalDateTime.now().plusYears(1).plusMonths(6);
        Duration duration = Duration.between(localDateTime, to);
        System.out.println("duration.toDays() = " + duration.toDays());
        System.out.println("duration.toHours() = " + duration.toHours());
        System.out.println("duration = " + duration.getSeconds());
    }
}
