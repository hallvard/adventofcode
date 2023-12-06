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

    @Test
    public void test() {
        assertEquals(281, new Part2().compute(SAMPLE));
    }
}