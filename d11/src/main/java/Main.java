public class Main {

    static int[][] grid = new int[300][300];
    static int serNum = 6878;
    static int highestTotalPower = Integer.MIN_VALUE;

    public static void main(String[] args) {
        long runTime = System.currentTimeMillis();
        assignPowerLevels();
        PointF topFuelCoordinate = findTopFuelCoordinate();
        System.out.println(topFuelCoordinate.x + "," + topFuelCoordinate.y+","+topFuelCoordinate.getSquareSize());
        System.out.println("totalPower:" + highestTotalPower);
        System.out.println("Running time: " + (double) (System.currentTimeMillis() - runTime) / 1000 + "s");
    }

    static void assignPowerLevels() {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                int rId = x + 11;
                grid[y][x] = (((rId * (y + 1) + serNum) * rId)%1000 / 100 - 5);
            }
        }
    }

    static PointF findTopFuelCoordinate() {
        PointF topFuelCoordinate = new PointF();
        for (int y = 0; y < (grid.length - 3); y++) {
            for (int x = 0; x < (grid[0].length - 3); x++) {
                int totalPower = 0;
                for (int boxSize = 1; (boxSize + y < grid.length && boxSize + x < grid[0].length); boxSize++) {
                    totalPower = getTotalPower(x,y,boxSize,totalPower);
                    if (totalPower > highestTotalPower) {
                        highestTotalPower = totalPower;
                        topFuelCoordinate = new PointF(x + 1, y + 1, boxSize);
                    }
                }

            }
        }
        return topFuelCoordinate;
    }

    static int getTotalPower(int x0,int y0, int boxSize, int totalPower) {
        boxSize--;
        for (int x = x0; x < x0 + boxSize; x++) {
            totalPower += grid[y0 + boxSize][x];
        }
        for (int y = y0; y <= y0 + boxSize; y++) {
            totalPower += grid[y][x0 + boxSize];
        }
        return totalPower;
    }
}
