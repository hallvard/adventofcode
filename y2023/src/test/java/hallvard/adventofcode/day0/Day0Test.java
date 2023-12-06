package hallvard.adventofcode.day0;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Day0Test {

    public final static String SAMPLE = """
        """;

    @Test
    public void testPart1() {
        assertEquals(0, new Part1().compute(SAMPLE));
    }

    @Test
    public void testPart2() {
        assertEquals(0, new Part2().compute(SAMPLE));
    }
}
