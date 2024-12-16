package hallvard.adventofcode.day9;

public class Part2 extends Part1 {

    @Override
    protected int extrapolate(int[] numbers, int diff) {
        return numbers[0] - diff;
    }

    public static void main(String[] args) {
        System.out.println(new Part2().compute(INPUT.split("\n")));
    }
}