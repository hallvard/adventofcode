package hallvard.adventofcode.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class Day2Test {

    public final static String SAMPLE = """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
        """;

    @Test
    public void testPart1() {
        assertEquals(8, new Part1().compute(SAMPLE, new Part1.GameColors(12, 13, 14)));
    }

    @Test
    public void testPart2() {
        assertEquals(2286, new Part2().compute(SAMPLE));
    }
}