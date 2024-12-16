package hallvard.adventofcode.day10;

import java.util.List;
import java.util.stream.Stream;

import hallvard.adventofcode.day10.Part1.Direction;

public class Part2 extends Part1 {

    int getTileCount(Grid grid, List<Location> loop, boolean countInside) {
        Tile startTile = new Tile(
            Direction.of(loop.get(loop.size() - 2), loop.get(loop.size() - 1)),
            Direction.of(loop.get(loop.size() - 1), loop.get(0))
        );
        int crossCount = 0;
        int count = 0;
        for (int y = 0; y < grid.tiles().length; y++) {
            Location location = new Location(0, y);
            boolean inside = false;
            while (grid.isLegalLocation(location)) {
                if (loop.contains(location)) {
                    Tile tile = grid.getTile(location);
                    if (tile == null) {
                        tile = startTile;
                    }
                    //System.out.println(tile + " @ " + location);
                    if (tile.inDirection().isPerpendicular(Direction.EAST)) {
                        crossCount++;
                    }
                    if (tile.outDirection().isPerpendicular(Direction.EAST)) {
                        crossCount++;
                    }
                    if (crossCount == 2) {
                        inside = ! inside;
                        crossCount = 0;
                    }
                } else if (inside == countInside) {
                    count++;
                }
                location = location.step(Direction.EAST);
                //System.out.println(inside + " @ " + location);
            }
        }
        return count;
    }

    @Override
    public int compute(String input) {
        Grid grid = parseGrid(input);
        return Stream.of(Direction.values())
            .map(direction -> getLoop(grid, direction))
            .filter(loop -> loop != null)
            .mapToInt(loop -> getTileCount(grid, loop, true))
            .min().getAsInt();
    }

    public static void main(String[] args) {
        System.out.println(new Part2().compute(INPUT));
    }

    public final static String INPUT = """
        """;
}