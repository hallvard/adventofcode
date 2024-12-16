package hallvard.adventofcode.day4;

import hallvard.adventofcode.day4.Part1.CharGrid;

public class Part2 {

    public int compute(String input) {
        var grid = new CharGrid(input.split("\\s+"));
        int count = 0;
        for (int x = 0; x < grid.width(); x++) {
            for (int y = 0; y < grid.height(); y++) {
                boolean rd = grid.isAt("MAS", 1, x, y, 1, 1);
                boolean ru = grid.isAt("MAS", 1, x, y, 1, -1);
                boolean ld = grid.isAt("MAS", 1, x, y, -1, 1);
                boolean lu = grid.isAt("MAS", 1, x, y, -1, -1);
                if ((rd && ru) || (rd && ld) || (lu && ru) || (lu && ld)) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(new Part2().compute(Part1.INPUT));
    }
}