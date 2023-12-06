package hallvard.adventofcode.day6;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Part1 {

    public record RaceData(int time, int distance) {
    }

    private List<Integer> parseNumbers(String line) {
        int pos = line.indexOf(':');
        return Stream.of(line.substring(pos + 1).trim().split("\\s+"))
            .map(Integer::parseInt)
            .toList();
    }

    public long compute(String[] raceData) {
        var times = parseNumbers(raceData[0]);
        var distances = parseNumbers(raceData[1]);
        List<RaceData> races = new ArrayList<>();
        for (int i = 0; i < times.size(); i++) {
            races.add(new RaceData(times.get(i), distances.get(i)));
        }
        return races.stream()
            .map(race -> IntStream.range(1, race.time)
                            .map(buttonTime -> (race.time - buttonTime) * buttonTime)
                            .filter(distance -> distance > race.distance)
                            .count()
            )
            .reduce(Long.valueOf(1), (Long l1, Long l2) -> l1 * l2);
    }

    public static void main(String[] args) {
        System.out.println(new Part1().compute(INPUT.split("\n")));
    }

    public final static String INPUT = """
        Time:        40     82     91     66
        Distance:   277   1338   1349   1063
        """;
}