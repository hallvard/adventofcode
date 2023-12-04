package hallvard.adventofcode.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class Part1Test {

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

    private final Part1 part1 = new Part1();

    @Test
    public void testSample() {
        assertEquals(4361, part1.compute(SAMPLE.split("\\s+")));
    }
}