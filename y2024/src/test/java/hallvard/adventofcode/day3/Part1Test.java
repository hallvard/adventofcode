package hallvard.adventofcode.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class Part1Test {

    public final static String SAMPLE =
        "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";

    @Test
    public void test() {
        assertEquals(161, new Part1().compute(SAMPLE));
    }
}