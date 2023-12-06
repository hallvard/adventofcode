package hallvard.adventofcode.day5;

import java.util.List;

public class Part2 extends Part1 {

    protected long compute(List<Long> seeds, List<NumberToNumberMap> maps) {
        long min = Long.MAX_VALUE;
        for (int i = 0; i < seeds.size(); i += 2) {
            long start = seeds.get(i), end = start + seeds.get(i + 1);
            System.out.println("Processing seeds " + start + " ..< " + end);
            for (long seed = start; seed < end; seed++) {
                var num = NumberToNumberMap.map(seed, maps);
                if (num < min) {
                    min = num;
                    System.out.println(min + " @ " + seed);
                }
            }
        }
        return min;
    }

    public static void main(String[] args) {
        System.out.println(new Part2().compute(INPUT.split("\n\n")));
    }
}