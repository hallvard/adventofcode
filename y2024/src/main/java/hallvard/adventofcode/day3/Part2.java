package hallvard.adventofcode.day3;

public class Part2 extends Part1 {

    @Override
    public int compute(String input) {
        StringBuilder buffer = new StringBuilder();
        int pos = 0;
        boolean enabled = true;
        while (pos < input.length()) {
            String searchFor = enabled ? "don't()" : "do()";
            int end = input.indexOf(searchFor, pos);
            if (end < 0) {
                end = input.length();
            }
            if (enabled) {
                buffer.append(input, pos, end);
            }
            enabled = !enabled;
            pos = end + searchFor.length();
        }
        System.out.println(buffer);
        return super.compute(buffer.toString());
    }

    public static void main(String[] args) {
        System.out.println(new Part2().compute(Part1.INPUT));
    }
}