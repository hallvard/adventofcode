package hallvard.adventofcode.day2;

public class Part2 extends Part1 {

    @Override
    protected boolean isSafe(String[] levels) {
        for (int i = 0; i < levels.length; i++) {
            if (isSafe(levels, i)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isSafe(String[] levels, int skip) {
        int signum = 0;
        Integer lastLevel = null;
        for (int i = 0; i < levels.length; i++) {
            if (i == skip) {
                continue;
            }
            int level = Integer.valueOf(levels[i]);
            if (lastLevel == null) {
                lastLevel = level;
                continue;
            }
            int diff = level - lastLevel;
            int signum2 = Integer.signum(diff);
            if (signum == 0) {
                signum = signum2;
            } else if (signum != signum2) {
                return false;
            }
            diff = Math.abs(diff);
            if (diff < 1 || diff > 3) {
                return false;
            }
            lastLevel = level;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new Part2().compute(Part1.INPUT));
    }
}