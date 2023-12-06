package hallvard.adventofcode.day6;

import java.util.stream.LongStream;

public class Part2 {

    public record RaceData(int time, int distance) {
    }

    private long parseNumber(String line) {
        int pos = line.indexOf(':');
        return Long.parseLong(line.substring(pos + 1).replaceAll("\\s+", ""));
    }

    public long compute(String[] raceData) {
        var raceTime = parseNumber(raceData[0]);
        var raceDistance = parseNumber(raceData[1]);
        return LongStream.range(1, raceTime)
            .map(buttonTime -> (raceTime - buttonTime) * buttonTime)
            .filter(distance -> distance > raceDistance)
            .count();
    }

    public static void main(String[] args) {
        System.out.println(new Part2().compute(INPUT.split("\n")));
    }

    public final static String INPUT = """
        Time:        40     82     91     66
        Distance:   277   1338   1349   1063
        """;
}