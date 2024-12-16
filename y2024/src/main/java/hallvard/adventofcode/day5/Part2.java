package hallvard.adventofcode.day5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Part2 extends Part1 {

    protected int handlePages(String[] pages, Map<String, Collection<String>> constraints, boolean isCorrect) {
        if (isCorrect) {
            return 0;
        }
        List<String> ordered = new ArrayList<>();
        for (int i = 0; i < pages.length; i++) {
            var after = constraints.get(pages[i]);
            int j = 0;
            if (after != null) {
                while (j < ordered.size()) {
                    if (after.contains(ordered.get(j))) {
                        break;
                    }
                    j++;
                }
            } else {
                j = ordered.size();
            }
            ordered.add(j, pages[i]);
        }
        return Integer.valueOf(ordered.get(ordered.size() / 2));
    }

    public static void main(String[] args) {
        System.out.println(new Part2().compute(INPUT));
    }
}