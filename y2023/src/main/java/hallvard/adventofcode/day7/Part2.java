package hallvard.adventofcode.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Part2 {

    public static final String CARDS_VALUES = "AKQT98765432J";

    public static int cardRank(char card) {
        return CARDS_VALUES.indexOf(card);
    }

    public static int[] cardRanks(String cards) {
        int[] ranks = new int[cards.length()];
        for (int i = 0; i < cards.length(); i++) {
            ranks[i] = cardRank(cards.charAt(i));
        }
        return ranks;
    }

    public enum HandType {
        
        FiveOfAKind, FourOfAKind, FullHouse, ThreeOfAKind, TwoPairs, OnePair, HighCard;

        private static int highestCardCountsAsInt(String hand) {
            Map<Character, Integer> cardCounts = new HashMap<>();
            for (int i = 0; i < hand.length(); i++) {
                var card = hand.charAt(i);
                cardCounts.put(card, cardCounts.getOrDefault(card, 0) + 1);
            }
            var countEntries = new ArrayList<>(cardCounts.entrySet());
            if (countEntries.size() == 1) {
                // may be jacks or other
                return 50;
            }
            Collections.sort(countEntries, (entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()));
            int jackCount = cardCounts.getOrDefault('J', 0);
            countEntries.removeIf(entry -> entry.getKey() == 'J');
            var entry1 = countEntries.get(0);
            var entry2 = (countEntries.size() > 1 ? countEntries.get(1) : null);
            // add jack count to highest count
            return (entry1.getValue() + jackCount) * 10 + (entry2 != null ? entry2.getValue() : 0);
        }
        
        public static HandType classify(String hand) {
            return switch (highestCardCountsAsInt(hand)) {
                case 50 -> FiveOfAKind;
                case 41 -> FourOfAKind;
                case 32 -> FullHouse;
                case 31 -> ThreeOfAKind;
                case 22 -> TwoPairs;
                case 21 -> OnePair;
                default -> HighCard;
            };
        }
    }

    public record HandAndBid(String hand, HandType type, int bid) {
        public HandAndBid(String hand, int bid) {
            this(hand, HandType.classify(hand), bid);
        }
    }

    public static int compare(HandAndBid hand1, HandAndBid hand2) {
        int compared = hand1.type.compareTo(hand2.type);
        if (compared == 0) {
            compared = Arrays.compare(cardRanks(hand1.hand), cardRanks(hand2.hand));
        }
        return compared;
    }

    public int compute(String[] handsAndBids) {
        var handAndBidList = new ArrayList<HandAndBid>();
        for (int i = 0; i < handsAndBids.length; i += 2) {
            handAndBidList.add(new HandAndBid(handsAndBids[i], Integer.parseInt(handsAndBids[i + 1])));
        }
        Collections.sort(handAndBidList, Part2::compare);
        int sum = 0;
        for (int i = 0; i < handAndBidList.size(); i++) {
            sum += handAndBidList.get(i).bid * (handAndBidList.size() - i);
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(new Part2().compute(Part1.INPUT.split("\\s+")));
    }
}