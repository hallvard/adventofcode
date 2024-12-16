package hallvard.adventofcode.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class Part2Test {

    public final static String SAMPLE =
        "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

    @Test
    public void test() {
        assertEquals(48, new Part2().compute(Part1Test.SAMPLE));
    }
}