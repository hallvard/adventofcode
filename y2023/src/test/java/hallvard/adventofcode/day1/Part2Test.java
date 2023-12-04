package hallvard.adventofcode.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class Part2Test {

    public final static String SAMPLE = """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
        """;

    private final Part2 part2 = new Part2();

    @Test
    public void testSample() {
        assertEquals(281, part2.compute(SAMPLE));
    }
}