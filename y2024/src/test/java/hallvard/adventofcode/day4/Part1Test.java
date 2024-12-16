package hallvard.adventofcode.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Part1Test {

    public final static String SAMPLE = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
        """;

    @Test
    public void test() {
        assertEquals(18, new Part1().compute(SAMPLE));
    }
}
