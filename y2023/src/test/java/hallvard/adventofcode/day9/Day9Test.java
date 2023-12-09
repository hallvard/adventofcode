package hallvard.adventofcode.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Day9Test {

    public final static String SAMPLE = """
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45
        """;

    @Test
    public void testPart1() {
        assertEquals(114, new Part1().compute(SAMPLE.split("\n")));
    }

    @Test
    public void testPart2() {
        assertEquals(0, new Part2().compute(SAMPLE.split("\n")));
    }
}
