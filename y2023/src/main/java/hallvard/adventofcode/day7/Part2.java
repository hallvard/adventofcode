package hallvard.adventofcode.day7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Part2 extends Part1 {

    private static final String CARDS_VALUES = "AKQT98765432J";

    @Override
    public int cardRank(char card) {
        return CARDS_VALUES.indexOf(card);
    }

    @Override
    public int highestCardCountsAsInt(String hand) {
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

    public static void main(String[] args) {
        System.out.println(new Part2().compute(Part1.INPUT.split("\\s+")));
    }
}