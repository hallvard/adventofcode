package hallvard.adventofcode.day11;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Day11Test {

    public final static String SAMPLE = """
        ...#......
        .......#..
        #.........
        ..........
        ......#...
        .#........
        .........#
        ..........
        .......#..
        #...#.....
        """;

    @Test
    public void testToDisplacements() {
        List<Long> indices = new ArrayList<>(List.of(3L, 4L, 7L));
        new Part1().toDisplacements(indices, 10);
        assertEquals(new ArrayList<>(List.of(0, 1, 2, 3, 3, 3, 4, 5)), indices) ;
    }

    @Test
    public void testPart1() {
        assertEquals(374, new Part1().compute(SAMPLE, 2));
    }

    @Test
    public void testPart2() {
        assertEquals(1030, new Part1().compute(SAMPLE, 10));
        assertEquals(8410, new Part1().compute(SAMPLE, 100));
    }
}
