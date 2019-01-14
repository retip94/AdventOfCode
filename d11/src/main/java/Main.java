import java.awt.*;

public class Main {

    static int[][] grid = new int[300][300];
    static int serNum = 6878;
    static int highestTotalPower = Integer.MIN_VALUE;

    public static void main(String[] args) {
        long runTime = System.currentTimeMillis();
        assignPowerLevels();
        Point topFuelCoordinate = findTopFuelCoordinate();
        System.out.println(topFuelCoordinate.x + "," + topFuelCoordinate.y);
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

    static Point findTopFuelCoordinate() {
        Point topFuelCoordinate = new Point();
        for (int y = 0; y < (grid.length - 3); y++) {
            for (int x = 0; x < (grid[0].length - 3); x++) {
                int totalPower = getTotalPower(x,y);
                if (totalPower > highestTotalPower) {
                    highestTotalPower = totalPower;
                    topFuelCoordinate = new Point(x + 1, y + 1);
                }
            }
        }
        return topFuelCoordinate;
    }

    static int getTotalPower(int x,int y) {
        int totalPower = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                totalPower += grid[y + i][x + j];
            }
        }
        return totalPower;
    }
}
