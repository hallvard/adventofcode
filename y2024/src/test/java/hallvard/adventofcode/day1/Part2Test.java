package hallvard.adventofcode.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class Part2Test {

    @Test
    public void test() {
        assertEquals(31, new Part2().compute(Part1Test.SAMPLE));
    }
}