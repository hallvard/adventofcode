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

    private final Part1 part1 = new Part1();

    @Test
    public void testSample() {
        assertEquals(142, part1.compute(SAMPLE));
    }
}