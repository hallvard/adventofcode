package hallvard.adventofcode.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class Part1Test {

    public final static String SAMPLE = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
        """;

    @Test
    public void test() {
        assertEquals(11, new Part1().compute(SAMPLE));
    }
}