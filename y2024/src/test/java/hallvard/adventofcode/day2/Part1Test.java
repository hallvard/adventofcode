package hallvard.adventofcode.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class Part1Test {

    public final static String SAMPLE = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
        """;

    @Test
    public void test() {
        assertEquals(2, new Part1().compute(SAMPLE));
    }
}