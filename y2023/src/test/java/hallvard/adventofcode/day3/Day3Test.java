package hallvard.adventofcode.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class Day3Test {

    public final static String SAMPLE = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...$.*....
        .664.598..
        """;

    @Test
    public void testPart1() {
        assertEquals(4361, new Part1().compute(SAMPLE.split("\\s+")));
    }

    @Test
    public void testPart2() {
        assertEquals(467835, new Part2().compute(SAMPLE.split("\\s+")));
    }
}