package hallvard.adventofcode.day10;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import hallvard.adventofcode.day10.Part1.Direction;

public class Part1 {

    record Location(int x, int y) {
        Location step(Direction direction) {
            return new Location(x + direction.dx, y + direction.dy);
        }
    }

    enum Direction {
        EAST(1, 0), WEST(-1, 0), NORTH(0, -1), SOUTH(0, 1);

        private int dx, dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        static Direction of(int dx, int dy) {
            return switch (dx + dy * 2) {
                case  1 -> EAST;
                case -1 -> WEST;
                case -2 -> NORTH;
                case  2 -> SOUTH;
                default -> throw new IllegalArgumentException("Illegal direction: " + dx + ", " + dy);
            };
        }

        static Direction of(Location fromLocation, Location toLocation) {
            return of(toLocation.x - fromLocation.x, toLocation.y - fromLocation.y);
        }

        Direction opposite() {
            return of(-dx, -dy);
        }

        boolean isPerpendicular(Direction direction) {
            return dx * direction.dx + dy * direction.dy == 0;
        }
    }

    record Tile(Direction inDirection, Direction outDirection) {
        public Direction direct(Direction direction) {
            if (direction == inDirection) {
                return outDirection;
            } else if (direction == outDirection.opposite()) {
                return inDirection.opposite();
            } else {
                return null;
            }
        }
    }

    final static Map<Character, Tile> TILES = Map.of(
        '|', new Tile(Direction.NORTH, Direction.NORTH),
        '-', new Tile(Direction.EAST, Direction.EAST),
        'L', new Tile(Direction.WEST, Direction.NORTH),
        'J', new Tile(Direction.EAST, Direction.NORTH),
        '7', new Tile(Direction.NORTH, Direction.WEST),
        'F', new Tile(Direction.NORTH, Direction.EAST)
    );

    record Grid(Tile[][] tiles, Location start) {
        boolean isLegalLocation(Location location) {
            return (location.y >= 0 && location.y < tiles.length && location.x >= 0 && location.x < tiles[location.y].length);
        }
        Tile getTile(Location location) {
            return tiles[location.y][location.x];
        }
    }

    List<Location> getLoop(Grid grid, Direction direction) {
        List<Location> locations = new ArrayList<>();
        Location location = grid.start().step(direction);
        do {
            if (! grid.isLegalLocation(location)) {
                return null;
            }
            Tile tile = grid.getTile(location);
            if (tile == null) {
                return null;
            }
            direction = tile.direct(direction);
            if (direction == null) {
                return null;
            }
            locations.add(location);
            location = location.step(direction);
        } while (! location.equals(grid.start()));
        // also include the start
        locations.add(location);
        return locations;
    }

    Grid parseGrid(String input) {
        String[] lines = input.split("\n");
        Tile[][] grid = new Tile[lines.length][];
        Location start = null;
        for (int row = 0; row < lines.length; row++) {
            String line = lines[row];
            grid[row] = new Tile[line.length()];
            for (int col = 0; col < line.length(); col++) {
                var symbol = line.charAt(col);
                grid[row][col] = TILES.get(symbol);
                if (symbol == 'S') {
                    start = new Location(col, row);
                }
            }
        }
        return new Grid(grid, start);
    }

    public int compute(String input) {
        Grid grid = parseGrid(input);
        return Stream.of(Direction.values())
            .map(direction -> getLoop(grid, direction))
            .filter(loop -> loop != null)
            .mapToInt(loop -> loop.size() / 2)
            .min().getAsInt();
    }

    public static void main(String[] args) {
        System.out.println(new Part1().compute(INPUT));
    }

    public final static String INPUT = """
        J.7--J-.LL77FJ-F7FLJ-7.|FFF.F7FL-7F-7.L-FF77.-F-L7.F-L--7FL7..LF|J.7FF7.FLJ.-7-F7F-J77F77.|7.777FJ7FL7F-|.FFJ7|-|7FL-.LL7F77.|J7.|JJ-7-7|-|7
        J.|.FJ|FJ.-|LJ|L7FJ|.F.7-|J7L-J|L7|..-JF-JL77-|F|JL||.|L|J7L.|F7J-F-77--L|.-JF7F|JJ-|J||F7-77.|||L-FJ7J-|--7.F|7|LF|F--JF-F.LJ|.F|L|.|||7JL|
        |.|-JJFJL|LJ.FLJ-|-FJL-|L|||.|-F-F7-F7|7|F-|L-FF.|.-7FJ.|7F.F-|-7--JL..FLF-J|LLLJJ..J.LL.|L.-.L--J|L||JF|J||F-|L|LL|JJ.L7J.77L|-L|7--L7|||.|
        F-J..FL--F--L7LJ7L||.|.|FFL|-F-FJ.LFJ77FJJ.L7.-J|-7L-J.F|||-FLF--.L-J7F7F-7||-|J||.FL|.F--.FLJLL-L7.LL-FJ.FLJ.|-7-7..LF-J|-J7.|FJJ|7.7.LL7.|
        ||F77F7--|LJ|F|F7-J7F-F-.|JF.|-7LJF77|J||.|-JFL-F-|.L..FJFJ7J.|F|.L7.FLJ7L|---J-F7|77|FJ7F-FF|L|LF-LJ7--J.L.L7JF-7L|-FL7LF7L7.F-|-F--L-|7F.7
        |---J7.|LJJFF-LJLF|--7|.LLFLF-7J.LLJLL-7.--7J-JF|7|-7L---J--.FFFF7L|-LJ.--77LF.|FL7--J-JL|.||--L7J7|LL7|.777.|.LJ|.|LF7F-JL7.F|7J7|-|.7FJ|7|
        JLJLF|-LF7FL--L-FF|.LJ7-||..|LL7|7F7-LFJ-J-|LLF77LJ7|FLLJ7L7L7-F7-F--7-L7.LF7LFF7L7-F|J7FJ7|JFL7|.L-7|||F|-J-J7|.J7LFLJJL-7|F|LJ.FJ---J-7L7|
        LF..LJ.LLF|J-7F-LJ|L7.|.|FL7.J|FJF7J7JL|L7.L7J|L77F7L|.F7|-J.F-JL7L7FJJ|-77F7F7||F7F77F77L|..|.F7J.L7-J-FJ||FLF|7.7|LL.-.7||.L7|LF7FL7-|7.-|
        -77-L-|7|.L-7|F|JFJJ-F|-|J-F7|FF77J|77FFF77FF7|FJ7|7LL7|F|7LLL--7|FJL7.L-JL|LJ||LJ|||F7J..LF7FF7J|-||-.L|-F|--F.LFF|-.|FF--L7JFJ.|-|-77-J|-F
        .-J7LFLJJ7..-L7FF7J|7FJ.L|-F-|L-JL7.LFF7||F-J||L-7J-.FFFFJ||.|F7|||F-JFFL7-L-7|L7FJ|||L777JF7FJ|F|77|-J.7.F7L7J77||JL-JF-.L7L-LJ.7L|J.-JJJJ.
        77LL.7LLJL7-7JLJ||.7F7-7LL-7LF7L-FF--7F7||L-7LJF-JLL-F7JLF7F7FJLJ||L--777L-F-J|FJL-JLJFJ|FFJLJFJJ||F7-7FF-7J.|---F|-F|F-J7.L7F|LFJ7L-LJ|...F
        J7..L.F||J..|7JL-F-LL|-7-7|F-J|--LL7FJ||||F7L-7L77FLFJ|F-JLJ|L--7LJF--J-77J|F7|L--7F-7||7FL-7FJLFLJ-F7-FJFJF77FL-.|J||L.F77LF|L-7F--|LJ|7-|J
        ||J.LFLJ--FJL|7F|L-JL|7|JLLL7FJJ.|L|L7|LJLJ|7FJFJ7F7L7|L--7FJF7F|F-J-|JLL7.LJ|L7F7||.LJ-F7F-JL7F7|.F|FFL7L7-F7J--LL--JJ.|JL7|L-7JL7-7JFFJ7.J
        FL777L|.L7J|JJLJJ-F-7FJF7-JJ||F7-FFJFJL-7F-JFJFJF7||J|L7F7||FJ|FJ|F-7F7J7.F77|FJ|LJL7F7FJ|L7F-J|L77-FF-7|FJFJ|J|.||..|.LFJ.L77.F7F7J|.FJFFJ.
        JJFL7LJ.F|FL-7.7J.7LF7-F77.F|||L-7L7|F7L|L7FJFJFJ||L7|FJ|||LJFJL7||FJ||F7FJL7|L7L-7FJ||L7|FJL-7L7|F7FL7LJL7|FJJ-FFJ7.F7-L7..LL|J|7|.|.F7---J
        FFJ.LJF-FFJLL|-F----7J|||7FFJ||F-JFJ||L7|FJL7L7L7LJFJ|L-J||F7L-7|||L7|||LJF-JL7|F-J|FJL7||L7F-JFJLJ|F7L-7FJ||F77F7JL-|LFJF--7|LJ|L7FF-L|-|LL
        JJF7JJF7|JFL|L-L---7L--J|F7L7|||F7L7||FJ||F7L7|FJF-J-|F--JLJL7FJ|LJFJ||L-7L-7-||L-7|L7FJ|L7|L-7L7F-J|L-7|L-JLJ|FJ|.JL77JFJFJJ|FJL||FL7.J..FJ
        LF7LLF.---J--F-L|7LL-7F7LJL7||||||FJLJL-J|||L||L7L7F7|L7F7|F7||-L-7|F||F7L-7|FJL7FJL-JL-JFJ|F-J||L-7L-7||F----J|FJ77F7---.7|.JJJ---7J|F-7FJ.
        FLJ7.|7.JFJ7|LF--7F-7||L--7|||||||L---7F7|||FJ|FJFJ|||FJ|L7|LJL--7|L-JLJ|F7||L7FJL--7F7F-JFJL7F7|F7|F-JLJL-----JL-7-J|F-LFL77LJ7-|--JL77FJ-J
        F-7FFJ-7-JJLL7L-7|L7|LJLF-JLJLJLJ|F--7||||||L7|L7L-J||L7L7|L----7LJF7F--J|||L7||F7F7LJ|L-7L-7|||||LJL-7F-7F----7F-J7F|J|7|...F7|--7||.L-|JFJ
        |JJ7J|FL-JJL|-F-J|FJL-7.L------7FJL-7LJ|LJ|L-JL7L--7LJFJFJ|F7FF7L7FJLJF7L||L7||LJ||L-7|F-JF-JLJ||L7F-7LJFJL7F7LLJLF--7---|.|-J-L7-J7J7J||7JJ
        ||---JF-J.FF|FL-7|L7F-JF-7F7F--JL7F7L7FJ7FL-7F-JF7JL-7L7|FJ|L7||FJL-77||FJL7||L-7|L7FJ|L-7L-7F-J|FJ|FJF7L-7LJL7JF7|F7|7.LFF7L7JL|..LLJJ|L|77
        -7.FL-JJJFFJ-F--JL7||F7L7|||L--7FJ|L7||F7F7L|L7FJ|F7.|FJ|L7|FJ||L7F-JFJ|L-7|||F-JL-JL-JF-JF-J|F7||L|L7|L-7|F-7|FJ|||LJJ-F7||F-7J|F-F|JLL-|7J
        JJ-L7J...--7.L---7LJ||L-JLJ|F77||.|FJ||||||FJFJL7|||FJ|FJFJ||FJ|FJL-7L7L7FJLJ|L---7F---JF7L-7LJ||L7|FJL-7LJL7LJL7LJL7FLFJ||||FJFLLF7.7..|.-.
        F|LJ|FL.L7FFF-7F7L7FJL----7|||FJ|FJ|L|LJ|||L7|F7||||L7|L7L7||L7||F--J-L7LJF-7|F---J|FF7FJL7FJF-J|FJ|L7F7L-7FJFF7|F--JF7|FJ|||L7--F||F|7.|.|F
        F|7.FF.JF-||L7LJL-JL7F----JLJ|L7LJFJFJF-J||FJ||||LJL-JL7L7||L7||||-F7F7|F7L7LJL---7|FJLJF7|L7|7FJL7L7|||F7|L-7|||L7F-JLJL-JLJFJ|.FJL7-7--F7|
        ||L-7.|L-LLLJL---7F-JL-7F7F-7|FL-7L7L7L7FJ|L7|||L-7F7F-J||LJFJ|||L7||||LJL7|F7F7F7|LJF--J||FJL7L-7|FJ||LJ||F-J|||FJ|F---7F-7FJF-7L7FJJJJ|LL|
        F7LL|-J|||.L-F---JL7F-7LJLJF||F--JFJFJFJL7|FJ||L7|LJ|L7F7L-7L7|LJFJ|LJL7JL|||LJLJ||F-JF7|||L-7L7FJ|L7|L-7LJL-7||||FJL-7LLJF||L|FJFJL7FF7|7-|
        |.J7...F-J-7.L----7||FJF7|F7||L--7L7L7L-7|||FJ|FJF77L7||L-7L7||F7L7L--7L7FJ|L--7FJ|L7L|L7LJF7L7|L7L7||F-JF---J|LJLJF--JF7F7||FJL7L7FJ7JJ||JJ
        |..F-FJLJFLF-F--7F|LJL-JL7||||FF-JFJFJF-J|||L7|L7||F-JLJF-J.||||L-JF7FL7|L7|F7.||.L7L7L7L7FJ|J||||FJ||L-7L-7|FJF7F-JLF7||||LJL7FJFJL7|-|7|.F
        |FF--J|-LJ7||L-7|FJF7F7F7LJLJL7L7FJFJFJF7|LJFJL7|||L-7F-JF7FJLJL-7FJL7FJ|FJLJ|FJL7J|FJFJFJ|FJFJ|FJ|L||F7|F-JFJFJLJF-7|LJLJL7F7||FJF7|J.LJ|F7
        F|J|-||.|LFFF--JLJFJLJLJ|F7F-7L-J|.L7|7|||F7|F7||||F7||F7|LJF----J|F-JL7LJF-7|L-7L-J|FJFJFJL-JFJL7L7|||LJL7LL7L--7|FJ|F----J|||LJFJ|||.JFJ-|
        .|JL7-L7-F--JF----JF7F--J|||FL7F7L7FJ|FJ|LJ|||||||||||LJ|L-7|F7F-7|L7FFJF7L7LJF7L-7FJL7|LL7F--J.FJFJ||L7F7L7L|F--J|L7||F7F77|LJF-JJLJ-JLJJF|
        |77-7FL|JL-7FJF----JLJF7FJLJF7LJL7LJFJL7L7FJ||||LJ||LJF-JFFJLJ||FJL7|FJFJL-JF-J|F7|L7FJ|F-J|F7F7L7L7|L7||L-JFJL--7|FJ|||||L-JF7L7JF|...LJFL7
        L|L7.|F||LLLJJL-7F--7FJLJF7FJL7F7|F7|F7|FJL7|||L7FJ|F-J7F7L7F-J||F7||L7|F7F7|F-J|||FJ|FJL-7|||||||FJL7|||-F7L7F--J||FJLJLJF-7||FJ||F7FL..L7|
        LJ.LJJ-7FFJF7F--J|F7||.F7||L-7LJ|LJ||||||F7||||FJL7||F-7||FJ|F-J||||L7||||||||F-J|||FJ|F--J||||L7|L7FJ||L-JL-JL---JLJF7F-7|LLJLJ|FF7.F-J-L|7
        |L-7FF7.7LFJ|L---J|LJ|FJLJ|F7L-7|F7||||||||||||L77||||FJ|LJ|||F-J|||FJ|||||||||F-J||L7|L7F-J|||.LJFJ|FJ|-F7F---------JLJJLJF7F7FFFJ|F77JF|LF
        77-L-L77FFL7|F7F-7|F-JL7F7LJL7FJLJLJLJ||LJ|||||FJFJ|||L7L-7FJ|L7FJ|||FJ||LJ|||||F7||FJ|FJ|F7|||F--JFJ|FJFJLJF-7F7F---------JLJL7FL7LJL7L-J||
        |L-7FF7-F-7||||L7LJL7F-J||F-7LJF-----7|L7FJ|LJ|L7L7|||FJF7|L7L7||FJ|||FJL7LLJLJLJLJ||FJL7LJ||LJ|F-7L7||FL---JFJ|LJF--7F7F------JF7|F--J.L|7|
        7J7FFJ|FL7LJLJL7|F--JL-7|||FJF-JJF---J|FJL7L-7|FJ-LJLJ|FJLJFJFJ||L7||||F7L-------77|||F7||FJL7FJ|FJFJ||F----7L-JF7|F7LJLJF------JLJL--77FLF7
        |L--L7L7|L----7|LJF7F7FJ|LJ|FJ|F-JF--7|L7LL7FJLJF7F---JL--7|FJ-LJFJ|||LJL7F-7F--7L7||LJ|L7L7FJL7|L7L7LJL---7|F7FJLJ||F---JF7F--7F7F7F7|.F-77
        L7J|FL7L-7F7F7|L7FJ|||L-JF7LJF-JF7|F-JL-JF-JL-7FJLJF-7F7F-J|L-7F7L7||L-7L||FJL-7L7||L7FJFJ-LJF7||FL-JF----7||||L---JLJF-7FJ|L-7LJLJ||LJ7JLLJ
        .|FF--JF-J|LJLJFJL7||L7F-JL7FJF-JLJL7F7F7L---7|L--7L7|||L-7L7FJ|L-J|L7FJFJ|L-7FJFJ|L-J|FJ.F--JLJL-7F7L---7LJLJL----7F-JFJ|.L-7L--7L||.FJ7LJ.
        FLFL7F7L7FJF7F7L--JLJFJL-7FJL-J.F7F-J|||L--7FJL-7FJFJ||L7FJ.||FL7F7||||FJFJF-J|7L7L--7LJF7L--7F7F-J||F7F7L7F7F----7|L-7L7L-7J|F-7|-|L7-LJ-JF
        7JL.||L7LJFJLJL-7F--7||F-J|F7F--J|L--JLJF7FJL7F-JL7L7||J||F-JL7FJ||L7||L7L7L-7L7FJF7FJ.FJL---J||L-7|||LJ|FJ|||F---J|F-JF|F7L7LJ7|L7L-JF7-|.|
        77LF||F|F7|F-7F-J|-FJL7|F7|||L--7L---7F-J||.|LJJF7|FJ|L7LJL7F7|L7|L-JLJ|L7L-7|FJL7||L7FJF----7|L--J||L-7||FJLJL7F7JLJFF-J|L7L--7|FJJ|-F--J7J
        |7JLLJ-||||L7|L-7|FJF7|LJ|LJL---JF-7FLJF7|L7F---JLJL7L7L--7||||FJL-7F----JF7||L7FJ|L7|L7L7F--J|7F7|||LFJ|||F---J||F7F7L-7L7|F7FJ||J7|-|L|L|7
        .--FFLFLJLJFJL--J|L-J|L--JF--7F7FJJL---J||FJL7F--7F7L7L7F7|||||L7F-JL-7F7FJ||L7|L7|FJ||L7|L--7|FJL7|L7L7|LJL-7F7|LJLJL-7|FJ||LJ-||.F77|-LJL|
        7J--|.LL-F7L--7F-JF--JF---JF7LJLJF|F7F7FJLJF7||F-J|L7||LJLJLJ|L7||F---J||L7||FJL7||L7|F-J||F-J|L-7|L7L-JL----J|LJF-----JLJ.LJJ|-LJFJL--7.--J
        L--7..FLFJL-7-LJF7L---JF---JL-7F-7FJLJLJF7J||||L-7|7|L---7F-7|FJLJ|F7F7||FJ||L7FJ|L7||L-7|FJF7|F-JL-JF------7FJF7L-7F--7F---7FFF-7|F---J7-FJ
        -|F7-L77L--7L7|FJL-----JF7F-7FJL7|L-----JL-J|LJF7|L7|F7F7LJFJ|L--7LJ|||||L7|L7||FJFJ||F-J|L-J||L-7F-7L7F----J|FJL7FJ|F-J|F--J7JL7LJL-7LLJ-L-
        |FLJ.L-7L|.L7L-JF--7F-7FJLJFJL--JL----------JF7|LJFJ|||||F7L7|F7FJF-J||||FJL-JLJL7L7LJL7FJF-7LJF-JL7L-JL-----JL-7||7|L--JL7F7-F7L-7F-J.L7.FJ
        7|L|7J...F7FL7F7L7LLJJ||F--JF7F7F7F---------7|||F7L7||LJ||L-JLJLJJL-7|LJ|L---7L|F|FJF7.LJJL7|F-JF7FJF7F7F---7F--JLJFJF7F7FJ|L7|L7FJL-7..LJ7|
        .|-LL.|-FF---J|L7L7F7FJ|L---J||LJ||F-------7|||LJL7||L-7|L--------7FJL-7|F-7FJF--J||||F7F7-||L-7|LJFJLJ|L--7LJF7F7-|FJLJLJ.L7LJFJL7F-J7.|-|7
        J|-FJF|-LL7F--JJL7|||L7L7F---J|F-J|L--7FF7FLJ|L7F-J|L-7|L7F7F7F7F-JL7F7||L7LJLL--7|FJLJ|||FJL--JL7FL7F7L---JF-J||L7|L------7L7FJF7||FLF--7L-
        |--JFLF-|J||F---7LJ||J|FJ|F-7FJL--JF-7L-JL7F-JFJL-7L-7|L7LJLJ|||L-7FJ|LJL7|-|JJFFJ|L--7||LJF-----JF7LJL7F---JF7LJFJ|F---7F7L-JL-JLJL7-|F-JLJ
        77FLF-F-7J||L7F7L--JL-J|FJ|FJ|F----J7L----JL-7L--7|F7|L7L7F7-LJ|F-JL7L7JJ|L-7-F-L-JF--JLJF7|F---7FJL---J|F-7FJ|F7L-JL--7|||F-7F7F7F7L-JL--7|
        F-F-J.|7F-LJ7LJL-----7FJ|FJL7|L-------------7|F--JLJ|L7|FJ||F77LJ-LLL-J|L|F-JF7|F7.L-----J||L-7FJ|F7F---JL7LJ||||F-7F--JLJLJ.|||||||F7F---J7
        |||J|FLLJ7L||.F7F--7|LJFJL77LJFF--7F7F7F----J|L----7L-J||FJ|||F7F7JFLJFL.LJ-FJL7|L--7F7-F7LJF-JL-J|||F----JF-7||||FJL-7F7F7F7LJLJ||LJ|L-7|J|
        F|..FJ.L-F7F7FJLJF7L---JF-JF---JF7LJ||LJF7-F7|F----J|F7LJL7LJLJLJ|-LJ.-.LJLFL-7|L--7LJL-JL--JF7F-7|LJL-----JFJLJLJL7F-J|LJ|||F--7|L7|L-7L77.
        LL777|7-FJLJ|L-7FJL-7F7FJF-JF---JL-7|L--JL-JLJL------JL7F7|F----7|JLF-F77LFF--JL---JF7F7F7F7FJ|L7||F--------JF7F7-FJL--JF-J||L7FJL7L--7|FJ|7
        L||L77|-L--7|F7LJF7JLJLJ-L--JF--7|FJL7F-7F7F7F---7F-7F7LJ||L---7LJ-JL7-LFFJL--7F-7F7|||LJLJLJFJFJ|||F7F------JLJL7L-----JF7|L-JL-7|F--J||JL7
        .L7FL-JFF--JLJ|F7||F-7FF7F7F-JF7L7L-7LJJ||LJ|L--7LJFJ||F7LJF---J-|.7J|7||.FJFFJ||LJLJLJF-----JFJ|LJLJLJF--7F7F7F7L7-F----JLJF7F7FJLJ-F7LJ|.L
        F-LF.FFFJF---7LJLJ|L7L-JLJ|L--JL7L--JF-7|L-7|F-7L-7L-J||L7FJ|7.JL|F|.7---7.LLL-JF-----7|F--7F7|.F7F---7L-7LJLJLJL7L-JF---7F-J|||L----JL7-|7J
        .LL|7J7L-J-F7L7F-7L7L7F--7|F7FF7L--7F|FJ|F7|||FJF7L--7LJF|L-7-J7LLL.-|.JJJ7-|F--JF-7F7LJL-7LJLJFJ|L--7|F7L----7F7L--7L-7FJL7|||L7F-----JFJJ7
        --F-|.FL7F-J|.LJ7L7|7LJLFJLJ|FJL--7L7|L7LJLJLJL7|L--7L7F-JF-J.|L7||J|LFJ||J|FL--7|.LJL----JF--7L7L---JLJL-----J|L--7|F7||F7L7LJJ|L----7----L
        |F-JL-JJFJF-JF--7|||F7F7L--7|L-7F7L-J|FJF7-F7.FJL--7L-J|F7L---7J||J|||L-7FF-L7FLLJF7F-----7L7FJFL--------7F-7F-JF--JLJLJ|||FJF7F|F-7F-J7|.L|
        ||-J|LJ.L7L7L|F-JFJLJ|||F7FJL--J|L-7.|L-JL-JL7L--7FJ|F7LJ|F---J7|JJ-LL7-L7L-7F|-LFJLJF---7L-J|F7F7F------J|FJ|F-JF7F---7|||L7|L7|L7LJJ--7.F|
        |7.F|7F7FL7|FJ|F7L-7FJ||||L7F7F-JF7L-JF7F----JF-7LJFFJL-7|L7F7J7F7.F7-|FL-7L7JLF|L---J|F7L7F7LJ|||L7F7F7F7|||||F7|||F--JLJL7LJFJ|FJJL||.F|FJ
        L.77LJ-F--JLJFJ|L--JL7|LJL-J|||F-J|F7FJ|L----7|FJF7FJF7FJL7LJ|FFJLF-|--||LL--7-7-FF----JL-J|L-7LJ|FJ|||||LJL-JLJLJLJL-----7|F7|7|||LLFF7-FL.
        J7|.FL.L--7F7L-JF-7F7LJF-7F7|LJL-7LJLJLL7F7F7LJ|FJ||FJLJF7L--JF7-|.|LF.|-F7FLL7JLFJF--7F---JF7L-7|L-JLJLJF---7F7F--7F7F7F-JLJLJFJ|7-.|L-J||J
        ||LFJLFLLFJ||F--JFJ||F7L7||||F---JF7F7F7LJLJL--J|FJ||F|FJL-7F7FF-7-L-7|7LLJJ-7F-FL-JF7LJF7F7|L--JL7LF77F7L--7|||L-7LJLJLJF7F--7L-J|||77|.-JJ
        FJFL7.|.||FJ||F--JFJLJ|FJLJLJL----JLJ||L---7F7|FJ|FJL7FJF--J|L7|FJJLLJLFJL.|FL|JLLF7||F-J|||L--7F7L7|L-JL-7FJLJ|F-JF-----JLJF-JFJJ|JJLJJ-|LF
        F-7JLFF7FJ|FJ||F--JF--J|F--------7F--J|F---J|L7|FJ|F-J|FJF7FJFJ||JF7.LF-7J-J-JLF--JLJLJF7LJL---J||FJL-7F-7LJF-7||F7L7F------JF77-F--7-|J7F-|
        L-J|.L|LJFJ|FJLJLF7L7F-JL----7F-7|L---JL----JFJ|L7|L-7||FJ||FJFJL7J||.F|JF77F7L|F---7F-J|F------J||F7FLJ-L--JFJLJ|L-JL------7||F-7LFL7L--J-J
        .L-JFLL-7L7LJL|F-JL-J|F----7-LJFJL--7F-------JFJFJ|F-J|||FJ|L-JF7|FLFF77.|L-7.F||.|.LJ7FJ|F------JLJ|F7F7F---JF--JF-7F7F----J|LJFJFJL-JJ|JFJ
        ||.|L-J-L7|.F7FJF7F--JL---7L7F7L---7LJF--7F7F7L7L-JL--JLJL-JF--JLJ7FL||L|J|LL-LLJ-L7F--JFJL-7F--7F-7LJLJ|L---7|F--JFLJLJF7JF7|F7|L7.F|FL|.||
        .LL7JJL-LLJFJLJFJ||F7.F7F-JFJ|L----JF-JF7LJLJL-JF-7F----7F-7|F---7JJL|L7|-J7|JF||.LFL---JF-7LJF-J|FL---7L-7F7||L7F--77F7|L-JLJ|LJ|L-7FJFJ777
        J..|7L7.LLFJF-7|FJ||L7|LJF7L7L---7F7L7FJ|F-7F7F7|FJL7F-7|L7|LJF7FJ|.F|FJJ||L7-L7-F7F7F7F7|FJF-JF7|F----JF7LJ|LJFJ|F-JFJLJF-7F7|F77.LLLJL-LLJ
        F-LLL-LF7L|FJJLJL7|L7LJF7|L7L----J|L-JL7|||LJLJ|||F-J|FJL-JL7FJLJF7-FJ|L||-FJL.|J|||||||LJ|FJF7||||F7F7FJL-7L--JFJL--JF7FJ-LJLJ||77FJ||7|.LF
        FJ-LLF--|JLJF-7F7LJF|F7||L7L7F----JF7F-J|L----7|||L--JL---7FJL--7|L7L7|L7|LJ|LFF7|LJLJ|L-7LJFJLJLJLJ||||F--JF--7|F---7|||F-----JL7.L7L--.F.-
        L7FFLJJL|7L|L7||L---J||||FJLLJF----JLJF7L--7F7|||L7F-7F7F-JL--7FJL7|FJ|-JF-F7.FJ||F7F7|F7L--JF------J|LJL---JF-J|L--7LJLJ|F-7F-7FJJL-|J.F-.|
        L777J|J.L||F7||L-----JLJLJ|F-7L-------JL--7LJLJLJ-|L7||LJ-F7F7||F-JLJFJLF7|LF-JFJLJLJ|LJ|.F7FL--7F---JF7-F7F7|F-JF--JF7.FJL7|L7LJJ-F7L7FJJF7
        F||||.F7F|-||||F7FF--7F7JF7L7L---------7F7|F7F7F-7|FJ|L7F7||||LJL---7L7FJL7LL-7|F7|F7|F7L-JL7F7-LJF-7FJL-JLJ|LJF7L---JL-JF7|L-JJF7-LF7L|JJJJ
        |FJ--7LJ||.|LJLJL-JF7LJL7|L7L---------7|||LJLJLJFJ|L7L7LJ||||L7JF--7L7LJF-JF77||||FJ|LJL7F7FJ|L-7.L7|L-----7L7FJL7F----7FJLJLF7F|JFLLJLF7L7|
        .L|J|FFJ|JFL-7F7F--JL--7||FJF---------JLJL--7F--J-L-JFJF7LJLJFJFJF-JF|F-JF-J|FJ||||FJJF7LJ|L7|F7L--JL------JJ|L-7|L---7|L-7F-JL----7FJ-LJ7|7
        F.L77-F--7JF-J||L7F----JLJL7L--7F----7F7JF-7|L------7L7||F7F-J.|FJF|FJL7||F-JL7||||L7FJL7FJFJLJL----7F-7F---7L--JL----JL--J|F------J7J||L-.F
        L|.||JL|LF.L-7|L-JL-------7|LF7LJF---J||FJFJL7F----7L7LJ|||||F-J|F7FJF7L-JL77FJLJ||FJL-7|L7L7F7F7F7LLJFLJF--J|F7F---7LF7.F-JL-7F--7L-JL7J|-|
        FJJ----J7LF7JLJF7F-------7||FJL7FL----JLJFJF-JL---7L-JF7LJLJFJF-J|||FJL7F-7L7L-7FJ||F7FJL-JFJ||||||F--7F-JF7F7|||F--JFJL-JF---J|F-J7JF--7L7|
        7|.LJLF.F7JLF7|||L---7F-7LJLJF7L--7F7F---JLL-7F---JF7FJL7FF7L7|F7||LJF-J|LL-JF7||FJLJ|L-7F7L7||||||L-7|L-7|LJLJLJL---JF7F7L7F-7|L-7F--7FJ.|J
        L7.FL-J-|-7-|L-JL7F7.LJFJF-7FJL--7||LJF---7F7||F7F7||L-7L7|L-JSJ||L-7L-7|F7F-J|||L7F-JF7LJL7LJLJLJL7FJL--J|F-7F7F7F---JLJL-J|FJ|F-J|F-J7.F|J
        |L-7-|7L|L.LL-7F7LJL--7|FJFJ|F7F7||L7FJF--J||LJ|LJ|||F7L7||F7F7FJL-7|7FJLJ|L-7||L7||-FJ|F7|L--7F--7LJF----JL7|||||L-----7F--J|FJL--JL7-7JJJ7
        LF-7-7-77J-7JLLJL----7LJL7L-J|||LJL-J|FJF--J|F-JF-J|LJL7|LJ|||LJF--JL7L-7FJ|FJ|L7LJL7L7||L7F--JL7|L--JF7JF-7||||||F7F---JL7F-J|F-7F--J7J|.L|
        |L7J-J.|-7-JF|LF7F7F7L---JF--JLJF----JL-JF--JL-7|F7L--7|L7FJLJF7L---7L7FJ|F7L7L7L7F7L7||L7|L-7F7L7-F--JL-J7LJLJLJLJ|L-7F7FJL--JL7LJ|F-77.FF|
        JJ.|.FFL-|.FJLFJLJLJ|F-7F7L-----JF------7L7F7F7|LJL7JFJ|FJL7F7||F7F-JFJL7|||FJFJ7LJ|FJ|L7||F7||L-JFJF----7F------7FJF7LJLJF-7F-7L--7L7L7F7-.
        LFF-777|FL-J7.L---7FJL7||L-------JF----7L7LJLJ|L-7FJFJFJ|F-J|||LJ|L-7|F7|||||FJF7F7|L7|FJ|||||L7F-JFJF---JL7F----JL7|L7F7FJL|L7|F7FJFJFJ|||7
        F|L-J.LL|.FLFF7F--J|F7||L---------JF7F7L7|F---JF7|L-JFJFJL--J||F7L7FJLJLJ||LJL-J||||FJ||FJ|||L-J|F-J||F----JL-7-F77LJFLJ|L-7|FJ|||L-JFJFJL77
        F-J|.F7.L---FJ|L--7|||||F--7JF-----J||L-JLJF-7J||L7F-J7L7F---JLJL7|L-7F7FJL-7F--J|||L7||L7||L7F-J|F-7LJF--7F7FJFJL-7F7F7L7FJ|L7||L7F7L-JF7L7
        |L7JF|-77L7FL7|F--JLJLJ|L-7L7|F7F--7LJF7F7-L7L-JL-JL-7F7||FF-7-F7||F7LJ|L-77|L7F7|LJFJ||FJ||FJL--JL7L--JF7LJ|L7|F-7|||||7LJ.L7|||F|||F7FJL7|
        -JF7LJJJF.F77||L------7|F7|FJLJ||F7L7FJLJL-7|F7F--7F7||||L7L7L7|LJLJ|F7L-7|FJFJ||L-7L7|||FJ|L-7F-7FJF---JL-7L7LJL7|LJLJL----7|||L7LJLJ|L7-LJ
        .L7L77J.F-JL-JL-------JLJLJL7F7LJ|L-JL7F---JLJ||F7LJLJ|||FJJL7|L--7FJ||F7||L7L-JL7.L7LJ|||FJF-J|FJL-JF-----JFL---J|F--7F7F--J||L7|J|7LL7|-J|
        L.L7--JLL-------7F----7F7F-7LJL-7L---7||LF-77FJLJ|F7F7||||JF7||F7FJ|FJ||LJL-JF---JF7L-7||||FJJFJL7-F7L---7LF7F7F7.|L-7|||L--7|L7LJF7J-FJ|JFJ
        .F7L-|7JFLF-----J||F7LLJLJ-L-7F-JF---J|L7L7L7L7F-J|||||||L-JLJ|||L7|L7|L--7F-JF7F7||JFJLJ|||F-JF-JFJ|F7F7L-JLJLJL7L--JLJL-7FJL-J7.LL-FL-JF||
        7LJ|F7J||-|F----7|FJL---7F7F7||F7L-7F7|FJFJFJF||F7||||||L--7F-J|L7|L7||F-7|L-7|||||L7L7F-J||L-7|F7|FJ|||L--7F7F--J.F7FF--7|L7-F--7.|.||J.F7|
        77.FF7LF--J|F---J|L7F7F7LJLJLJLJL--J||||-|FJF7||||||||||F7J||F7L7||FJ|||FJL7FJ||||L7|L||F7||F7||||||7||L-7|LJ|L--7FJL-JF-JL-JFJF-J7.F--7.7--
        F-F7||-L---JL7F-7||LJLJL-7F-7F-7F7F7||||FJ|FJ||||||||||||L7||||FJ||L7||||F7|L7||||FJL7|||||||||LJ||L7||F7L--7|F-7LJF--7L-7F-7L7|F7-FLJLJ7|-J
        L-JL|J7LF---7|||LJF7F----JL7||FJ|LJ||||LJFJL7||||||||LJLJFJ|||||FJ|FJ|LJLJ|L7||LJ|L-7||||||LJ|L7FJL7LJ|||F7FJ|L7|F7L-7|F-JL7|FJLJ|-J-F7.|7-|
        F|-LJ.L-L--7|LJF--J|L---7F7|||L-J-FJ||L-7|F-JLJ|||||L--7FJFJ|||||FJL7|F7F-JFJ|L7J|F-JLJLJ||F-JFJL7FJF7||LJ|L7|FJLJL--JLJF7FJLJF--JJ.F7-7LL-7
        FL.L7.|.F--J|F7L--7|F---J|||LJ|F7FJFJ|F-J|L7F--J||||F7FJL7|FJ|||||-FJLJ|L-7|FL-JFJL---7F-J|L-7L-7|L-J||L-7|FJ|L----7F-77||L7F-JF7|.F7JL.77F|
        77FJ|F--JF-7LJL---JLJF7F7|||F--JLJFJF|L-7|FJ|F7FJ||||LJF7LJL7|LJ|L7|F7FJF-JL--7L|F-7F7|L-7L7FJF7|L7F7||F7|||FJF----JL7|FJL-JL--JL7-F7.FJL7-7
        |FF-LL7F7|.L-7F7F---7|LJLJ||L----7|7FJF7||L7||||FJ||L-7|||F-J|F-JFJ||||FJF7F7FJFJL7||||F-JFJ|FJ|L7||||||LJ||L7L------JLJF-7F7F---JF|L-J-7J7|
        FJ||.L||LJF--J|||F--J|F---J|F----JL7L7||||FJ||||L7||F7LJL7L--JL7FJJ||||L7|||LJ7L7FJLJ||L--JL|||L7|||||LJF-JL7|F-7F7F---7L7LJ|L-7..F7.F7.-JFL
        JF-L-FLJLLL--7|LJ|F-7||F7F7|L7F-7F7L7LJ||LJ.||LJ7||LJL7F7L7F---JL7FJ|||-LJ||F7F7||F7FJ|F----JL-7LJLJ|L7FJF7FJ|L7|||L--7L7L-7|F7|7F|FFJ-7JFLJ
        L|J|J|FL7LLF-JL7FJL7LJ|||||L7||FJ|L7L-7|L--7||F--JL-7F||L7|L7F-7FJ|FJLJF--J||LJLJLJ||FJ|F7F-7F7L---7L7|L7|||FJFJ|||F7FJ.L7FJ||LJJ--F.LLJ.LJ|
        FJL|FJ7FFF-JF--JL7FJF-J||||FJ||L7|FJF-JL7F7|||L--7F7|FJ|FJ|FJ|FJL7|L-7FJF-7||F-7F7FJLJ|||LJFJ||F7F-JFJ|FJ|LJ|FJ-LJ|||L-77||-||LJL|FL77-J.F-7
        JJ-F-.F7L|F7|F--7|L7L--J|||L7LJ|LJL7L-7FJ|||LJF--J||||FJ|FJL7|L7FJ|F7||FJ7||||FJ||L-7F7||FFJFJLJ||F7L7||FJF-JL-7F-J||F7L7LJ-|L7J.LJ7L--.L7L7
        L|FL|.|.FLJLJL-7LJFJF---J|L-JF-----JF-JL-J|L-7L7F7|||||J||F-J|FJL7||||LJF-J|LJL7|L7FJ||||FJFJJF-JLJL7LJLJ7L7F7FJL-7|LJL7L7|FJFJ7..LJ-|.|.|.7
        FFF.LF|-F-7F---JF7L7|F7F7L--7L-----7L----7|F7|L||LJ||LJFJ|L7FJL7FJLJ|L-7L-7L7F7|L7||FJLJ|L7|F-JF-7F7L-----7LJ|L--7|L--7L7L7|FJ.|F-.JJLFL7F.|
        FLJ7|F-7L7LJF7F7||FJ|||||F--JF7F7F-JF--7FJLJLJFJL-7|L7-|FJFJL7FJL7-FJF7L7FJFJ|LJFJ||L--7L7LJ|F7|FJ|L-----7|F-JF7FJ|F7FJFJFJLJJF-J|..7.|JFJF-
        |JJF|JL7LL7FJ||||||-||||||F7FJLJLJF7L7-LJF7F7LL7F7|L7L7|L7L7FJL-7L7|FJ|FJL7L7|F7L7||F--JFJ-FJ|||L7L-----7|||F7||L7|||L7L7L-7F-7J-F7F|7JFF7J7
        ||FLJ.F|.LLJ|LJ|||L7||LJLJ||L7F--7|L7L---JLJL-7|||L7|FJ|FJ7LJF7FJFJ||F|L-7|FJ|||FJ||L7F7L-7|FJLJ-L7F7F--JLJLJ|||FJLJ|FJFJF-J|FJJFL-7JJF|7F77
        L-||FL|J7F||F--J||FJLJF---J|-LJF7||FJF7F-7F7F7|LJ|FJLJFJL7F--JLJFJL|L7L-7|||-||||FJL7LJ|F-J|L--7F-J|LJ7F---7FJ||L-7LLJJL7L--JL7FJ-J||F-7||.|
        |7|LF-L--J--L--7|||-JLL-7F7L7F7|LJ||FJ|L7||||||F7|L--7L7FJL7F7F7L7.L7L7FJ||L7||||L-7L7L|L-7|F7FJ|F7L7F7|F--JL7||F7L7F7-LL-7F7FJJFL---7FJ-|F|
        7-FJJ.||7|7-|FFJ|LJ-|J.FLJL7LJLJF7||L7L7|LJLJ|||LJF--JFJL-7||LJL7L7FJFJL7||FJLJ|L7LL7L7L7FJ||||LLJL7LJLJL---7LJ||L7LJ|F-F-J|LJFL|.F|LJ|JFF77
        |JJJLL-L7L7J.FL-J.JJ|.FF77LL-7F7|||L7|FJL--7|LJL-7L--7L---J||F--JFJ|FJF7||LJLF-JFJF7L7|FJL7||LJF7F-JF-7F----JF-J|JL-7|J7L-7|FJJ7|-FF--7-FJLJ
        F7|FF7LL|-L.F7L|.J7-|FFJL7FF-J|LJ|L7LJL-7F7|F7F--JF-7L---7|LJL7F7L7LJFJLJL--7L-7L7|L-J||F7|||-FJLJF-JFJL----7L-7L-7-||-JJFLJJJ-LJ|L7|.|.L7|7
        LL7-L|J|LFJ77|.-J.77FFJF7L-JF7|F-JFJF---J|LJ||L--7L7|F-7FJJ-F-J|L7L-7L7F7F-7|F-JFJL7F7||||||L7|F-7L-7|F7F7F7|FLL7FJ-LJ.L--|FL7.L-J-||7FF---F
        .F77.|7|FLFF-J7.L7|FFL-JL-7FJ|||F-JFJF7F7L--JL--7|FJ|L7||7LL|F7L7L7FJ7LJ|L7LJL-7L7-LJ|||||||FJ||-L7FJLJ|||||L7LFJL7..L..F-L7-F7F|JLJ7-F.L|-|
        FFJ|FJ7LL7JF7LL|.F-F------J|FJ||L-7|FJLJL7F--7F7||L7|FJLJ7LFLJL7L7|L7F--JFJJL7.L7|F--J|||LJ||J|L-7|L-7-LJ||L7L7L7FJ--L--J--JFJFL|-F-F-L7---J
        F|-LLJFF-J-L7FJL-FJL-7F--7FJL7|L7FJLJF---J|LFJ|LJ|FJLJ..F|-FLJFJFJ|FJ|F7FJJ.J-|.LJL-7FJ||LL||FJF-J|F7L7F|LJJL-J-||J|FJ.LL7L-L-F7|.|.LJ.|7.LL
        |J7-J-F|.|LLJ-.|F|7|J|L7FJ|LFJ|FJL7F-JF7F7L7L7L-7LJJF|-L-L7|JFJFJ7LJF||LJ|.77-|-|LJ|LJFLJLLLJL7|7LLJL7|J7LLL7.|LLJL-|.J7.|7L|JLF-7JF|F7F--..
        |L-7-L|J.|FLL7F-|J-JFL-JL7|7L-JL7FJL7FJLJ|FJFJF7|.LLFF7LJJ|7-|FJF7L|-||7LFF--FJFJ|LFJ|J-|-.LF-JL77-LL||7L7|LFFJ-.L7||-L--JF7J--7.FJFL7-F-J-L
        7-LJ7|L--|7JL77.L||L|LL7LLJJ7F77|L-7||LLFJL7L-JLJ.F.LJ||7.F77LJ7|L-7J|L7|FL-7.F|-F-JJ||.JJFLL7F-J7JFL|L7J---F7J|-|F|J..|7L7.J7L|..7|FLF||.|J
        L-J7.|.LJL-JF--7J|||L77-JJ|LFJL-JF-JLJ-L|F7L77LJ-L|FLL.F|-L|LJJ7|-FLJ|FJF|LLJ-7LF|F7.J--J7|-FLJ7|7.J-|FJL|L7LL7-.|L7L7-J7|JL|77|.F|F|.JL7777
        ...F7|F7|LJFJ7F7.LF7L-L-J7LJ|F7F7|J|F|J7LJ|FJJJ-J-J.LL-F|JL-7|LLL.JJFLJJ-77LJ.|-J7.-FJFLJL7.L|--JF7|-LJJF--J-FJ|FJFJLL7L|JLLFJ-J7JL-J7JLFJF7
        -F|7LLJ-L.LF.-J|F|JLJJ-L.FJ.LJLJLJ.FFJJ7LLLJJ7JJ.JF|-JF-J-FL7-7|L7.||-JJ-J-7FF-7.77-77JL||J-7J-L7|JF7L-L-77L-LF|LLJLF7.7J--F|JJ-J7|J-77-JFJ7
        .LJ|7LL7L-F7-LJ||LFJ.|-L|JF7.J7|-L-J|JFL-7|LL7FFL-7.|7-FL7.-J|L7L7-L|JL7LL|L7J||FL-7-JJ.7J7.|J|||77||7F-L-7.FFJJ-FJ.F-J|-L.-JJ.|L7|.7L77F-7|
        77L|-.|L7FLLJJ.L|7J..-.F-7-|7|7|||JFJ.-J.7J.FJ-JL|JJ.7-L7|77FJ.J.L.|JLFJJL7LLJFJLF7J.FF|J--7||||||FL7LJF|L|7-7|JFF--7FLL.||J7.-77FJJ|FF7JFF7
        -7-LLLJ|L|7FJ|F.J7...L7|F-7||7|J-J.L|7.|F|-JJ-F|||.|7LJL7.|L|F-FJJF|7FL|.7J..--77.|.L|JJ.|.F-7||F-F7L77FL.|7FJ---J77L-7|L-|JFF7L-7|-FJ|.L|L7
        L7LLLL--7L-JJL.LL--.|-F-L-|-7-LJJ-F.LJ--LJFJ--JL---7-LJLL-7.|JJ|.LF-FJ-LF|-7-|-J-F|-LLLL|-JLLFJLJ.L|JLJ7-F-FJ-7-J.-J-LJ7JL7-J.J7JJ|J|L-|.7-|
        """;
}