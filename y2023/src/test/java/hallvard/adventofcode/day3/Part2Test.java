package hallvard.adventofcode.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class Part2Test {

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

    private final Part2 part2 = new Part2();

    @Test
    public void testSample() {
        assertEquals(467835, part2.compute(SAMPLE.split("\\s+")));
    }
}