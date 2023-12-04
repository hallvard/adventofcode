package hallvard.adventofcode.day2;

public class Part2 extends Part1 {

    public int compute(String s) {
        return s.lines()
            .map(this::parseGame)
            .map(Game::colors)
            .mapToInt(colors -> colors.red() * colors.green() * colors.blue())
            .sum();
    }

    public static void main(String[] args) {
        System.out.println(new Part2().compute(INPUT));
    }
}