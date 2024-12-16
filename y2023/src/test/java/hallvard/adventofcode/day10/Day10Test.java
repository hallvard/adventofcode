package hallvard.adventofcode.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Day10Test {

    public final static String SAMPLE1a = """
        .....
        .S-7.
        .|.|.
        .L-J.
        .....
        """;

    public final static String SAMPLE1b = """
        ..F7.
        .FJ|.
        SJ.L7
        |F--J
        LJ...
        """;

    @Test
    public void testPart1() {
        assertEquals(4, new Part1().compute(SAMPLE1a));
        assertEquals(8, new Part1().compute(SAMPLE1b));
    }

    public final static String SAMPLE2a = """
        ...........
        .S-------7.
        .|F-----7|.
        .||.....||.
        .||.....||.
        .|L-7.F-J|.
        .|..|.|..|.
        .L--J.L--J.
        ...........
        """;

    public final static String SAMPLE2b = """
        ..........
        .S------7.
        .|F----7|.
        .||....||.
        .||....||.
        .|L-7F-J|.
        .|..||..|.
        .L--JL--J.
        ..........
        """;

    public final static String SAMPLE2c = """
        .F----7F7F7F7F-7....
        .|F--7||||||||FJ....
        .||.FJ||||||||L7....
        FJL7L7LJLJ||LJ.L-7..
        L--J.L7...LJS7F-7L7.
        ....F-J..F7FJ|L7L7L7
        ....L7.F7||L7|.L7L7|
        .....|FJLJ|FJ|F7|.LJ
        ....FJL-7.||.||||...
        ....L---J.LJ.LJLJ...
        """;

    public final static String SAMPLE2d = """
        FF7FSF7F7F7F7F7F---7
        L|LJ||||||||||||F--J
        FL-7LJLJ||||||LJL-77
        F--JF--7||LJLJ7F7FJ-
        L---JF-JLJ.||-FJLJJ7
        |F|F-JF---7F7-L7L|7|
        |FFJF7L7F-JF7|JL---7
        7-L-JL7||F7|L7F-7F7|
        L.L7LFJ|||||FJL7||LJ
        L7JLJL-JLJLJL--JLJ.L
        """;

    @Test
    public void testPart2() {
        assertEquals(4, new Part2().compute(SAMPLE2a));
        assertEquals(4, new Part2().compute(SAMPLE2b));
        assertEquals(8, new Part2().compute(SAMPLE2c));
        assertEquals(10, new Part2().compute(SAMPLE2d));
    }
}
