package hallvard.adventofcode.day8;

import java.util.Arrays;
import java.util.Map;

public class Part2 extends Part1 {

    @Override
    public long compute(String input) {
        Pair<String, Map<String, Pair<String, String>>> parsed = parse(input);
        var steps = parsed.item1();
        var graph = parsed.item2();
        var nodes = graph.keySet().stream().filter(node -> node.endsWith("A")).toArray(length -> new String[length]);
        long stepCount = 0;
        int stepPos = 0;
        int zCount = 0;
        int maxZCount = 0;
        while (zCount < nodes.length) {
            zCount = 0;
            var step = steps.charAt(stepPos);
            for (int i = 0; i < nodes.length; i++) {
                var node = next(graph.get(nodes[i]), step);
                if (node.endsWith("Z")) {
                    zCount++;
                }
                nodes[i] = node;
            }
            stepCount++;
            stepPos = (stepPos + 1) % steps.length();
            if (zCount > maxZCount) {
                System.out.println(zCount + " / " + nodes.length + " @ " + stepCount + ": " + Arrays.toString(nodes));
                maxZCount = zCount;
            }
        }
        return stepCount;
    }
    
    public static void main(String[] args) {
        System.out.println(new Part2().compute(INPUT));
    }
}