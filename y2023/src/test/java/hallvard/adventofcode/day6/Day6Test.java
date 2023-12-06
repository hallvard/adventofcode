package hallvard.adventofcode.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Day6Test {

    public final static String SAMPLE = """
        Time:      7  15   30
        Distance:  9  40  200
        """;

    @Test
    public void testPart1() {
        assertEquals(288, new Part1().compute(SAMPLE.split("\n")));
    }

    @Test
    public void testPart2() {
        assertEquals(71503, new Part2().compute(SAMPLE.split("\n")));
    }
}
