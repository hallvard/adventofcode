package hallvard.adventofcode.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Day8Test {

    public final static String SAMPLE1A = """
        RL

        AAA = (BBB, CCC)
        BBB = (DDD, EEE)
        CCC = (ZZZ, GGG)
        DDD = (DDD, DDD)
        EEE = (EEE, EEE)
        GGG = (GGG, GGG)
        ZZZ = (ZZZ, ZZZ)
        """;

    public final static String SAMPLE1B = """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
        """;

    public final static String SAMPLE2A = """
        LR

        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)
        """;

    @Test
    public void testPart1Sample1() {
        assertEquals(2, new Part1().compute(SAMPLE1A));
        assertEquals(6, new Part1().compute(SAMPLE1B));
    }

    @Test
    public void testPart2() {
        assertEquals(6, new Part2().compute(SAMPLE2A));
    }
}
