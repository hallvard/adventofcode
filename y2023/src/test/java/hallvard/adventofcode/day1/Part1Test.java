package hallvard.adventofcode.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class Part1Test {

    public final static String SAMPLE = """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
        """;

    @Test
    public void test() {
        assertEquals(142, new Part1().compute(SAMPLE));
    }
}