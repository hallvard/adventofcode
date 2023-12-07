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
    public void testPart1HandTypeClassify() {
        var part = new Part1();
        assertEquals(HandType.FiveOfAKind, part.classify("AAAAA"));
        assertEquals(HandType.OnePair, part.classify("32T3K"));
        assertEquals(HandType.ThreeOfAKind, part.classify("T55J5"));
        assertEquals(HandType.TwoPairs, part.classify("KK677"));
        assertEquals(HandType.TwoPairs, part.classify("KTJJT"));
        assertEquals(HandType.ThreeOfAKind, part.classify("QQQJA"));
        assertEquals(HandType.HighCard, part.classify("23456"));
    }

    @Test
    public void testPart1() {
        assertEquals(6440, new Part1().compute(SAMPLE.split("\\s+")));
    }

    @Test
    public void testPart2HandTypeClassify() {
        var part = new Part2();
        assertEquals(HandType.FiveOfAKind, part.classify("AAAAA"));
        assertEquals(HandType.OnePair, part.classify("32T3K"));
        assertEquals(HandType.FourOfAKind, part.classify("T55J5"));
        assertEquals(HandType.TwoPairs, part.classify("KK677"));
        assertEquals(HandType.FourOfAKind, part.classify("KTJJT"));
        assertEquals(HandType.FourOfAKind, part.classify("QQQJA"));
        assertEquals(HandType.HighCard, part.classify("23456"));
    }

    @Test
    public void testPart2() {
        assertEquals(5905, new Part2().compute(SAMPLE.split("\\s+")));
    }
}
