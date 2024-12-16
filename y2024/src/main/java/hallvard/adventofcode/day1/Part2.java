package hallvard.adventofcode.day1;

import java.util.HashMap;
import java.util.Map;

public class Part2 {

    public int compute(String input) {
        String[] ss = input.split("\\s+");
        int[][] nums = new int[2][];
        nums[0] = new int[ss.length / 2];
        nums[1] = new int[ss.length / 2];
        for (int i = 0; i < ss.length; i++) {
            nums[i % 2][i / 2] = Integer.valueOf(ss[i]);
        }
        Map<Integer, Integer> counters = new HashMap<>();
        for (int i = 0; i < nums[1].length; i++) {
            counters.compute(nums[1][i], (key, counter) -> counter == null ? 1 : counter + 1);
        }
        int score = 0;
        for (int i = 0; i < nums[0].length; i++) {
            score += nums[0][i] * counters.getOrDefault(nums[0][i], 0);
        }
        return score;
    }

    public static void main(String[] args) {
        System.out.println(new Part2().compute(Part1.INPUT));
    }
}