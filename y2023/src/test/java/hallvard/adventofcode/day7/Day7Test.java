package hallvard.adventofcode.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import hallvard.adventofcode.day7.Part1.HandType;

public class Day7Test {

    public final static String SAMPLE = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
        """;

    @Test
    public void testHandTypeClassify() {
        assertEquals(HandType.FiveOfAKind, HandType.classify("AAAAA"));
        assertEquals(HandType.OnePair, HandType.classify("32T3K"));
        assertEquals(HandType.ThreeOfAKind, HandType.classify("T55J5"));
        assertEquals(HandType.TwoPairs, HandType.classify("KK677"));
        assertEquals(HandType.TwoPairs, HandType.classify("KTJJT"));
        assertEquals(HandType.ThreeOfAKind, HandType.classify("QQQJA"));
        assertEquals(HandType.HighCard, HandType.classify("23456"));
    }

    @Test
    public void testPart1() {
        assertEquals(6440, new Part1().compute(SAMPLE.split("\\s+")));
    }

    @Test
    public void testPart2() {
        assertEquals(5905, new Part2().compute(SAMPLE.split("\\s+")));
    }
}
