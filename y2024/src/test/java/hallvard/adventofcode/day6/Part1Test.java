package hallvard.adventofcode.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Part1Test {

    public final static String SAMPLE = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...
        """;

    @Test
    public void test() {
        assertEquals(41, new Part1().compute(SAMPLE));
    }
}
